package tr.com.infumia.cooldown;

import lombok.experimental.Delegate;
import org.jetbrains.annotations.NotNull;

/**
 * an interface to determine unique id cooldown maps.
 */
public interface StringCooldownMap extends CooldownMap<String> {
  /**
   * creates a new composed cooldown map.
   *
   * @param base the base to create.
   *
   * @return a newly creates composed cooldown map instance.
   */
  @NotNull
  static StringCooldownMap create(@NotNull final Cooldown base) {
    return new Impl(CooldownMap.create(base));
  }

  /**
   * a record class that represents simple implementation of {@link StringCooldownMap}.
   */
  record Impl(@NotNull @Delegate CooldownMap<String> delegate)
    implements StringCooldownMap {}
}
