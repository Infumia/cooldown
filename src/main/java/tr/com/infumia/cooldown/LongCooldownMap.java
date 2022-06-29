package tr.com.infumia.cooldown;

import lombok.experimental.Delegate;
import org.jetbrains.annotations.NotNull;

/**
 * an interface to determine long cooldown maps.
 */
public interface LongCooldownMap extends CooldownMap<Long> {
  /**
   * creates a new composed cooldown map.
   *
   * @param base the base to create.
   *
   * @return a newly creates composed cooldown map instance.
   */
  @NotNull
  static LongCooldownMap create(@NotNull final Cooldown base) {
    return new Impl(CooldownMap.create(base));
  }

  /**
   * a record class that represents simple implementation of {@link LongCooldownMap}.
   */
  record Impl(@NotNull @Delegate CooldownMap<Long> delegate)
    implements LongCooldownMap {}
}
