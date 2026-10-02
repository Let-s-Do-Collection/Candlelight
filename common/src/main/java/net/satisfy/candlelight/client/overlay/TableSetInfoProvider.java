package net.satisfy.candlelight.client.overlay;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.satisfy.candlelight.core.block.TableSetBlock;
import net.satisfy.candlelight.core.block.entity.TableSetBlockEntity;
import net.satisfy.candlelight.core.registry.ObjectRegistry;
import net.satisfy.candlelight.core.util.Wearables;
import net.satisfy.foundation.overlay.BlockInfoProvider;
import net.satisfy.foundation.overlay.InfoSection;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TableSetInfoProvider implements BlockInfoProvider {
    @Override
    public List<InfoSection> describe(Level level, BlockPos pos, BlockState state, @Nullable BlockHitResult hit) {
        if (!(state.getBlock() instanceof TableSetBlock) || !(level.getBlockEntity(pos) instanceof TableSetBlockEntity tableSet)) {
            return List.of();
        }
        Player player = Minecraft.getInstance().player;
        if (player == null) {
            return List.of();
        }

        List<InfoSection> sections = new ArrayList<>();
        ItemStack dish = tableSet.getInventory().isEmpty() ? ItemStack.EMPTY : tableSet.getInventory().get(0);
        if (dish.isEmpty()) {
            sections.add(InfoSection.lines(Component.translatable("hud.candlelight.table_set.dish"),
                    List.of(Component.translatable("hud.candlelight.table_set.no_dish").withStyle(ChatFormatting.GRAY))));
        } else {
            Component note = TableSetBlock.isServedDish(dish)
                    ? Component.translatable("hud.candlelight.table_set.served_dish").withStyle(ChatFormatting.GREEN)
                    : Component.translatable("hud.candlelight.table_set.plain_food").withStyle(ChatFormatting.GRAY);
            sections.add(InfoSection.rows(Component.translatable("hud.candlelight.table_set.dish"), List.of(InfoSection.Row.item(dish, note))));
        }

        int courses = TableSetBlock.countCourses(level, pos, state, player);
        List<InfoSection.Row> rows = new ArrayList<>();
        rows.add(row(ObjectRegistry.NAPKIN.get().getDefaultInstance(), state, TableSetBlock.NAPKIN, "napkin"));
        rows.add(row(ObjectRegistry.GLASS.get().getDefaultInstance(), state, TableSetBlock.GLASS, "glass"));
        rows.add(row(ObjectRegistry.WINE_GLASS.get().getDefaultInstance(), state, TableSetBlock.WINE_GLASS, "wine_glass"));
        rows.add(InfoSection.Row.item(ObjectRegistry.GLASS.get().getDefaultInstance(), status(TableSetBlock.hasDrink(state), "drink")));
        rows.add(InfoSection.Row.item(Items.CANDLE.getDefaultInstance(), status(TableSetBlock.hasLight(level, pos), "light")));
        rows.add(InfoSection.Row.item(ObjectRegistry.DRESS.get().getDefaultInstance(), status(Wearables.isElegantlyDressed(player), "outfit")));
        sections.add(InfoSection.rows(Component.translatable("hud.candlelight.table_set.courses", courses, TableSetBlock.MAX_COURSES), rows));

        if (state.getValue(TableSetBlock.CLOCHE)) {
            sections.add(InfoSection.lines(Component.translatable("hud.candlelight.table_set.cloche").withStyle(ChatFormatting.GOLD),
                    List.of(Component.translatable("hud.candlelight.table_set.cloche_hint").withStyle(ChatFormatting.GRAY))));
        }
        return sections;
    }

    private static InfoSection.Row row(ItemStack icon, BlockState state, BooleanProperty property, String key) {
        return InfoSection.Row.item(icon, status(state.getValue(property), key));
    }

    private static Component status(boolean done, String key) {
        Component name = Component.translatable("hud.candlelight.table_set." + key);
        return done
                ? Component.literal("✔ ").append(name).withStyle(ChatFormatting.GREEN)
                : Component.literal("✘ ").append(name).withStyle(ChatFormatting.GRAY);
    }
}
