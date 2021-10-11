package one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.domain.lock.timeout.strategy;

import lombok.NonNull;
import lombok.Value;

import java.time.Duration;
import java.time.OffsetDateTime;

@Value
public class TimebasedLockTimeoutStrategy implements LockTimeoutStrategy {

    @NonNull
    Duration duration;

    public boolean isLimitReached(@NonNull OffsetDateTime initiallyTime, @NonNull OffsetDateTime currentTime) {
        return initiallyTime.plus(duration).isBefore(currentTime);
    }

}
