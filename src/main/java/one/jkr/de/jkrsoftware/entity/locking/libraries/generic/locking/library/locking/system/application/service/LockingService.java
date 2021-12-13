package one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.application.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.application.port.in.get.current.entity.lock.CurrentLockUseCase;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.application.port.in.get.current.entity.lock.GetCurrentEntityLockCommand;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.application.port.in.get.current.entity.lock.IsAlreadyLockedCommand;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.application.port.in.get.current.entity.lock.IsEntityLockValidCommand;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.application.port.in.lock.LockCommand;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.application.port.in.lock.LockUseCase;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.application.port.in.unlock.ForceUnlockCommand;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.application.port.in.unlock.UnlockCommand;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.application.port.in.unlock.UnlockUseCase;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.application.port.out.LockPort;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.application.port.out.LockRequestPort;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.domain.lock.entity.lock.EntityLock;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.domain.lock.request.LockRequest;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.domain.lock.timeout.strategy.LockTimeoutStrategy;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class LockingService implements CurrentLockUseCase, LockUseCase, UnlockUseCase {

    @NonNull
    private static final String LOG_PREFIX = "[Locking Service]: ";

    @NonNull
    private final LockPort lockPort;

    @NonNull
    private final LockRequestPort lockRequestPort;

    @Override
    public Optional<EntityLock> getCurrentEntityLock(@NonNull GetCurrentEntityLockCommand command) {
        return lockPort.getCurrentLock(command.getLockIdentifier());
    }

    @Override
    public boolean isAlreadyLocked(@NonNull IsAlreadyLockedCommand command) {
        log.info(LOG_PREFIX + "Check if Entity is already locked. Corresponding Command: \"{}\".", command);
        return lockPort.getCurrentLock(command.getLockIdentifier()).isPresent();
    }

    @Override
    public Optional<EntityLock> waitForLock(@NonNull LockCommand lockCommand, @NonNull LockTimeoutStrategy lockTimeoutStrategy) {
        log.info(LOG_PREFIX + "Request Lock for \"{}\" and wait for it with Timeout-Strategy: \"{}\".",
                lockCommand.getLockIdentifier(), lockTimeoutStrategy);

        LockRequest lockRequest = lockRequestPort.submitLockRequest(lockCommand.getLockIdentifier());
        lockRequestPort.waitForFreeSlot(lockRequest, lockTimeoutStrategy);

        Optional<EntityLock> lock = lock(lockCommand);
        if (lock.isEmpty()) {
            lockRequestPort.removeLockRequest(lockRequest);
            log.error(LOG_PREFIX + "Can't fulfill the LockCommand (\"{}\"), cause I can't gather a Lock.", lockCommand);
        }
        return lock;
    }

    @Override
    public Optional<EntityLock> lock(@NonNull LockCommand lockCommand) {
        log.info(LOG_PREFIX + "Request Lock for \"{}\".", lockCommand.getLockIdentifier());

        Optional<EntityLock> currentLock = lockPort.getCurrentLock(lockCommand.getLockIdentifier());
        if (currentLock.isPresent()) {
            log.info(LOG_PREFIX + "Couldn't fulfil the LockCommand \"{}\", cause there is already a Lock for Lock-ID \"{}\".",
                    lockCommand, lockCommand.getLockIdentifier());
            return Optional.empty();
        }

        LockRequest lockRequest = lockRequestPort.submitLockRequest(lockCommand.getLockIdentifier());
        return lockPort.lock(lockRequest);
    }

    @Override
    public boolean unlock(@NonNull UnlockCommand unlockCommand) {
        log.info(LOG_PREFIX + "Request Unlock for \"{}\".", unlockCommand.getEntityLock());

        Optional<EntityLock> currentLock = lockPort.getCurrentLock(unlockCommand.getEntityLock().getLockIdentifier());
        if (currentLock.isEmpty()) {
            log.error(LOG_PREFIX + "There is no existing Lock for the LockIdentifier of the Unlock-Request {}.", unlockCommand);
            return false;
        }

        EntityLock entityLock = currentLock.get();
        if (entityLock.getEntityLockId().equals(unlockCommand.getEntityLock().getEntityLockId())) {
            lockPort.unlock(unlockCommand.getEntityLock().getLockIdentifier());
            lockRequestPort.removeLockRequest(unlockCommand.getEntityLock().getLockRequest());

            log.info(LOG_PREFIX + "Successfully unlocked \"{}\", originally locked at \"{}\". Unlock-Request: \"{}\".",
                    entityLock.getLockIdentifier(), entityLock.getLockedAt(), unlockCommand);

            return true;
        } else {
            log.error(
                    LOG_PREFIX + "Couldn't fulfil the Unlock-Request {}, cause there is another active Lock (\"{}\") on the same Lock-ID.",
                    unlockCommand, entityLock);
            return false;
        }
    }

    @Override
    public boolean unlock(@NonNull ForceUnlockCommand forceUnlockCommand) {
        log.info(LOG_PREFIX + "Request Force-Unlock for \"{}\".", forceUnlockCommand.getLockIdentifier());
        lockPort.unlock(forceUnlockCommand.getLockIdentifier());
        return true;
    }

    @Override
    public boolean isValid(@NonNull IsEntityLockValidCommand command) {
        log.info(LOG_PREFIX + "Check if Entity-Lock \"{}\" is Valid for Lock-ID \"{}\".",
                command.getEntityLock(), command.getLockIdentifier());
        return getCurrentEntityLock(new GetCurrentEntityLockCommand(command.getLockIdentifier()))
                .map(entityLock -> entityLock.getEntityLockId().equals(command.getEntityLock().getEntityLockId()))
                .orElse(false);
    }
}
