package net.satisfy.candlelight.forge;

import net.neoforged.fml.ModList;
import net.satisfy.candlelight.forge.compat.CuriosCompat;
import dev.architectury.platform.hooks.EventBusesHooks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.satisfy.candlelight.Candlelight;
import net.satisfy.candlelight.core.registry.CompostableRegistry;
import net.satisfy.candlelight.core.registry.FlammableBlockRegistry;

@Mod(Candlelight.MOD_ID)
public class CandlelightNeoForge {

    public CandlelightNeoForge(final IEventBus modEventBus) {
        EventBusesHooks.whenAvailable(Candlelight.MOD_ID, IEventBus::start);
        Candlelight.init();
        if (ModList.get().isLoaded("curios")) {
            CuriosCompat.init();
        }
        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            CompostableRegistry.init();
            FlammableBlockRegistry.init();
        });
        Candlelight.commonInit();
    }
}