package net.satisfy.candlelight.client.renderer.block;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.satisfy.candlelight.Candlelight;

import java.util.List;
import java.util.function.Function;

public final class SideTableBookModels {
    public static final List<ResourceLocation> IDS = List.of(
            Candlelight.identifier("block/side_table_book_0"),
            Candlelight.identifier("block/side_table_book_1"),
            Candlelight.identifier("block/side_table_book_2"),
            Candlelight.identifier("block/side_table_book_3"));

    private static Function<ResourceLocation, BakedModel> lookup = id -> null;

    private SideTableBookModels() {
    }

    public static void setLookup(Function<ResourceLocation, BakedModel> lookup) {
        SideTableBookModels.lookup = lookup;
    }

    public static BakedModel get(int index) {
        return lookup.apply(IDS.get(Math.floorMod(index, IDS.size())));
    }
}
