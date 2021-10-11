package one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.domain.lock.request;

import lombok.NonNull;
import lombok.Value;

import java.util.UUID;

@Value
public class LockRequestId {

    @NonNull
    UUID requestId;

}
