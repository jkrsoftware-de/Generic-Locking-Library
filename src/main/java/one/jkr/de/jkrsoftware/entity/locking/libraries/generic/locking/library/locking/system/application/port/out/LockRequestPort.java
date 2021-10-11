package one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.application.port.out;

import lombok.NonNull;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.domain.lock.LockIdentifier;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.domain.lock.request.LockRequest;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.domain.lock.timeout.strategy.LockTimeoutReachedException;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.domain.lock.timeout.strategy.LockTimeoutStrategy;

public interface LockRequestPort {

    LockRequest submitLockRequest(@NonNull LockIdentifier lockIdentifier);

    void waitForFreeSlot(@NonNull LockRequest lockRequest, @NonNull LockTimeoutStrategy lockTimeoutStrategy) throws
                                                                                                             LockTimeoutReachedException;

    void removeLockRequest(@NonNull LockRequest lockRequest);

}
