package net.satisfy.candlelight;

import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.platform.Platform;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.satisfy.candlelight.core.compat.AccessoriesCompat;
import net.satisfy.candlelight.core.event.CommonEvents;
import net.satisfy.candlelight.core.networking.CandlelightMessages;
import net.satisfy.candlelight.core.registry.*;
import net.satisfy.foundation.rarity.FoundationRarities;
import net.satisfy.foundation.rarity.FoundationRarity;

import java.util.List;

public class Candlelight {
    public static final String MOD_ID = "candlelight";

    public static ResourceLocation identifier(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }

    public static void init() {
        DataComponentRegistry.init();
        ObjectRegistry.init();
        FlammableBlockRegistry.init();
        ArmorSetRegistry.init();
        ScreenHandlerTypeRegistry.init();
        MobEffectRegistry.init();
        SoundEventRegistry.init();
        EntityTypeRegistry.init();
        CommonEvents.init();
        TabRegistry.init();
        CandlelightMessages.init();
        LifecycleEvent.SETUP.register(Candlelight::registerRarities);
        if (Platform.isModLoaded("accessories")) {
            AccessoriesCompat.init();
        }
    }

    private static void registerRarities() {
        FoundationRarities.register(ObjectRegistry.CANDLELIGHT_BANNER.get(), FoundationRarity.LEGENDARY);
        for (RegistrySupplier<Item> piece : List.of(ObjectRegistry.COOKING_HAT, ObjectRegistry.CHEFS_JACKET, ObjectRegistry.CHEFS_PANTS, ObjectRegistry.CHEFS_BOOTS, ObjectRegistry.FLOWER_CROWN, ObjectRegistry.DRESS, ObjectRegistry.SHIRT, ObjectRegistry.FORMAL_SHIRT, ObjectRegistry.TROUSERS_AND_VEST, ObjectRegistry.NECKTIE)) {
            FoundationRarities.register(piece.get(), FoundationRarity.RARE);
        }
    }
}