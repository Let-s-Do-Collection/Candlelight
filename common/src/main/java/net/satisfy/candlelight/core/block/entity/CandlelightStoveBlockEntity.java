package net.satisfy.candlelight.core.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.candlelight.core.registry.EntityTypeRegistry;
import net.satisfy.farm_and_charm.core.block.entity.StoveBlockEntity;
import org.jetbrains.annotations.NotNull;

public class CandlelightStoveBlockEntity extends StoveBlockEntity {
    public CandlelightStoveBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(blockPos, blockState);
    }

    @Override
    public boolean isValidBlockState(BlockState blockState) {
        return getType().isValid(blockState);
    }

    @Override
    public @NotNull BlockEntityType<?> getType() {
        return EntityTypeRegistry.STOVE_BLOCK_ENTITY.get();
    }
}