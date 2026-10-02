package net.satisfy.candlelight;

import dev.architectury.platform.Platform;
import net.minecraft.resources.ResourceLocation;
import net.satisfy.candlelight.core.compat.AccessoriesCompat;
import net.satisfy.candlelight.core.event.CommonEvents;
import net.satisfy.candlelight.core.networking.CandlelightMessages;
import net.satisfy.candlelight.core.registry.*;

public class Candlelight {
    public static final String MOD_ID = "candlelight";

    public static ResourceLocation identifier(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }

    public static void init() {
        DataComponentRegistry.init();
        ObjectRegistry.init();
        ScreenHandlerTypeRegistry.init();
        MobEffectRegistry.init();
        SoundEventRegistry.init();
        EntityTypeRegistry.init();
        CommonEvents.init();
        TabRegistry.init();
        CandlelightMessages.init();
        if (Platform.isModLoaded("accessories")) {
            AccessoriesCompat.init();
        }
    }

    public static void commonInit() {
        FlammableBlockRegistry.init();
    }
}