package one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.application.port.in;

import lombok.NonNull;
import lombok.Value;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.domain.lock.entity.lock.EntityLock;

@Value
public class UnlockCommand {

    @NonNull
    EntityLock entityLock;

}
