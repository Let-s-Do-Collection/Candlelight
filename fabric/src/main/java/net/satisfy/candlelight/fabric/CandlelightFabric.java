package net.satisfy.candlelight.fabric;

import net.satisfy.candlelight.fabric.config.CandlelightFabricConfig;
import net.minecraft.world.InteractionResult;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.satisfy.candlelight.fabric.compat.TrinketsCompat;
import net.fabricmc.api.ModInitializer;
import net.satisfy.candlelight.Candlelight;
import net.satisfy.candlelight.core.registry.CompostableRegistry;
import net.satisfy.candlelight.fabric.world.CandlelightBiomeModification;

public class CandlelightFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ConfigHolder<CandlelightFabricConfig> config = AutoConfig.register(CandlelightFabricConfig.class, GsonConfigSerializer::new);
        config.getConfig().apply();
        config.registerSaveListener((holder, data) -> {
            data.apply();
            return InteractionResult.SUCCESS;
        });
        Candlelight.init();
        if (FabricLoader.getInstance().isModLoaded("trinkets")) {
            TrinketsCompat.init();
        }
        CompostableRegistry.init();
        CandlelightBiomeModification.init();

        FabricLoader.getInstance().getModContainer(Candlelight.MOD_ID).ifPresent(container ->
                ResourceManagerHelper.registerBuiltinResourcePack(
                        ResourceLocation.fromNamespaceAndPath(Candlelight.MOD_ID, "vanilla_blend"),
                        container,
                        Component.translatable("pack.candlelight.vanilla_blend"),
                        ResourcePackActivationType.NORMAL));
    }
}
