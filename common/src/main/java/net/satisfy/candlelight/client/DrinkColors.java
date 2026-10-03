package net.satisfy.candlelight.client;

import com.mojang.blaze3d.platform.NativeImage;
import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.BlockAndTintGetter;
import net.satisfy.candlelight.core.block.entity.TableSetBlockEntity;
import net.satisfy.candlelight.core.registry.ObjectRegistry;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public final class DrinkColors {
    private static final int DEFAULT_COLOR = 0xFFC0803A;
    private static final float MIN_SATURATION = 0.25F;
    private static final Map<Item, Integer> COLORS = new ConcurrentHashMap<>();

    private DrinkColors() {
    }

    public static void init() {
        ColorHandlerRegistry.registerBlockColors((state, level, pos, tintIndex) -> color(level, pos, tintIndex), ObjectRegistry.TABLE_SET);
    }

    private static int color(BlockAndTintGetter level, BlockPos pos, int tintIndex) {
        if (level == null || pos == null || tintIndex < 0 || !(level.getBlockEntity(pos) instanceof TableSetBlockEntity tableSet)) {
            return DEFAULT_COLOR;
        }
        ItemStack drink = tableSet.getDrink(tintIndex == 1);
        if (drink.isEmpty()) {
            return DEFAULT_COLOR;
        }
        PotionContents potion = drink.get(DataComponents.POTION_CONTENTS);
        if (potion != null) {
            return potion.getColor() | 0xFF000000;
        }
        return COLORS.computeIfAbsent(drink.getItem(), item -> averageColor(new ItemStack(item)));
    }

    private static int averageColor(ItemStack stack) {
        TextureAtlasSprite sprite = Minecraft.getInstance().getItemRenderer().getModel(stack, null, null, 0).getParticleIcon();
        ResourceLocation name = sprite.contents().name();
        ResourceLocation file = ResourceLocation.fromNamespaceAndPath(name.getNamespace(), "textures/" + name.getPath() + ".png");
        Optional<Resource> resource = Minecraft.getInstance().getResourceManager().getResource(file);
        if (resource.isEmpty()) {
            return DEFAULT_COLOR;
        }
        try (InputStream stream = resource.get().open(); NativeImage image = NativeImage.read(stream)) {
            return average(image);
        } catch (IOException exception) {
            return DEFAULT_COLOR;
        }
    }

    private static int average(NativeImage image) {
        long red = 0;
        long green = 0;
        long blue = 0;
        int count = 0;
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int abgr = image.getPixelRGBA(x, y);
                if ((abgr >>> 24) < 128) continue;
                int r = abgr & 0xFF;
                int g = (abgr >> 8) & 0xFF;
                int b = (abgr >> 16) & 0xFF;
                int max = Math.max(r, Math.max(g, b));
                int min = Math.min(r, Math.min(g, b));
                if (max == 0 || (max - min) / (float) max < MIN_SATURATION) continue;
                red += r;
                green += g;
                blue += b;
                count++;
            }
        }
        if (count == 0) {
            return DEFAULT_COLOR;
        }
        return 0xFF000000 | (int) (red / count) << 16 | (int) (green / count) << 8 | (int) (blue / count);
    }
}
