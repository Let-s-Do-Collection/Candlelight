package net.satisfy.candlelight.core.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.satisfy.candlelight.Candlelight;

@SuppressWarnings("unused")
public class TabRegistry {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Candlelight.MOD_ID, Registries.CREATIVE_MODE_TAB);
    public static final RegistrySupplier<CreativeModeTab> CANDLELIGHT_TAB = TABS.register("candlelight", () -> CreativeModeTab.builder(CreativeModeTab.Row.BOTTOM, 0)
            .icon(() -> new ItemStack(ObjectRegistry.COOKING_POT.get()))
            .title(Component.translatable("creativetab.candlelight.tab_main"))
            .displayItems((parameters, output) -> {
                acceptMain(output);
                acceptKitchen(output);
                acceptFurniture(output);
            })
            .build());

    public static void acceptMain(CreativeModeTab.Output output) {
        output.accept(ObjectRegistry.COOKING_POT.get());
        output.accept(ObjectRegistry.COOKING_PAN.get());
        output.accept(ObjectRegistry.BOWL.get());
        output.accept(ObjectRegistry.PLATE.get());
        output.accept(ObjectRegistry.GLASS.get());
        output.accept(ObjectRegistry.WINE_GLASS.get());
        output.accept(ObjectRegistry.NAPKIN.get());
        output.accept(ObjectRegistry.CLOCHE.get());
        output.accept(ObjectRegistry.DINNER_BELL.get());
        output.accept(ObjectRegistry.TOMATO_SOUP.get());
        output.accept(ObjectRegistry.MUSHROOM_SOUP.get());
        output.accept(ObjectRegistry.BEETROOT_SALAD.get());
        output.accept(ObjectRegistry.PASTA_WITH_MOZZARELLA.get());
        output.accept(ObjectRegistry.BOLOGNESE.get());
        output.accept(ObjectRegistry.CHICKEN_TERIYAKI.get());
        output.accept(ObjectRegistry.SALAD.get());
        output.accept(ObjectRegistry.CHOCOLATE_MOUSSE.get());
        output.accept(ObjectRegistry.MOZZARELLA.get());
        output.accept(ObjectRegistry.BEEF_TARTARE.get());
        output.accept(ObjectRegistry.PASTA_WITH_BOLOGNESE.get());
        output.accept(ObjectRegistry.PASTA_WITH_LETTUCE.get());
        output.accept(ObjectRegistry.OMELET.get());
        output.accept(ObjectRegistry.FRESH_GARDEN_SALAD.get());
        output.accept(ObjectRegistry.HARVEST_PLATE.get());
        output.accept(ObjectRegistry.BEEF_WITH_MUSHROOM_IN_WINE_AND_POTATOES.get());
        output.accept(ObjectRegistry.TOMATO_MOZZARELLA_SALAD.get());
        output.accept(ObjectRegistry.PORK_RIBS.get());
        output.accept(ObjectRegistry.POTROAST.get());
        output.accept(ObjectRegistry.ROASTBEEF_WITH_GLAZED_CARROTS.get());
        output.accept(ObjectRegistry.ROASTED_LAMB_WITH_LETTUCE.get());
        output.accept(ObjectRegistry.TROPICAL_FISH_SUPREME.get());
        output.accept(ObjectRegistry.FILLET_STEAK.get());
        output.accept(ObjectRegistry.SALMON_ON_WHITE_WINE.get());
        output.accept(ObjectRegistry.CHICKEN_ALFREDO.get());
        output.accept(ObjectRegistry.CHICKEN_WITH_VEGETABLES.get());
        output.accept(ObjectRegistry.LASAGNE.get());
        output.accept(ObjectRegistry.BEEF_WELLINGTON.get());
        output.accept(ObjectRegistry.COOKING_HAT.get());
        output.accept(ObjectRegistry.CHEFS_JACKET.get());
        output.accept(ObjectRegistry.CHEFS_PANTS.get());
        output.accept(ObjectRegistry.CHEFS_BOOTS.get());
        output.accept(ObjectRegistry.FLOWER_CROWN.get());
        output.accept(ObjectRegistry.DRESS.get());
        output.accept(ObjectRegistry.NECKTIE.get());
        output.accept(ObjectRegistry.SHIRT.get());
        output.accept(ObjectRegistry.TROUSERS_AND_VEST.get());
        output.accept(ObjectRegistry.GOLD_RING.get());
        output.accept(ObjectRegistry.JEWELRY_BOX.get());
        output.accept(ObjectRegistry.PAINTING.get());
        output.accept(ObjectRegistry.HEART.get());
        output.accept(ObjectRegistry.ROSE.get());
        output.accept(ObjectRegistry.CHOCOLATE_BOX.get());
        output.accept(ObjectRegistry.TYPEWRITER_IRON.get());
        output.accept(ObjectRegistry.TYPEWRITER_GOLD.get());
        output.accept(ObjectRegistry.TABLE_SIGN_ITEM.get());
        output.accept(ObjectRegistry.NOTE_PAPER.get());
        output.accept(ObjectRegistry.NOTE_PAPER_WRITEABLE.get());
        output.accept(ObjectRegistry.LETTER_OPEN.get());
        output.accept(ObjectRegistry.LOVE_LETTER_OPEN.get());
        output.accept(ObjectRegistry.CANDLELIGHT_BANNER.get());
    }

    public static void acceptKitchen(CreativeModeTab.Output output) {
        output.accept(ObjectRegistry.COBBLESTONE_STOVE.get());
        output.accept(ObjectRegistry.COBBLESTONE_KITCHEN_SINK.get());
        output.accept(ObjectRegistry.COBBLESTONE_COUNTER.get());
        output.accept(ObjectRegistry.SANDSTONE_STOVE.get());
        output.accept(ObjectRegistry.SANDSTONE_KITCHEN_SINK.get());
        output.accept(ObjectRegistry.SANDSTONE_COUNTER.get());
        output.accept(ObjectRegistry.STONE_BRICKS_STOVE.get());
        output.accept(ObjectRegistry.STONE_BRICKS_KITCHEN_SINK.get());
        output.accept(ObjectRegistry.STONE_BRICKS_COUNTER.get());
        output.accept(ObjectRegistry.DEEPSLATE_STOVE.get());
        output.accept(ObjectRegistry.DEEPSLATE_KITCHEN_SINK.get());
        output.accept(ObjectRegistry.DEEPSLATE_COUNTER.get());
        output.accept(ObjectRegistry.GRANITE_STOVE.get());
        output.accept(ObjectRegistry.GRANITE_KITCHEN_SINK.get());
        output.accept(ObjectRegistry.GRANITE_COUNTER.get());
        output.accept(ObjectRegistry.END_STOVE.get());
        output.accept(ObjectRegistry.END_KITCHEN_SINK.get());
        output.accept(ObjectRegistry.END_COUNTER.get());
        output.accept(ObjectRegistry.MUD_STOVE.get());
        output.accept(ObjectRegistry.MUD_KITCHEN_SINK.get());
        output.accept(ObjectRegistry.MUD_COUNTER.get());
        output.accept(ObjectRegistry.QUARTZ_STOVE.get());
        output.accept(ObjectRegistry.QUARTZ_KITCHEN_SINK.get());
        output.accept(ObjectRegistry.QUARTZ_COUNTER.get());
        output.accept(ObjectRegistry.RED_NETHER_BRICKS_STOVE.get());
        output.accept(ObjectRegistry.RED_NETHER_BRICKS_KITCHEN_SINK.get());
        output.accept(ObjectRegistry.RED_NETHER_BRICKS_COUNTER.get());
        output.accept(ObjectRegistry.BASALT_STOVE.get());
        output.accept(ObjectRegistry.BASALT_KITCHEN_SINK.get());
        output.accept(ObjectRegistry.BASALT_COUNTER.get());
        output.accept(ObjectRegistry.BAMBOO_STOVE.get());
        output.accept(ObjectRegistry.BAMBOO_KITCHEN_SINK.get());
        output.accept(ObjectRegistry.BAMBOO_COUNTER.get());
    }

    public static void acceptFurniture(CreativeModeTab.Output output) {
        output.accept(ObjectRegistry.FLOORBOARD.get());
        output.accept(ObjectRegistry.DRAWER.get());
        output.accept(ObjectRegistry.CABINET.get());
        output.accept(ObjectRegistry.SIDEBOARD.get());
        output.accept(ObjectRegistry.CHAIR.get());
        output.accept(ObjectRegistry.SOFA.get());
        output.accept(ObjectRegistry.TABLE.get());
        output.accept(ObjectRegistry.LAMP.get());
        output.accept(ObjectRegistry.SIDE_TABLE.get());
        output.accept(ObjectRegistry.OAK_CABINET.get());
        output.accept(ObjectRegistry.OAK_DRAWER.get());
        output.accept(ObjectRegistry.OAK_TABLE.get());
        output.accept(ObjectRegistry.OAK_CHAIR.get());
        output.accept(ObjectRegistry.OAK_SHELF.get());
        output.accept(ObjectRegistry.OAK_BIG_TABLE.get());
        output.accept(ObjectRegistry.BIRCH_CABINET.get());
        output.accept(ObjectRegistry.BIRCH_DRAWER.get());
        output.accept(ObjectRegistry.BIRCH_TABLE.get());
        output.accept(ObjectRegistry.BIRCH_CHAIR.get());
        output.accept(ObjectRegistry.BIRCH_SHELF.get());
        output.accept(ObjectRegistry.BIRCH_BIG_TABLE.get());
        output.accept(ObjectRegistry.SPRUCE_CABINET.get());
        output.accept(ObjectRegistry.SPRUCE_DRAWER.get());
        output.accept(ObjectRegistry.SPRUCE_TABLE.get());
        output.accept(ObjectRegistry.SPRUCE_CHAIR.get());
        output.accept(ObjectRegistry.SPRUCE_SHELF.get());
        output.accept(ObjectRegistry.SPRUCE_BIG_TABLE.get());
        output.accept(ObjectRegistry.DARK_OAK_CABINET.get());
        output.accept(ObjectRegistry.DARK_OAK_DRAWER.get());
        output.accept(ObjectRegistry.DARK_OAK_TABLE.get());
        output.accept(ObjectRegistry.DARK_OAK_CHAIR.get());
        output.accept(ObjectRegistry.DARK_OAK_SHELF.get());
        output.accept(ObjectRegistry.DARK_OAK_BIG_TABLE.get());
        output.accept(ObjectRegistry.ACACIA_CABINET.get());
        output.accept(ObjectRegistry.ACACIA_DRAWER.get());
        output.accept(ObjectRegistry.ACACIA_TABLE.get());
        output.accept(ObjectRegistry.ACACIA_CHAIR.get());
        output.accept(ObjectRegistry.ACACIA_SHELF.get());
        output.accept(ObjectRegistry.ACACIA_BIG_TABLE.get());
        output.accept(ObjectRegistry.JUNGLE_CABINET.get());
        output.accept(ObjectRegistry.JUNGLE_DRAWER.get());
        output.accept(ObjectRegistry.JUNGLE_TABLE.get());
        output.accept(ObjectRegistry.JUNGLE_CHAIR.get());
        output.accept(ObjectRegistry.JUNGLE_SHELF.get());
        output.accept(ObjectRegistry.JUNGLE_BIG_TABLE.get());
        output.accept(ObjectRegistry.MANGROVE_CABINET.get());
        output.accept(ObjectRegistry.MANGROVE_DRAWER.get());
        output.accept(ObjectRegistry.MANGROVE_TABLE.get());
        output.accept(ObjectRegistry.MANGROVE_CHAIR.get());
        output.accept(ObjectRegistry.MANGROVE_SHELF.get());
        output.accept(ObjectRegistry.MANGROVE_BIG_TABLE.get());
        output.accept(ObjectRegistry.WARPED_CABINET.get());
        output.accept(ObjectRegistry.WARPED_DRAWER.get());
        output.accept(ObjectRegistry.WARPED_TABLE.get());
        output.accept(ObjectRegistry.WARPED_CHAIR.get());
        output.accept(ObjectRegistry.WARPED_SHELF.get());
        output.accept(ObjectRegistry.WARPED_BIG_TABLE.get());
        output.accept(ObjectRegistry.CRIMSON_CABINET.get());
        output.accept(ObjectRegistry.CRIMSON_DRAWER.get());
        output.accept(ObjectRegistry.CRIMSON_TABLE.get());
        output.accept(ObjectRegistry.CRIMSON_CHAIR.get());
        output.accept(ObjectRegistry.CRIMSON_SHELF.get());
        output.accept(ObjectRegistry.CRIMSON_BIG_TABLE.get());
        output.accept(ObjectRegistry.CHERRY_CABINET.get());
        output.accept(ObjectRegistry.CHERRY_DRAWER.get());
        output.accept(ObjectRegistry.CHERRY_TABLE.get());
        output.accept(ObjectRegistry.CHERRY_CHAIR.get());
        output.accept(ObjectRegistry.CHERRY_SHELF.get());
        output.accept(ObjectRegistry.CHERRY_BIG_TABLE.get());
        output.accept(ObjectRegistry.BAMBOO_CABINET.get());
        output.accept(ObjectRegistry.BAMBOO_DRAWER.get());
        output.accept(ObjectRegistry.BAMBOO_TABLE.get());
        output.accept(ObjectRegistry.BAMBOO_CHAIR.get());
        output.accept(ObjectRegistry.BAMBOO_SHELF.get());
        output.accept(ObjectRegistry.BAMBOO_BIG_TABLE.get());
    }

    public static void init() {
        TABS.register();
    }
}
