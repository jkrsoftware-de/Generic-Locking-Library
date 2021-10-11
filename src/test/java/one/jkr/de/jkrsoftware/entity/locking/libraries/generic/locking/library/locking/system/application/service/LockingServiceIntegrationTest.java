package one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.application.service;

import lombok.SneakyThrows;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.adapter.out.persistence.lock.requests.persistence.InMemoryLockRequestPersistor;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.adapter.out.persistence.lock.InMemoryLockPersistor;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.application.port.in.ForceUnlockCommand;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.application.port.in.LockCommand;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.application.port.in.UnlockCommand;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.application.port.out.LockPort;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.application.port.out.LockRequestPort;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.domain.lock.LockGroup;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.domain.lock.LockIdentifier;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.domain.lock.LockSubject;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.domain.lock.entity.lock.EntityLock;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.domain.lock.timeout.strategy.LockTimeoutReachedException;
import one.jkr.de.jkrsoftware.entity.locking.libraries.generic.locking.library.locking.system.domain.lock.timeout.strategy.LockTimeoutStrategy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

class LockingServiceIntegrationTest {

    private final Clock clock = Clock.systemUTC();

    private final LockPort lockPort = new InMemoryLockPersistor(clock);

    private final LockRequestPort lockRequestPort = new InMemoryLockRequestPersistor(50L, clock);

    private final LockingService uut = new LockingService(lockPort, lockRequestPort);

    @Test
    void getCurrentEntityLock() {
        // given.
        LockIdentifier lockIdentifier = new LockIdentifier(
                new LockGroup("some-lock-group"),
                new LockSubject("id-of-some-lockable-entity-for-testing")
        );
        LockCommand lockCommand = new LockCommand(lockIdentifier);

        // when.
        Optional<EntityLock> firstLock = uut.lock(lockCommand);
        Optional<EntityLock> secondLock = uut.lock(lockCommand);

        // then.
        Assertions.assertThat(firstLock).isPresent();
        Assertions.assertThat(firstLock.get().getLockIdentifier()).isEqualTo(lockIdentifier);
        Assertions.assertThat(secondLock).isEmpty();
    }

    @Test
    void isAlreadyLocked_whenThereIsNoLockBefore() throws LockTimeoutReachedException {
        // given.
        LockIdentifier lockIdentifier = new LockIdentifier(
                new LockGroup("some-firstLock-group"),
                new LockSubject("id-of-some-lockable-entity-for-testing")
        );

        // when.
        boolean alreadyLocked = uut.isAlreadyLocked(lockIdentifier);

        // then.
        Assertions.assertThat(alreadyLocked).isFalse();
    }


    @Test
    void isAlreadyLocked_whenThereAlreadyLocked() throws LockTimeoutReachedException {
        // given.
        LockIdentifier lockIdentifier = new LockIdentifier(
                new LockGroup("some-firstLock-group"),
                new LockSubject("id-of-some-lockable-entity-for-testing")
        );
        LockCommand lockCommand = new LockCommand(lockIdentifier);

        // when.
        uut.waitForLock(lockCommand, LockTimeoutStrategy.hardTimeBasedLockTimeoutStrategy(Duration.ofSeconds(5)));
        boolean alreadyLocked = uut.isAlreadyLocked(lockIdentifier);

        // then.
        Assertions.assertThat(alreadyLocked).isTrue();
    }

    @Test
    void waitForLock_whenThereIsNoLockBefore() throws LockTimeoutReachedException {
        // given.
        LockIdentifier lockIdentifier = new LockIdentifier(
                new LockGroup("some-firstLock-group"),
                new LockSubject("id-of-some-lockable-entity-for-testing")
        );
        LockCommand lockCommand = new LockCommand(lockIdentifier);

        // when.
        EntityLock entityLock = uut.waitForLock(lockCommand, LockTimeoutStrategy.hardTimeBasedLockTimeoutStrategy(Duration.ofSeconds(5)));

        // then.
        Assertions.assertThat(entityLock.getLockIdentifier()).isEqualTo(lockIdentifier);
    }

    @Test
    void waitForLock_whenThereIsAlreadyALock_withoutUnlock() {
        // given.
        LockIdentifier lockIdentifier = new LockIdentifier(
                new LockGroup("some-firstLock-group"),
                new LockSubject("id-of-some-lockable-entity-for-testing")
        );
        LockCommand lockCommand = new LockCommand(lockIdentifier);

        // when.
        Optional<EntityLock> firstLock = uut.lock(lockCommand);
        Throwable secondLockException = Assertions.catchThrowable(() -> uut.waitForLock(lockCommand,
                LockTimeoutStrategy.hardTimeBasedLockTimeoutStrategy(Duration.of(5, ChronoUnit.SECONDS))));

        // then.
        Assertions.assertThat(firstLock).isPresent();
        Assertions.assertThat(secondLockException).isNotNull().isExactlyInstanceOf(LockTimeoutReachedException.class);
    }

