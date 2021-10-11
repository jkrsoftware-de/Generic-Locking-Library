package one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.domain.lock.entity.lock;

import lombok.NonNull;
import lombok.Value;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.domain.lock.LockIdentifier;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.domain.lock.request.LockRequest;

import java.time.OffsetDateTime;

@Value
public class EntityLock {

    @NonNull
    EntityLockId entityLockId;

    @NonNull
    LockIdentifier lockIdentifier;

    @NonNull
    LockRequest lockRequest;

    @NonNull
    OffsetDateTime lockedAt;

}
