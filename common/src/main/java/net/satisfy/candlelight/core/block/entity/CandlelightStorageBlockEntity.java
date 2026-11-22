package net.satisfy.candlelight.core.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.candlelight.core.registry.EntityTypeRegistry;
import net.satisfy.foundation.storage.StorageBlock;
import net.satisfy.foundation.storage.StorageBlockEntity;

public class CandlelightStorageBlockEntity extends StorageBlockEntity {
    public CandlelightStorageBlockEntity(BlockPos pos, BlockState state) {
        super(EntityTypeRegistry.STORAGE_BLOCK_ENTITY.get(), pos, state);
    }

    public CandlelightStorageBlockEntity(BlockPos pos, BlockState state, int size) {
        super(EntityTypeRegistry.STORAGE_BLOCK_ENTITY.get(), pos, state, size);
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        return slot >= 0 && slot < getItems().size() && getItems().get(slot).isEmpty() && getBlockState().getBlock() instanceof StorageBlock storage && storage.canInsertStack(stack);
    }
}
