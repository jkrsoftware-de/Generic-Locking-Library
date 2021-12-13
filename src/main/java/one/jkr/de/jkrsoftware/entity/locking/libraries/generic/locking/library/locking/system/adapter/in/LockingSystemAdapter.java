package one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.adapter.in;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.application.port.in.get.current.entity.lock.GetCurrentEntityLockCommand;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.application.port.in.get.current.entity.lock.IsAlreadyLockedCommand;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.application.port.in.lock.LockCommand;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.application.port.in.unlock.ForceUnlockCommand;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.application.port.in.unlock.UnlockCommand;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.application.service.LockingService;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.domain.lock.entity.lock.EntityLock;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.domain.lock.timeout.strategy.LockTimeoutStrategy;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class LockingSystemAdapter {

    @NonNull
    private final LockingService lockingService;

    public Optional<EntityLock> getCurrentEntityLock(@NonNull GetCurrentEntityLockCommand command) {
        return lockingService.getCurrentEntityLock(command);
    }

    public boolean isAlreadyLocked(@NonNull IsAlreadyLockedCommand command) {
        return lockingService.isAlreadyLocked(command);
    }

    public Optional<EntityLock> waitForLock(@NonNull LockCommand lockCommand, @NonNull LockTimeoutStrategy lockTimeoutStrategy) {
        return lockingService.waitForLock(lockCommand, lockTimeoutStrategy);
    }

    public Optional<EntityLock> lock(@NonNull LockCommand lockCommand) {
        return lockingService.lock(lockCommand);
    }

    public boolean unlock(@NonNull UnlockCommand unlockCommand) {
        return lockingService.unlock(unlockCommand);
    }

    public boolean unlock(@NonNull ForceUnlockCommand forceUnlockCommand) {
        return lockingService.unlock(forceUnlockCommand);
    }

}
