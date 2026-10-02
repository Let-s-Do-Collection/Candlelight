package net.satisfy.candlelight.forge.client;

import net.neoforged.fml.ModList;
import net.satisfy.candlelight.forge.client.compat.CuriosClientCompat;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.satisfy.candlelight.Candlelight;
import net.satisfy.candlelight.client.CandlelightClient;
import net.satisfy.candlelight.client.gui.LetterGui;
import net.satisfy.candlelight.core.registry.ScreenHandlerTypeRegistry;
import net.satisfy.candlelight.forge.client.extensions.*;

import static net.satisfy.candlelight.core.registry.ObjectRegistry.*;

@SuppressWarnings("removal")
@EventBusSubscriber(modid = Candlelight.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class CandlelightClientForge {

    @SubscribeEvent
    public static void beforeClientSetup(RegisterEvent event) {
        CandlelightClient.preInitClient();
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        CandlelightClient.initClient();
        if (ModList.get().isLoaded("curios")) {
            event.enqueueWork(CuriosClientCompat::init);
        }
    }

    @SubscribeEvent
    public static void clientSetup(RegisterMenuScreensEvent event) {
        event.register(ScreenHandlerTypeRegistry.LETTER_SCREEN_HANDLER.get(), LetterGui::new);
    }

    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(new CandlelightHatExtensions(), FLOWER_CROWN.get(), COOKING_HAT.get(), NECKTIE.get());
        event.registerItem(new CandlelightChestplateExtensions(), CHEFS_JACKET.get(), SHIRT.get(), FORMAL_SHIRT.get());
        event.registerItem(new CandlelightLeggingsExtensions(), CHEFS_PANTS.get());
        event.registerItem(new CandlelightBootsExtensions(), CHEFS_BOOTS.get());
        event.registerItem(new DyeableCandlelightArmorExtensions(), TROUSERS_AND_VEST.get(), DRESS.get());
    }
}
