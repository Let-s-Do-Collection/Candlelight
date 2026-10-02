package net.satisfy.candlelight.core.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.satisfy.candlelight.core.block.CookingPanBlock;
import net.satisfy.candlelight.core.registry.EntityTypeRegistry;
import net.satisfy.candlelight.core.util.Wearables;
import net.satisfy.farm_and_charm.core.block.entity.RoasterBlockEntity;
import org.jetbrains.annotations.NotNull;

public class CookingPanBlockEntity extends RoasterBlockEntity {
    private static final float CHEF_SET_EXTRA_PORTION_CHANCE = 0.25F;

    private int storedDamage;

    public CookingPanBlockEntity(BlockPos pos, BlockState state) {
        super(EntityTypeRegistry.COOKING_PAN_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    protected BooleanProperty getLitProperty() {
        return CookingPanBlock.LIT;
    }

    @Override
    protected BooleanProperty getRoastingProperty() {
        return CookingPanBlock.COOKING;
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        storedDamage = tag.getInt("StoredDamage");
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        tag.putInt("StoredDamage", storedDamage);
    }

    public int getStoredDamage() {
        return storedDamage;
    }

    public void setStoredDamage(int storedDamage) {
        this.storedDamage = storedDamage;
    }

    @Override
    protected int getExtraOutputCount(ItemStack output) {
        Player owner = getOwner();
        if (owner == null || level == null || !Wearables.hasChefSet(owner)) return 0;
        return level.random.nextFloat() < CHEF_SET_EXTRA_PORTION_CHANCE ? 1 : 0;
    }
}
