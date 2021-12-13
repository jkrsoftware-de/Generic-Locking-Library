package one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.application.port.in.get.current.entity.lock;

import lombok.NonNull;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.domain.lock.entity.lock.EntityLock;

import java.util.Optional;

public interface CurrentLockUseCase {

    Optional<EntityLock> getCurrentEntityLock(@NonNull GetCurrentEntityLockCommand command);

    boolean isAlreadyLocked(@NonNull IsAlreadyLockedCommand command);

    boolean isValid(@NonNull IsEntityLockValidCommand command);

}
