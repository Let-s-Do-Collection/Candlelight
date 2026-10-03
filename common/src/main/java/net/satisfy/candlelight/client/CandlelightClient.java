package net.satisfy.candlelight.client;

import net.satisfy.candlelight.client.overlay.SideTableInfoProvider;
import net.satisfy.candlelight.client.renderer.block.SideTableRenderer;
import net.satisfy.foundation.client.armor.ArmorColors;
import net.satisfy.foundation.client.render.WallDecorationRenderer;
import net.satisfy.foundation.banner.CompletionistBannerRenderer;
import net.satisfy.foundation.storage.StorageBlockEntityRenderer;
import net.satisfy.foundation.storage.StorageTypeRenderer;
import dev.architectury.platform.Platform;
import net.minecraft.network.chat.Component;
import net.satisfy.candlelight.core.registry.TabRegistry;
import net.satisfy.foundation.client.creative.CreativeSideTabs;
import net.minecraft.world.InteractionHand;
import net.satisfy.candlelight.client.gui.TableSignEditScreen;
import net.satisfy.candlelight.client.overlay.TableSignInfoProvider;
import net.satisfy.candlelight.client.overlay.TableSetInfoProvider;
import net.satisfy.foundation.overlay.BlockInfoOverlay;
import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import net.satisfy.candlelight.client.compat.AccessoriesClientCompat;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import dev.architectury.registry.menu.MenuRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.satisfy.candlelight.client.gui.LetterGui;
import net.satisfy.candlelight.client.model.*;
import net.satisfy.candlelight.client.renderer.block.*;
import net.satisfy.candlelight.core.registry.EntityTypeRegistry;
import net.satisfy.candlelight.core.registry.ScreenHandlerTypeRegistry;
import net.satisfy.candlelight.core.registry.StorageTypeRegistry;

import static net.satisfy.candlelight.core.registry.ObjectRegistry.*;

@Environment(EnvType.CLIENT)
public class CandlelightClient {

    public static void initClient() {
        RenderTypeRegistry.register(RenderType.cutout(), TABLE.get(), ROSE.get(), POTTED_ROSE.get(), GLASS_BLOCK.get(),
                OAK_CHAIR.get(), DARK_OAK_CHAIR.get(), SPRUCE_CHAIR.get(), WARPED_CHAIR.get(),
                BIRCH_CHAIR.get(), MANGROVE_CHAIR.get(), ACACIA_CHAIR.get(), CRIMSON_CHAIR.get(),
                JUNGLE_CHAIR.get(), OAK_TABLE.get(), ACACIA_TABLE.get(), DARK_OAK_TABLE.get(),
                BIRCH_TABLE.get(), SPRUCE_TABLE.get(), JUNGLE_TABLE.get(), MANGROVE_TABLE.get(),
                WARPED_TABLE.get(), CRIMSON_TABLE.get(), CHAIR.get(), TABLE.get(), BAMBOO_CHAIR.get(),
                BAMBOO_TABLE.get(), CHERRY_TABLE.get(), CHERRY_CHAIR.get(), WINE_GLASS_BLOCK.get(),
                COOKING_POT.get(), COOKING_PAN.get(), RED_NETHER_BRICKS_STOVE.get(), QUARTZ_STOVE.get(),
                MUD_STOVE.get(), END_STOVE.get(), GRANITE_STOVE.get(), DEEPSLATE_STOVE.get(), SANDSTONE_STOVE.get(),
                STONE_BRICKS_STOVE.get(), COBBLESTONE_STOVE.get(), BAMBOO_STOVE.get()
        );

        RenderTypeRegistry.register(RenderType.translucent(), WINE_GLASS_BLOCK.get(), TABLE_SET.get(), GLASS_BLOCK.get());

        BlockEntityRendererRegistry.register(EntityTypeRegistry.CANDLELIGHT_BANNER_ENTITY.get(), CompletionistBannerRenderer::new);
        BlockEntityRendererRegistry.register(EntityTypeRegistry.STORAGE_BLOCK_ENTITY.get(), context -> new StorageBlockEntityRenderer());
        BlockEntityRendererRegistry.register(EntityTypeRegistry.SIDE_TABLE_BLOCK_ENTITY.get(), SideTableRenderer::new);
        BlockEntityRendererRegistry.register(EntityTypeRegistry.TABLE_SET_BLOCK_ENTITY.get(), context -> new StorageBlockEntityRenderer());
        BlockEntityRendererRegistry.register(EntityTypeRegistry.DINNER_BELL_BLOCK_ENTITY.get(), DinnerBellRenderer::new);
        BlockEntityRendererRegistry.register(EntityTypeRegistry.TYPE_WRITER_BLOCK_ENTITY.get(), TypewriterRenderer::new);
        BlockEntityRendererRegistry.register(EntityTypeRegistry.WALL_DECORATION.get(), WallDecorationRenderer::new);

        MenuRegistry.registerScreenFactory(ScreenHandlerTypeRegistry.LETTER_SCREEN_HANDLER.get(), LetterGui::new);

        registerStorageType();

        ArmorColors.register(DRESS.get(), TROUSERS_AND_VEST.get());

        DrinkColors.init();
        CreativeSideTabs.register(TabRegistry.CANDLELIGHT_TAB.getKey(),
                CreativeSideTabs.SideTab.of(Component.translatable("creativetab.candlelight.side.food"), BEEF_WELLINGTON.get(), TabRegistry::acceptMain),
                CreativeSideTabs.SideTab.of(Component.translatable("creativetab.candlelight.side.kitchen_blocks"), COBBLESTONE_STOVE.get(), TabRegistry::acceptKitchen),
                CreativeSideTabs.SideTab.of(Component.translatable("creativetab.candlelight.side.furniture"), OAK_CHAIR.get(), TabRegistry::acceptFurniture));
        BlockInfoOverlay.init();
        BlockInfoOverlay.registerProvider(new TableSetInfoProvider());
        BlockInfoOverlay.registerProvider(new TableSignInfoProvider());
        BlockInfoOverlay.registerProvider(new SideTableInfoProvider());
        if (Platform.isModLoaded("accessories")) {
            AccessoriesClientCompat.init();
        }
    }

