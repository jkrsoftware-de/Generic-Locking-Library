package one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.adapter.out.persistence.lock.requests.persistence;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.application.port.out.LockRequestPort;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.domain.lock.LockIdentifier;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.domain.lock.request.LockRequest;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.domain.lock.request.LockRequestId;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.domain.lock.timeout.strategy.TimebasedLockTimeoutStrategy;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.domain.lock.timeout.strategy.LockTimeoutReachedException;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.domain.lock.timeout.strategy.LockTimeoutStrategy;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.*;

@RequiredArgsConstructor
@Slf4j
public class InMemoryLockRequestPersistor implements LockRequestPort {

    private static final String LOG_PREFIX = "[In-Memory Lock-Request Persistor]: ";

    private final Map<LockIdentifier, LinkedHashSet<LockRequest>> lockRequestQueueMap = new LinkedHashMap<>();

    private final long pollingRateOnLockRequestQueue;

    @NonNull
    private final Clock clock;

    @Override
    public LockRequest submitLockRequest(@NonNull LockIdentifier lockIdentifier) {
        log.debug(LOG_PREFIX + "Submit Lock-Request for Lock-ID \"{}\".", lockIdentifier);

        LockRequest lockRequest = new LockRequest(new LockRequestId(UUID.randomUUID()), lockIdentifier, OffsetDateTime.now(clock));
        putLockRequestInQueue(lockRequest);
        return lockRequest;
    }

    @Override
    public void waitForFreeSlot(@NonNull LockRequest lockRequest, @NonNull LockTimeoutStrategy lockTimeoutStrategy)
            throws LockTimeoutReachedException {

        log.debug(LOG_PREFIX + "Wait for next Time-Slot to fulfil the Lock-Request \"{}\".", lockRequest);

        while (!isNextRequestInQueue(lockRequest)) {
            handleLockTimeoutStrategy(lockRequest, lockTimeoutStrategy);
            try {
                Thread.sleep(pollingRateOnLockRequestQueue);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void handleLockTimeoutStrategy(@NonNull LockRequest lockRequest, @NonNull LockTimeoutStrategy lockTimeoutStrategy)
            throws LockTimeoutReachedException {
        boolean shouldTimeout;
        if (lockTimeoutStrategy instanceof TimebasedLockTimeoutStrategy) {
            shouldTimeout = ((TimebasedLockTimeoutStrategy) lockTimeoutStrategy).isLimitReached(lockRequest.getRequestedAt(),
                    OffsetDateTime.now());
        } else {
            log.error(LOG_PREFIX + "Couldn't handle the Lock-Timeout Strategy, cause it's defined but not managed by the Handler.");
            throw new RuntimeException("Couldn't handle the Lock-Timeout Strategy, cause it's defined but not managed by the Handler.");
        }

        if (shouldTimeout) {
            log.error(LOG_PREFIX + "The Lock-Waiting timed out for LockRequest \"{}\". Specified LockTimeout-Strategy was: \"{}\".",
                    lockRequest, lockTimeoutStrategy);
            throw new LockTimeoutReachedException("The Lock-Waiting timed out for LockRequest \"" + lockRequest + "\". Specified " +
                    "LockTimeout-Strategy was: \"" + lockTimeoutStrategy + "\".");
        }
    }

    @Override
    public void removeLockRequest(@NonNull LockRequest lockRequest) {
        log.debug(LOG_PREFIX + "Remove Lock Request \"{}\".", lockRequest);
        getQueue(lockRequest.getLockIdentifier()).remove(lockRequest);
    }

    private boolean isNextRequestInQueue(LockRequest lockRequest) {
        Optional<LockRequest> firstRequestInQueue = getQueue(lockRequest.getLockIdentifier()).stream().findFirst();

        if (firstRequestInQueue.isPresent()) {
            return lockRequest.getLockRequestId().equals(firstRequestInQueue.get().getLockRequestId());
        } else {
            log.error(
                    LOG_PREFIX + "There is no Request present in the In-Memory Queue. Couldn't decide if \"{}\" is the next Request or not.",
                    lockRequest);
            throw new RuntimeException("There is no Request present in the In-Memory Queue. Couldn't decide if \"" +
                    lockRequest + "\" is the next Request or not.");
        }
    }

    private LinkedHashSet<LockRequest> getQueue(LockIdentifier lockIdentifier) {
        if (lockRequestQueueMap.containsKey(lockIdentifier)) {
            return lockRequestQueueMap.get(lockIdentifier);
        } else {
            LinkedHashSet<LockRequest> lockRequestQueue = new LinkedHashSet<>();
            lockRequestQueueMap.put(lockIdentifier, lockRequestQueue);

            return lockRequestQueue;
        }
    }

    private void putLockRequestInQueue(LockRequest lockRequest) {
        getQueue(lockRequest.getLockIdentifier()).add(lockRequest);
    }
}
