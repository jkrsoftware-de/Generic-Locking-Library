package one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.application.port.out;

import lombok.NonNull;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.domain.lock.LockIdentifier;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.domain.lock.entity.lock.EntityLock;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.domain.lock.request.LockRequest;

import java.util.Optional;

public interface LockPort {

    Optional<EntityLock> lock(@NonNull LockRequest lockRequest);

    Optional<EntityLock> getCurrentLock(@NonNull LockIdentifier lockIdentifier);

    void unlock(@NonNull LockIdentifier lockIdentifier);

}
