package tr.com.infumia.cooldown;

import java.util.UUID;
import lombok.experimental.Delegate;
import org.jetbrains.annotations.NotNull;

/**
 * an interface to determine unique id cooldown maps.
 */
public interface UniqueIdCooldownMap extends CooldownMap<UUID> {
  /**
   * creates a new composed cooldown map.
   *
   * @param base the base to create.
   *
   * @return a newly creates composed cooldown map instance.
   */
  @NotNull
  static UniqueIdCooldownMap create(@NotNull final Cooldown base) {
    return new Impl(CooldownMap.create(base));
  }

  /**
   * a record class that represents simple implementation of {@link UniqueIdCooldownMap}.
   */
  record Impl(@NotNull @Delegate CooldownMap<UUID> delegate)
    implements UniqueIdCooldownMap {}
}
