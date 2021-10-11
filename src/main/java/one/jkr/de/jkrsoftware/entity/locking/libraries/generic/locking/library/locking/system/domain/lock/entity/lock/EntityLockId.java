package one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.domain.lock.entity.lock;

import lombok.NonNull;
import lombok.Value;

import java.util.UUID;

@Value
public class EntityLockId {

    @NonNull
    UUID lockId;

}
