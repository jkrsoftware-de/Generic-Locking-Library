package one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.domain.lock.timeout.strategy;

import java.io.IOException;

public class LockTimeoutReachedException extends IOException {

    public LockTimeoutReachedException(String message) {
        super(message);
    }

}