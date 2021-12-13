package one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.application.port.in.lock;

import lombok.NonNull;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.domain.lock.entity.lock.EntityLock;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.domain.lock.timeout.strategy.LockTimeoutStrategy;

import java.util.Optional;

public interface LockUseCase {

    Optional<EntityLock> waitForLock(@NonNull LockCommand lockCommand, @NonNull LockTimeoutStrategy lockTimeoutStrategy);

    Optional<EntityLock> lock(@NonNull LockCommand lockCommand);

}
