package net.satisfy.candlelight.core.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.candlelight.core.registry.EntityTypeRegistry;

public class DinnerBellBlockEntity extends BlockEntity {
    public static final int RING_EVENT = 1;

    private long ringTime = Long.MIN_VALUE;

    public DinnerBellBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(EntityTypeRegistry.DINNER_BELL_BLOCK_ENTITY.get(), blockPos, blockState);
    }

    @Override
    public boolean triggerEvent(int id, int param) {
        if (id == RING_EVENT) {
            if (level != null) {
                ringTime = level.getGameTime();
            }
            return true;
        }
        return super.triggerEvent(id, param);
    }

    public float getRingProgress(float partialTicks) {
        if (level == null || ringTime == Long.MIN_VALUE) {
            return -1.0F;
        }
        return level.getGameTime() - ringTime + partialTicks;
    }
}
