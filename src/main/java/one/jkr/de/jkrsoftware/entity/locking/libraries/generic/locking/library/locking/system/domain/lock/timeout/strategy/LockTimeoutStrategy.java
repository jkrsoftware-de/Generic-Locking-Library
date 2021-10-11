package one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.domain.lock.timeout.strategy;

import lombok.NonNull;

import java.time.Duration;

public interface LockTimeoutStrategy {

    static TimebasedLockTimeoutStrategy hardTimeBasedLockTimeoutStrategy(@NonNull Duration duration) {
        return new TimebasedLockTimeoutStrategy(duration);
    }

}
