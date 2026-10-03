package net.satisfy.candlelight.core.compat;

import net.satisfy.foundation.armor.Wearing;
import io.wispforest.accessories.api.AccessoriesCapability;

public final class AccessoriesCompat {
    private AccessoriesCompat() {
    }

    public static void init() {
        Wearing.registerSlotProvider((entity, item) -> AccessoriesCapability.getOptionally(entity)
                .map(capability -> capability.isEquipped(item))
                .orElse(false));
    }
}
