package one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.application.port.in.get.current.entity.lock;

import lombok.NonNull;
import lombok.Value;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.domain.lock.LockIdentifier;

@Value
public class GetCurrentEntityLockCommand {

    @NonNull
    LockIdentifier lockIdentifier;

}
