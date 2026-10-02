package net.satisfy.candlelight.core.block;

import net.minecraft.world.level.block.BaseEntityBlock;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.candlelight.core.registry.EntityTypeRegistry;
import net.satisfy.foundation.food.IngredientEffectFoodBlock;
import net.satisfy.foundation.food.IngredientEffectFoodBlockEntity;
import net.satisfy.foundation.food.IngredientEffects;

public class CEffectFoodBlock extends IngredientEffectFoodBlock {
    private final int maxBites;
    private final FoodProperties food;

    public CEffectFoodBlock(Properties properties, int maxBites, FoodProperties food) {
        super(properties, maxBites, food);
        this.maxBites = maxBites;
        this.food = food;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(properties -> new CEffectFoodBlock(properties, this.maxBites, this.food));
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof IngredientEffectFoodBlockEntity e) {
            e.addEffects(IngredientEffects.getEffects(stack));
            e.setChanged();
        } else {
            super.setPlacedBy(level, pos, state, placer, stack);
        }
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new IngredientEffectFoodBlockEntity(EntityTypeRegistry.EFFECT_FOOD_BLOCK_ENTITY.get(), pos, state);
    }
}
