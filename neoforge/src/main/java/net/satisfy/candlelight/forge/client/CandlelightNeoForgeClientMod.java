package net.satisfy.candlelight.forge.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.satisfy.candlelight.Candlelight;

@Mod(value = Candlelight.MOD_ID, dist = Dist.CLIENT)
public class CandlelightNeoForgeClientMod {
    public CandlelightNeoForgeClientMod(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
}
