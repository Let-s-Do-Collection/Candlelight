package net.satisfy.candlelight.fabric.client;

import net.satisfy.candlelight.client.renderer.block.SideTableBookModels;
import net.minecraft.client.Minecraft;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.loader.api.FabricLoader;
import net.satisfy.candlelight.fabric.client.compat.TrinketsClientCompat;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.satisfy.candlelight.client.CandlelightClient;
import net.satisfy.candlelight.core.registry.ObjectRegistry;
import net.satisfy.candlelight.fabric.client.renderer.*;

public class CandlelightClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CandlelightClient.preInitClient();
        CandlelightClient.initClient();
        ModelLoadingPlugin.register(context -> context.addModels(SideTableBookModels.IDS));
        SideTableBookModels.setLookup(id -> Minecraft.getInstance().getModelManager().getModel(id));

        ArmorRenderer.register(new CandlelightHatRenderer(), ObjectRegistry.COOKING_HAT.get(), ObjectRegistry.FLOWER_CROWN.get(), ObjectRegistry.NECKTIE.get());
        ArmorRenderer.register(new CandlelightChestplateRenderer(), ObjectRegistry.CHEFS_JACKET.get(), ObjectRegistry.FORMAL_SHIRT.get(), ObjectRegistry.SHIRT.get());
        ArmorRenderer.register(new CandlelightLeggingsRenderer(), ObjectRegistry.CHEFS_PANTS.get());
        ArmorRenderer.register(new CandlelightBootsRenderer(), ObjectRegistry.CHEFS_BOOTS.get());
        ArmorRenderer.register(new DyeableCandlelightChestplateRenderer(), ObjectRegistry.DRESS.get());
        ArmorRenderer.register(new DyeableCandlelightLeggingsRenderer(), ObjectRegistry.TROUSERS_AND_VEST.get());

        if (FabricLoader.getInstance().isModLoaded("trinkets")) {
            TrinketsClientCompat.init();
        }
    }
}
