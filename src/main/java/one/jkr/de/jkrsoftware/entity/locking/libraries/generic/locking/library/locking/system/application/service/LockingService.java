package one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.application.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.application.port.in.ForceUnlockCommand;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.application.port.in.LockCommand;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.application.port.in.LockingUseCase;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.application.port.in.UnlockCommand;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.application.port.out.LockPort;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.application.port.out.LockRequestPort;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.domain.lock.LockIdentifier;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.domain.lock.entity.lock.EntityLock;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.domain.lock.request.LockRequest;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.domain.lock.timeout.strategy.LockTimeoutReachedException;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.domain.lock.timeout.strategy.LockTimeoutStrategy;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class LockingService implements LockingUseCase {

    @NonNull
    private static final String LOG_PREFIX = "[Locking Service]: ";

    @NonNull
    private final LockPort lockPort;

    @NonNull
    private final LockRequestPort lockRequestPort;

    @Override
    public Optional<EntityLock> getCurrentEntityLock(@NonNull LockIdentifier lockIdentifier) {
        log.info(LOG_PREFIX + "Get Current Lock for \"{}\".", lockIdentifier);
        return lockPort.getCurrentLock(lockIdentifier);
    }

    @Override
    public boolean isAlreadyLocked(@NonNull LockIdentifier lockIdentifier) {
        log.info(LOG_PREFIX + "Check if \"{}\" is already locked.", lockIdentifier);
        return lockPort.getCurrentLock(lockIdentifier).isPresent();
    }

    @Override
    public EntityLock waitForLock(@NonNull LockCommand lockCommand, @NonNull LockTimeoutStrategy lockTimeoutStrategy)
            throws LockTimeoutReachedException {
        log.info(LOG_PREFIX + "Request Lock for \"{}\" and wait for it.", lockCommand.getLockIdentifier());

        LockRequest lockRequest = lockRequestPort.submitLockRequest(lockCommand.getLockIdentifier());
        try {
            lockRequestPort.waitForFreeSlot(lockRequest, lockTimeoutStrategy);
        } catch (LockTimeoutReachedException e) {
            lockRequestPort.removeLockRequest(lockRequest);
            throw e;
        }

        Optional<EntityLock> lock = lock(lockCommand);
        if(lock.isPresent()) {
            return lock.get();
        } else {
            lockRequestPort.removeLockRequest(lockRequest);
            log.error(LOG_PREFIX + "The Internal LockRequestPort was wrong. Can't gather the Lock.");
            throw new RuntimeException("The Internal LockRequestPort was wrong. Can't gather the Lock.");
        }
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
        if(currentLock.isEmpty()) {
            log.error(LOG_PREFIX + "There is no existing Lock for the LockIdentifier of the Unlock-Request {}.", unlockCommand);
            return false;
        }

        EntityLock entityLock = currentLock.get();

        if(entityLock.getEntityLockId().equals(unlockCommand.getEntityLock().getEntityLockId())) {
            lockPort.unlock(unlockCommand.getEntityLock().getLockIdentifier());
            lockRequestPort.removeLockRequest(unlockCommand.getEntityLock().getLockRequest());

            log.info(LOG_PREFIX + "Successfully unlocked \"{}\", originally locked at \"{}\". Unlock-Request: \"{}\".",
                    entityLock.getLockIdentifier(), entityLock.getLockedAt(), unlockCommand);

            return true;
        } else {
            log.error(LOG_PREFIX + "Couldn't fulfil the Unlock-Request {}, cause there is another active Lock (\"{}\") on the same Lock-ID.",
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
    public boolean isValid(@NonNull EntityLock entityLock, @NonNull LockIdentifier lockIdentifier) {
        log.info(LOG_PREFIX + "Check if Entity-Lock \"{}\" is Valid for Lock-ID \"{}\".", entityLock, lockIdentifier);

        Optional<EntityLock> currentEntityLock = getCurrentEntityLock(lockIdentifier);
        if(currentEntityLock.isEmpty()) {
            return false;
        } else
            return currentEntityLock.get().getEntityLockId().equals(entityLock.getEntityLockId());
    }
}
