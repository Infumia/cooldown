package tr.com.infumia.cooldown;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

/**
 * an interface to determine cooldowns.
 */
public interface Cooldown {
  /**
   * creates a new cooldown.
   *
   * @param duration the duration to create.
   *
   * @return cooldown.
   */
  @NotNull
  static Cooldown of(@NotNull final Duration duration) {
    return new Impl(duration);
  }

  /**
   * creates a new cooldown.
   *
   * @param amount the amount to create.
   * @param unit the unit to create.
   *
   * @return cooldown.
   */
  @NotNull
  static Cooldown of(final long amount, @NotNull final TimeUnit unit) {
    return Cooldown.of(Duration.of(amount, unit.toChronoUnit()));
  }

  /**
   * clones a copy of {@code this}.
   *
   * @return copy of {@code this}.
   */
  @NotNull
  Cooldown copy();

  /**
   * obtains the elapsed.
   *
   * @return elapsed.
   */
  default long elapsed() {
    return System.currentTimeMillis() - this.lastTested();
  }

  /**
   * obtains the last tested.
   *
   * @return last tested.
   */
  long lastTested();

  /**
   * sets the last tested.
   *
   * @param lastTested the last tested to set.
   */
  void lastTested(@Range(from = 0L, to = Long.MAX_VALUE) long lastTested);

  /**
   * gets the remaining time.
   *
   * @return remaining time.
   */
  default long remainingMillis() {
    final var diff = this.elapsed();
    return diff > this.timeout() ? 0L : this.timeout() - diff;
  }

  /**
   * gets the remaining time.
   *
   * @param unit the unit to get.
   *
   * @return remaining time.
   */
  default long remainingTime(@NotNull final TimeUnit unit) {
    return Math.max(
      0L,
      unit.convert(this.remainingMillis(), TimeUnit.MILLISECONDS)
    );
  }

  /**
   * resets the cooldown.
   */
  default void reset() {
    this.lastTested(System.currentTimeMillis());
  }

  /**
   * test the cooldown.
   *
   * @return test result.
   */
  default boolean test() {
    if (!this.testSilently()) {
      return false;
    }
    this.reset();
    return true;
  }

  /**
   * tests silently.
   *
   * @return test result.
   */
  default boolean testSilently() {
    return this.elapsed() > this.timeout();
  }

  /**
   * obtains the timeout.
   *
   * @return timeout.
   */
  long timeout();

  /**
   * a class that represents simple implementation of {@link Cooldown}.
   */
  @Getter
  @Accessors(fluent = true)
  @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
  final class Impl implements Cooldown {

    /**
     * the timeout.
     */
    long timeout;

    /**
     * the last tested.
     */
    @NonFinal
    @Range(from = 0L, to = Long.MAX_VALUE)
    long lastTested = 0;

    /**
     * ctor.
     *
     * @param duration the duration.
     */
    private Impl(@NotNull final Duration duration) {
      this.timeout = duration.toMillis();
    }

    @NotNull
    @Override
    public Cooldown copy() {
      return new Impl(Duration.ofMillis(this.timeout));
    }

    @Override
    public void lastTested(
      @Range(from = 0L, to = Long.MAX_VALUE) final long lastTested
    ) {
      this.lastTested = Math.max(lastTested, 0);
    }
  }
}
