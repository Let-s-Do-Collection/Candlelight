package net.satisfy.candlelight;

import java.util.List;
import net.minecraft.world.level.block.Block;
import net.satisfy.farm_and_charm.core.compat.RecipeWorkstations;
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

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.satisfy.foundation.entity.equipment.ZombieEquipment;
import net.minecraft.world.entity.EquipmentSlot;
import net.satisfy.candlelight.platform.PlatformHelper;

public class Candlelight {
    public static final String MOD_ID = "candlelight";

    public static ResourceLocation identifier(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }

    public static void init() {
        ZombieEquipment.add(() -> PlatformHelper.zombieCookingHatChance()).adultsOnly().slot(EquipmentSlot.HEAD, ObjectRegistry.COOKING_HAT);
        CompostableRegistry.init();
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
        registerWorkstations();
        if (Platform.isModLoaded("accessories")) {
            AccessoriesCompat.init();
        }
    }

    private static void registerWorkstations() {
        RecipeWorkstations.add(RecipeWorkstations.Type.COOKING_POT, ObjectRegistry.COOKING_POT);
        RecipeWorkstations.add(RecipeWorkstations.Type.ROASTER, ObjectRegistry.COOKING_PAN_ITEM);
        for (RegistrySupplier<Block> stove : List.of(ObjectRegistry.COBBLESTONE_STOVE, ObjectRegistry.SANDSTONE_STOVE, ObjectRegistry.STONE_BRICKS_STOVE, ObjectRegistry.DEEPSLATE_STOVE,
                ObjectRegistry.GRANITE_STOVE, ObjectRegistry.END_STOVE, ObjectRegistry.MUD_STOVE, ObjectRegistry.QUARTZ_STOVE, ObjectRegistry.RED_NETHER_BRICKS_STOVE,
                ObjectRegistry.BASALT_STOVE, ObjectRegistry.BAMBOO_STOVE)) {
            RecipeWorkstations.add(RecipeWorkstations.Type.STOVE, stove);
        }
    }

    private static void registerRarities() {
        registerRarity(FoundationRarity.UNCOMMON,
                ObjectRegistry.TOMATO_SOUP, ObjectRegistry.MUSHROOM_SOUP, ObjectRegistry.SALAD, ObjectRegistry.BEETROOT_SALAD, ObjectRegistry.OMELET,
                ObjectRegistry.PASTA_WITH_LETTUCE, ObjectRegistry.PASTA_WITH_MOZZARELLA, ObjectRegistry.BOLOGNESE, ObjectRegistry.FRESH_GARDEN_SALAD, ObjectRegistry.TOMATO_MOZZARELLA_SALAD,
                ObjectRegistry.ROSE, ObjectRegistry.POTTED_ROSE, ObjectRegistry.COOKING_POT, ObjectRegistry.COOKING_PAN_ITEM, ObjectRegistry.TABLE_SET, ObjectRegistry.CLOCHE,
                ObjectRegistry.TYPEWRITER_IRON, ObjectRegistry.LETTER_CLOSED);
        registerRarity(FoundationRarity.RARE,
                ObjectRegistry.PASTA_WITH_BOLOGNESE, ObjectRegistry.CHICKEN_ALFREDO, ObjectRegistry.CHICKEN_TERIYAKI, ObjectRegistry.CHICKEN_WITH_VEGETABLES, ObjectRegistry.SALMON_ON_WHITE_WINE,
                ObjectRegistry.FILLET_STEAK, ObjectRegistry.BEEF_TARTARE, ObjectRegistry.ROASTED_LAMB_WITH_LETTUCE, ObjectRegistry.ROASTBEEF_WITH_GLAZED_CARROTS, ObjectRegistry.POTROAST,
                ObjectRegistry.HARVEST_PLATE, ObjectRegistry.CHOCOLATE_MOUSSE, ObjectRegistry.CHOCOLATE_BOX, ObjectRegistry.JEWELRY_BOX, ObjectRegistry.TYPEWRITER_GOLD,
                ObjectRegistry.LOVE_LETTER_CLOSED, ObjectRegistry.DINNER_BELL,
                ObjectRegistry.COOKING_HAT, ObjectRegistry.CHEFS_JACKET, ObjectRegistry.CHEFS_PANTS, ObjectRegistry.CHEFS_BOOTS, ObjectRegistry.FLOWER_CROWN,
                ObjectRegistry.DRESS, ObjectRegistry.SHIRT, ObjectRegistry.FORMAL_SHIRT, ObjectRegistry.TROUSERS_AND_VEST, ObjectRegistry.NECKTIE);
        registerRarity(FoundationRarity.EPIC,
                ObjectRegistry.TROPICAL_FISH_SUPREME, ObjectRegistry.BEEF_WITH_MUSHROOM_IN_WINE_AND_POTATOES, ObjectRegistry.LASAGNE, ObjectRegistry.BEEF_WELLINGTON,
                ObjectRegistry.PORK_RIBS, ObjectRegistry.GOLD_RING);
        registerRarity(FoundationRarity.LEGENDARY, ObjectRegistry.CANDLELIGHT_BANNER);
        for (Item item : BuiltInRegistries.ITEM) {
            if (MOD_ID.equals(BuiltInRegistries.ITEM.getKey(item).getNamespace()) && FoundationRarities.get(new ItemStack(item)) == null) {
                FoundationRarities.register(item, FoundationRarity.COMMON);
            }
        }
    }

    @SafeVarargs
    private static void registerRarity(FoundationRarity rarity, RegistrySupplier<? extends ItemLike>... entries) {
        for (RegistrySupplier<? extends ItemLike> entry : entries) {
            FoundationRarities.register(entry.get(), rarity);
        }
    }
}