package net.satisfy.candlelight.core.compat;

import io.wispforest.accessories.api.AccessoriesCapability;
import net.satisfy.candlelight.core.util.Wearables;

public final class AccessoriesCompat {
    private AccessoriesCompat() {
    }

    public static void init() {
        Wearables.registerSlotProvider((entity, item) -> AccessoriesCapability.getOptionally(entity)
                .map(capability -> capability.isEquipped(item))
                .orElse(false));
    }
}
