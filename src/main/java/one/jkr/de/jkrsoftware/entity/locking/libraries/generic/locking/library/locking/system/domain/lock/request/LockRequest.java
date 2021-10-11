package one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.domain.lock.request;

import lombok.NonNull;
import lombok.Value;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.domain.lock.LockIdentifier;

import java.time.OffsetDateTime;

@Value
public class LockRequest {

    @NonNull
    LockRequestId lockRequestId;

    @NonNull
    LockIdentifier lockIdentifier;

    @NonNull
    OffsetDateTime requestedAt;

}