    public static void openTableSignScreen(InteractionHand hand, String text) {
        Minecraft.getInstance().setScreen(new TableSignEditScreen(hand, text));
    }

    public static void registerStorageType(ResourceLocation location, StorageTypeRenderer renderer) {
        StorageBlockEntityRenderer.registerStorageType(location, renderer);
    }

    public static void registerStorageType() {
        registerStorageType(StorageTypeRegistry.SHELF, new ShelfRenderer());
        registerStorageType(StorageTypeRegistry.TABLE_SET, new TableSetRenderer());
        registerStorageType(StorageTypeRegistry.JEWELRY_BOX, new JewelryRenderer());
    }

    public static void preInitClient() {
        registerEntityModelLayers();
    }

    public static void registerEntityModelLayers() {
        EntityModelLayerRegistry.register(TypewriterModel.LAYER_LOCATION, TypewriterModel::getTexturedModelData);
        EntityModelLayerRegistry.register(DinnerBellModel.LAYER_LOCATION, DinnerBellModel::getTexturedModelData);
        EntityModelLayerRegistry.register(FlowerCrownModel.LAYER_LOCATION, FlowerCrownModel::createBodyLayer);
        EntityModelLayerRegistry.register(TieModel.LAYER_LOCATION, TieModel::createBodyLayer);
        EntityModelLayerRegistry.register(DressChestplateModel.LAYER_LOCATION, DressChestplateModel::createBodyLayer);
        EntityModelLayerRegistry.register(CookingHatModel.LAYER_LOCATION, CookingHatModel::createBodyLayer);
        EntityModelLayerRegistry.register(CookingChestplateModel.LAYER_LOCATION, CookingChestplateModel::createBodyLayer);
        EntityModelLayerRegistry.register(CookingLeggingsModel.LAYER_LOCATION, CookingLeggingsModel::createBodyLayer);
        EntityModelLayerRegistry.register(CookingBootsModel.LAYER_LOCATION, CookingBootsModel::createBodyLayer);
    }
}