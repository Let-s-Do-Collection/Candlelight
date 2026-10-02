package net.satisfy.candlelight.fabric.compat;

import dev.emi.trinkets.api.TrinketsApi;
import net.satisfy.candlelight.core.util.Wearables;

public final class TrinketsCompat {
    private TrinketsCompat() {
    }

    public static void init() {
        Wearables.registerSlotProvider((entity, item) -> TrinketsApi.getTrinketComponent(entity)
                .map(component -> component.isEquipped(item))
                .orElse(false));
    }
}
