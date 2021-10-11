package one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.domain.lock;

import lombok.NonNull;
import lombok.Value;

@Value
public class LockIdentifier {

    @NonNull
    LockGroup lockGroup;

    @NonNull
    LockSubject lockSubject;

}

