package net.satisfy.candlelight.forge.compat;

import net.satisfy.candlelight.core.util.Wearables;
import top.theillusivec4.curios.api.CuriosApi;

public final class CuriosCompat {
    private CuriosCompat() {
    }

    public static void init() {
        Wearables.registerSlotProvider((entity, item) -> CuriosApi.getCuriosInventory(entity)
                .map(handler -> handler.isEquipped(item))
                .orElse(false));
    }
}
