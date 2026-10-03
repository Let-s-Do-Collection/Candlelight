package net.satisfy.candlelight.fabric.compat;

import net.satisfy.foundation.armor.Wearing;
import dev.emi.trinkets.api.TrinketsApi;

public final class TrinketsCompat {
    private TrinketsCompat() {
    }

    public static void init() {
        Wearing.registerSlotProvider((entity, item) -> TrinketsApi.getTrinketComponent(entity)
                .map(component -> component.isEquipped(item))
                .orElse(false));
    }
}
