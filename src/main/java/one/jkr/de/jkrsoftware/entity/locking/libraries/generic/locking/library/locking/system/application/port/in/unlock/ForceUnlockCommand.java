package one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.application.port.in.unlock;

import lombok.NonNull;
import lombok.Value;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.domain.lock.LockIdentifier;

@Value
public class ForceUnlockCommand {

    @NonNull
    LockIdentifier lockIdentifier;

}
