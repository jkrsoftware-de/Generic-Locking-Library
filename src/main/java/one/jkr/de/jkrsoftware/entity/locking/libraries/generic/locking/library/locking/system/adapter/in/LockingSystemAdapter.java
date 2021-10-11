package one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.adapter.in;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.application.port.in.ForceUnlockCommand;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.application.port.in.LockCommand;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.application.port.in.LockingUseCase;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.application.port.in.UnlockCommand;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.domain.lock.LockIdentifier;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.domain.lock.entity.lock.EntityLock;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.domain.lock.timeout.strategy.LockTimeoutReachedException;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.domain.lock.timeout.strategy.LockTimeoutStrategy;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class LockingSystemAdapter {

    @NonNull
    private final LockingUseCase lockingUseCase;

    public Optional<EntityLock> getCurrentEntityLock(@NonNull LockIdentifier lockIdentifier) {
        return lockingUseCase.getCurrentEntityLock(lockIdentifier);
    }

    public boolean isAlreadyLocked(@NonNull LockIdentifier lockIdentifier) {
        return lockingUseCase.isAlreadyLocked(lockIdentifier);
    }

    public EntityLock waitForLock(@NonNull LockCommand lockCommand, @NonNull LockTimeoutStrategy lockTimeoutStrategy)
            throws LockTimeoutReachedException {
        return lockingUseCase.waitForLock(lockCommand, lockTimeoutStrategy);
    }

    public Optional<EntityLock> lock(@NonNull LockCommand lockCommand) {
        return lockingUseCase.lock(lockCommand);
    }

    public boolean unlock(@NonNull UnlockCommand unlockCommand) {
        return lockingUseCase.unlock(unlockCommand);
    }

    public boolean unlock(@NonNull ForceUnlockCommand forceUnlockCommand) {
        return lockingUseCase.unlock(forceUnlockCommand);
    }
}