    @Test
    void waitForLock_whenThereIsAlreadyALock_withUnlock() throws LockTimeoutReachedException {
        // given.
        LockIdentifier lockIdentifier = new LockIdentifier(
                new LockGroup("some-firstLock-group"),
                new LockSubject("id-of-some-lockable-entity-for-testing")
        );
        LockCommand lockCommand = new LockCommand(lockIdentifier);

        // when.
        Optional<EntityLock> firstLock = uut.lock(lockCommand);
        unlockAfterDelay(firstLock.get(), Duration.ofSeconds(2));
        EntityLock secondLock = uut.waitForLock(lockCommand, LockTimeoutStrategy.hardTimeBasedLockTimeoutStrategy(
                Duration.of(10, ChronoUnit.SECONDS)));

        // then.
        Assertions.assertThat(firstLock).isPresent();
        Assertions.assertThat(firstLock.get().getLockIdentifier()).isEqualTo(lockIdentifier);
        Assertions.assertThat(secondLock).isNotNull();
        Assertions.assertThat(secondLock.getLockIdentifier()).isEqualTo(lockIdentifier);
    }

    @Test
    void lock_noLockBefore_should_success() {
        // given.
        LockIdentifier lockIdentifier = new LockIdentifier(
                new LockGroup("some-lock-group"),
                new LockSubject("id-of-some-lockable-entity-for-testing")
        );
        LockCommand lockCommand = new LockCommand(lockIdentifier);

        // when.
        Optional<EntityLock> lock = uut.lock(lockCommand);

        // then.
        Assertions.assertThat(lock).isPresent();
        Assertions.assertThat(lock).isNotNull();
        Assertions.assertThat(lock.get().getLockIdentifier()).isEqualTo(lockIdentifier);
    }

    @Test
    void lock_whenAlreadyLocked_shouldNotLocked() {
        // given.
        LockIdentifier lockIdentifier = new LockIdentifier(
                new LockGroup("some-lock-group"),
                new LockSubject("id-of-some-lockable-entity-for-testing")
        );
        LockCommand lockCommand = new LockCommand(lockIdentifier);

        // when.
        Optional<EntityLock> firstLock = uut.lock(lockCommand);
        Optional<EntityLock> secondLock = uut.lock(lockCommand);

        // then.
        Assertions.assertThat(firstLock).isPresent();
        Assertions.assertThat(firstLock.get().getLockIdentifier()).isEqualTo(lockIdentifier);
        Assertions.assertThat(secondLock).isEmpty();
    }

    @Test
    void unlock_unsuccessful_unlockTwiceAfterSuccessfulUnlock() {

        // given.
        LockIdentifier lockIdentifier = new LockIdentifier(
                new LockGroup("some-lock-group"),
                new LockSubject("id-of-some-lockable-entity-for-testing")
        );
        LockCommand lockCommand = new LockCommand(lockIdentifier);

        // when.
        Optional<EntityLock> entityLock = uut.lock(lockCommand);

        boolean becameUnlocked = uut.unlock(new UnlockCommand(entityLock.get()));
        boolean becameUnlockedTwice = uut.unlock(new UnlockCommand(entityLock.get()));

        // then.
        Assertions.assertThat(entityLock).isPresent();
        Assertions.assertThat(entityLock.get().getLockIdentifier()).isEqualTo(lockIdentifier);
        Assertions.assertThat(becameUnlocked).isTrue();
        Assertions.assertThat(becameUnlockedTwice).isFalse();
    }

    @Test
    void unlock_successful_whenLocked() {

        // given.
        LockIdentifier lockIdentifier = new LockIdentifier(
                new LockGroup("some-lock-group"),
                new LockSubject("id-of-some-lockable-entity-for-testing")
        );
        LockCommand lockCommand = new LockCommand(lockIdentifier);

        // when.
        Optional<EntityLock> entityLock = uut.lock(lockCommand);
        boolean becameUnlocked = uut.unlock(new UnlockCommand(entityLock.get()));

        // then.
        Assertions.assertThat(entityLock).isPresent();
        Assertions.assertThat(entityLock.get().getLockIdentifier()).isEqualTo(lockIdentifier);
        Assertions.assertThat(becameUnlocked).isTrue();
    }

    @Test
    void unlock_force_noLockBefore() {

        // given.
        LockIdentifier lockIdentifier = new LockIdentifier(
                new LockGroup("some-lock-group"),
                new LockSubject("id-of-some-lockable-entity-for-testing")
        );
        ForceUnlockCommand forceUnlockCommand = new ForceUnlockCommand(lockIdentifier);

        // when.
        boolean becameUnlocked = uut.unlock(forceUnlockCommand);

        // then.
        Assertions.assertThat(becameUnlocked).isTrue();
    }

    @Test
    void unlock_force_whenLocked() {
        // given.
        LockIdentifier lockIdentifier = new LockIdentifier(
                new LockGroup("some-lock-group"),
                new LockSubject("id-of-some-lockable-entity-for-testing")
        );
        LockCommand lockCommand = new LockCommand(lockIdentifier);
        ForceUnlockCommand forceUnlockCommand = new ForceUnlockCommand(lockIdentifier);

        // when.
        Optional<EntityLock> entityLock = uut.lock(lockCommand);
        boolean becameUnlocked = uut.unlock(forceUnlockCommand);

        // then.
        Assertions.assertThat(entityLock).isPresent();
        Assertions.assertThat(entityLock.get().getLockIdentifier()).isEqualTo(lockIdentifier);
        Assertions.assertThat(becameUnlocked).isTrue();
    }

    @SneakyThrows
    private void unlockAfterDelay(EntityLock lockToUnlock, Duration duration) {
        Runnable unlockSequence = () -> {
            try {
                Thread.sleep(duration.toMillis());
                uut.unlock(new UnlockCommand(lockToUnlock));
            } catch (InterruptedException e) {
                // just nothing. :)
            }
        };
        CompletableFuture.runAsync(unlockSequence);
    }
}