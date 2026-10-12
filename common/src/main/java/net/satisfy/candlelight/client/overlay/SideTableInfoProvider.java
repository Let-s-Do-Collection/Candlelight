package net.satisfy.candlelight.client.overlay;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.satisfy.candlelight.core.block.SideTableBlock;
import net.satisfy.candlelight.core.block.entity.SideTableBlockEntity;
import net.satisfy.candlelight.platform.PlatformHelper;
import net.satisfy.foundation.overlay.BlockInfoProvider;
import net.satisfy.foundation.overlay.InfoSection;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SideTableInfoProvider implements BlockInfoProvider {
    @Override
    public List<InfoSection> describe(Level level, BlockPos pos, BlockState state, @Nullable BlockHitResult hit) {
        if (!PlatformHelper.showSideTableInfo() || !(state.getBlock() instanceof SideTableBlock) || !(level.getBlockEntity(pos) instanceof SideTableBlockEntity table)) {
            return List.of();
        }
        if (state.getValue(SideTableBlock.LIGHT) != SideTableBlock.Light.NONE) {
            ItemStack light = table.getLight();
            boolean lit = state.getValue(SideTableBlock.LIT);
            Component status = Component.translatable(lit ? "hud.candlelight.side_table.on" : "hud.candlelight.side_table.off").withStyle(lit ? ChatFormatting.GOLD : ChatFormatting.GRAY);
            return List.of(InfoSection.rows(status, List.of(InfoSection.Row.item(light, light.getHoverName()))));
        }
        ItemStack items = table.getItems();
        if (items.isEmpty()) {
            return List.of();
        }
        return List.of(InfoSection.icons(Component.translatable("hud.candlelight.side_table.on_table"), List.of(items), InfoSection.ROW_COLUMNS).withDecorations());
    }
}
