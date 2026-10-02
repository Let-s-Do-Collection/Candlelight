package net.satisfy.candlelight.fabric;

import net.fabricmc.loader.api.FabricLoader;
import net.satisfy.candlelight.fabric.compat.TrinketsCompat;
import net.fabricmc.api.ModInitializer;
import net.satisfy.candlelight.Candlelight;
import net.satisfy.candlelight.core.registry.CompostableRegistry;
import net.satisfy.candlelight.fabric.world.CandlelightBiomeModification;

public class CandlelightFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Candlelight.init();
        if (FabricLoader.getInstance().isModLoaded("trinkets")) {
            TrinketsCompat.init();
        }
        CompostableRegistry.init();
        CandlelightBiomeModification.init();
    }
}
