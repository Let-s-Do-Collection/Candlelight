package net.satisfy.candlelight.core.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.candlelight.core.block.entity.TableSignBlockEntity;
import org.jetbrains.annotations.Nullable;

public class TableSignBlock extends BoardBlock implements EntityBlock {
    public TableSignBlock(Properties settings) {
        super(settings);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TableSignBlockEntity(pos, state);
    }
}
