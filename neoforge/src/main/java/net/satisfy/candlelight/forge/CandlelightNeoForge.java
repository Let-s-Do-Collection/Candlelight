package net.satisfy.candlelight.forge;

import net.satisfy.candlelight.forge.config.CandlelightNeoForgeConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import org.jetbrains.annotations.Nullable;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.network.chat.Component;
import java.util.Optional;
import java.nio.file.Path;
import java.nio.file.Files;
import java.net.URL;
import java.net.URISyntaxException;
import net.neoforged.fml.ModList;
import net.satisfy.candlelight.forge.compat.CuriosCompat;
import dev.architectury.platform.hooks.EventBusesHooks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.satisfy.candlelight.Candlelight;
import net.satisfy.candlelight.core.registry.CompostableRegistry;

@Mod(Candlelight.MOD_ID)
public class CandlelightNeoForge {

    public CandlelightNeoForge(final IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, CandlelightNeoForgeConfig.COMMON);
        modEventBus.addListener((ModConfigEvent.Loading event) -> {
            if (event.getConfig().getSpec() == CandlelightNeoForgeConfig.COMMON) CandlelightNeoForgeConfig.applyCommon();
        });
        modEventBus.addListener((ModConfigEvent.Reloading event) -> {
            if (event.getConfig().getSpec() == CandlelightNeoForgeConfig.COMMON) CandlelightNeoForgeConfig.applyCommon();
        });
        EventBusesHooks.whenAvailable(Candlelight.MOD_ID, IEventBus::start);
        Candlelight.init();
        if (ModList.get().isLoaded("curios")) {
            CuriosCompat.init();
        }
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(CandlelightNeoForge::addBuiltinPacks);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            CompostableRegistry.init();
        });
    }

    private static void addBuiltinPacks(AddPackFindersEvent event) {
        if (event.getPackType() != PackType.CLIENT_RESOURCES) return;
        Path root = findBuiltinPack("vanilla_blend");
        if (root == null) return;
        PackLocationInfo info = new PackLocationInfo("mod/" + Candlelight.MOD_ID + ":vanilla_blend",
                Component.translatable("pack.candlelight.vanilla_blend"), PackSource.BUILT_IN, Optional.empty());
        Pack pack = Pack.readMetaAndCreate(info, new PathPackResources.PathResourcesSupplier(root),
                PackType.CLIENT_RESOURCES, new PackSelectionConfig(false, Pack.Position.TOP, false));
        if (pack != null) {
            event.addRepositorySource(consumer -> consumer.accept(pack));
        }
    }

    private static @Nullable Path findBuiltinPack(String name) {
        Path modPath = ModList.get().getModFileById(Candlelight.MOD_ID).getFile().findResource("resourcepacks", name);
        if (Files.exists(modPath.resolve("pack.mcmeta"))) return modPath;
        try {
            URL url = CandlelightNeoForge.class.getResource("/resourcepacks/" + name + "/pack.mcmeta");
            if (url != null && "file".equals(url.getProtocol())) return Path.of(url.toURI()).getParent();
        } catch (URISyntaxException ignored) {
        }
        return null;
    }
}
