package one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.application.port.in;

import lombok.NonNull;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.domain.lock.LockIdentifier;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.domain.lock.entity.lock.EntityLock;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.domain.lock.timeout.strategy.LockTimeoutReachedException;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.domain.lock.timeout.strategy.LockTimeoutStrategy;

import java.util.Optional;

public interface LockingUseCase {

    Optional<EntityLock> getCurrentEntityLock(@NonNull LockIdentifier lockIdentifier);

    boolean isAlreadyLocked(@NonNull LockIdentifier lockIdentifier);

    EntityLock waitForLock(@NonNull LockCommand lockCommand, @NonNull LockTimeoutStrategy lockTimeoutStrategy) throws
                                                                                                               LockTimeoutReachedException;
    Optional<EntityLock> lock(@NonNull LockCommand lockCommand);

    boolean unlock(@NonNull UnlockCommand unlockCommand);

    boolean unlock(@NonNull ForceUnlockCommand forceUnlockCommand);

    boolean isValid(@NonNull EntityLock entityLock, @NonNull LockIdentifier lockIdentifier);

}
