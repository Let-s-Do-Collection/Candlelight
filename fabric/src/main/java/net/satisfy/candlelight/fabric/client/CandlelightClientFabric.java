package net.satisfy.candlelight.fabric.client;

import net.satisfy.foundation.fabric.client.FoundationArmorRenderer;
import net.satisfy.candlelight.client.renderer.block.SideTableBookModels;
import net.minecraft.client.Minecraft;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.loader.api.FabricLoader;
import net.satisfy.candlelight.fabric.client.compat.TrinketsClientCompat;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.satisfy.candlelight.client.CandlelightClient;
import net.satisfy.candlelight.core.registry.ObjectRegistry;

public class CandlelightClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CandlelightClient.preInitClient();
        CandlelightClient.initClient();
        ModelLoadingPlugin.register(context -> context.addModels(SideTableBookModels.IDS));
        SideTableBookModels.setLookup(id -> Minecraft.getInstance().getModelManager().getModel(id));

        ArmorRenderer.register(FoundationArmorRenderer.INSTANCE, ObjectRegistry.COOKING_HAT.get(), ObjectRegistry.FLOWER_CROWN.get(), ObjectRegistry.NECKTIE.get(),
                ObjectRegistry.CHEFS_JACKET.get(), ObjectRegistry.FORMAL_SHIRT.get(), ObjectRegistry.SHIRT.get(), ObjectRegistry.DRESS.get(),
                ObjectRegistry.CHEFS_PANTS.get(), ObjectRegistry.TROUSERS_AND_VEST.get(), ObjectRegistry.CHEFS_BOOTS.get());

        if (FabricLoader.getInstance().isModLoaded("trinkets")) {
            TrinketsClientCompat.init();
        }
    }
}
