package tr.com.infumia.cooldown;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Predicate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

/**
 * an interface to determine composed cooldown maps.
 *
 * @param <Key> type of the key.
 * @param <Internal> type of the internal cache key.
 */
public interface ComposedCooldownMap<Key, Internal> extends Predicate<Key> {
  /**
   * creates a new composed cooldown map.
   *
   * @param base the base to create.
   * @param function the function to create.
   * @param <Key> type of the key.
   * @param <Internal> type of the internal cache key.
   *
   * @return a newly creates composed cooldown map instance.
   */
  @NotNull
  static <Key, Internal> ComposedCooldownMap<Key, Internal> create(
    @NotNull final Cooldown base,
    @NotNull final Function<Key, Internal> function
  ) {
    return new Impl<>(base, function);
  }

  /**
   * gets all cooldowns.
   *
   * @return all cooldowns.
   */
  @NotNull
  Map<Internal, Cooldown> all();

  /**
   * obtains the base.
   *
   * @return base.
   */
  @NotNull
  Cooldown base();

  /**
   * obtains the elapsed.
   *
   * @param key the key to get.
   *
   * @return elapsed.
   */
  default long elapsed(@NotNull final Key key) {
    return this.get(key).elapsed();
  }

  /**
   * gets the cooldown.
   *
   * @param key the key to get.
   *
   * @return cooldown.
   */
  @NotNull
  Cooldown get(@NotNull Key key);

  /**
   * obtains the last tested.
   *
   * @param key the key to get.
   *
   * @return last tested.
   */
  default long lastTested(@NotNull final Key key) {
    return this.get(key).lastTested();
  }

  /**
   * sets the last tested.
   *
   * @param key the key to set.
   * @param lastTested the last tested to set.
   */
  default void lastTested(@NotNull final Key key, final long lastTested) {
    this.get(key).lastTested(lastTested);
  }

  /**
   * puts the cooldown.
   *
   * @param key the key to put.
   * @param cooldown the cooldown to put.
   */
  void put(@NotNull Internal key, @NotNull Cooldown cooldown);

  /**
   * gets the remaining time.
   *
   * @param key the key to get.
   *
   * @return remaining time.
   */
  default long remainingMillis(@NotNull final Key key) {
    return this.get(key).remainingMillis();
  }

  /**
   * gets the remaining time.
   *
   * @param key the key to get.
   * @param unit the unit to get.
   *
   * @return remaining time.
   */
  default long remainingTime(
    @NotNull final Key key,
    @NotNull final TimeUnit unit
  ) {
    return this.get(key).remainingTime(unit);
  }

  /**
   * resets the cooldown.
   *
   * @param key the key to reset.
   */
  default void reset(@NotNull final Key key) {
    this.get(key).reset();
  }

  /**
   * tests the cooldown.
   *
   * @param key the key to test.
   *
   * @return test result.
   */
  @Override
  default boolean test(@NotNull final Key key) {
    return this.get(key).test();
  }

  /**
   * tests silently.
   *
   * @param key the key to test.
   *
   * @return test result.
   */
  default boolean testSilently(@NotNull final Key key) {
    return this.get(key).testSilently();
  }

  /**
   * a class that represents simple implementation of {@link ComposedCooldownMap}.
   */
  @Accessors(fluent = true)
  @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
  final class Impl<Key, Internal>
    implements ComposedCooldownMap<Key, Internal> {

    /**
     * the base.
     */
    @Getter
    @NotNull
    Cooldown base;

    /**
     * cache.
     */
    @NotNull
    LoadingCache<Internal, Cooldown> cache;

    /**
     * the function.
     */
    @NotNull
    Function<Key, Internal> function;

    /**
     * ctor.
     *
     * @param base the base.
     * @param function the function
     */
    private Impl(
      @NotNull final Cooldown base,
      @NotNull final Function<Key, Internal> function
    ) {
      this.base = base;
      this.function = function;
      this.cache =
        Caffeine
          .newBuilder()
          .expireAfterAccess(Duration.ofMillis(base.timeout()).plusSeconds(10L))
          .build(key -> base.copy());
    }

    @NotNull
    @Override
    public Map<Internal, Cooldown> all() {
      return this.cache.asMap();
    }

    @NotNull
    @Override
    public Cooldown get(@NotNull final Key key) {
      return this.cache.get(this.function.apply(key));
    }

    @Override
    public void put(
      @NotNull final Internal key,
      @NotNull final Cooldown cooldown
    ) {
      this.cache.put(key, cooldown);
    }
  }
}
