package net.satisfy.candlelight.core.block.entity;

import net.satisfy.candlelight.core.config.CandlelightConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.satisfy.candlelight.core.block.LargeCookingPotBlock;
import net.satisfy.candlelight.core.registry.EntityTypeRegistry;
import net.satisfy.candlelight.core.util.Wearables;
import net.satisfy.farm_and_charm.core.block.entity.CookingPotBlockEntity;

public class LargeCookingPotBlockEntity extends CookingPotBlockEntity {
    public LargeCookingPotBlockEntity(BlockPos pos, BlockState state) {
        super(EntityTypeRegistry.LARGE_COOKING_POT_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    protected BooleanProperty getLitProperty() {
        return LargeCookingPotBlock.LIT;
    }

    @Override
    protected BooleanProperty getCookingProperty() {
        return LargeCookingPotBlock.COOKING;
    }

    @Override
    protected int getExtraOutputCount(ItemStack output) {
        Player owner = getOwner();
        if (owner == null || level == null || !Wearables.hasChefSet(owner)) return 0;
        return level.random.nextFloat() < CandlelightConfig.chefSetExtraPortionChance ? 1 : 0;
    }
}
