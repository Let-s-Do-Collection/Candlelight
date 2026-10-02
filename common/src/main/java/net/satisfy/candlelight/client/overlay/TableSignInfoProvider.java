package net.satisfy.candlelight.client.overlay;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.satisfy.candlelight.core.block.entity.TableSignBlockEntity;
import net.satisfy.candlelight.core.item.TableSignItem;
import net.satisfy.foundation.overlay.BlockInfoProvider;
import net.satisfy.foundation.overlay.InfoSection;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TableSignInfoProvider implements BlockInfoProvider {
    @Override
    public List<InfoSection> describe(Level level, BlockPos pos, BlockState state, @Nullable BlockHitResult hit) {
        if (!(level.getBlockEntity(pos) instanceof TableSignBlockEntity sign)) {
            return List.of();
        }
        List<String> lines = TableSignItem.getLines(sign.getText());
        if (lines.isEmpty()) {
            return List.of();
        }
        List<Component> components = lines.stream()
                .<Component>map(line -> Component.literal(line).withStyle(ChatFormatting.WHITE))
                .toList();
        return List.of(InfoSection.lines(state.getBlock().getName(), components));
    }
}
