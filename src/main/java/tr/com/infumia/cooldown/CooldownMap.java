package tr.com.infumia.cooldown;

import java.util.function.Function;
import lombok.experimental.Delegate;
import org.jetbrains.annotations.NotNull;

/**
 * an interface to determine cooldown maps.
 *
 * @param <Key> type of the key.
 */
public interface CooldownMap<Key> extends ComposedCooldownMap<Key, Key> {
  /**
   * creates a new composed cooldown map.
   *
   * @param base the base to create.
   * @param <Key> type of the key.
   *
   * @return a newly creates composed cooldown map instance.
   */
  @NotNull
  static <Key> CooldownMap<Key> create(@NotNull final Cooldown base) {
    return new Impl<>(
      ComposedCooldownMap.<Key, Key>create(base, Function.identity())
    );
  }

  /**
   * a record class that represents simple implementation of {@link CooldownMap}.
   */
  record Impl<Key>(@NotNull @Delegate ComposedCooldownMap<Key, Key> delegate)
    implements CooldownMap<Key> {}
}
