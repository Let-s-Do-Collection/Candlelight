package net.satisfy.candlelight.core.block.entity;

import net.minecraft.tags.ItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.candlelight.core.registry.EntityTypeRegistry;
import org.jetbrains.annotations.NotNull;

public class SideTableBlockEntity extends BlockEntity {
    public static final int MAX_ITEMS = 4;
    public static final int MAX_BOOKS = 2;

    public static int maxFor(ItemStack stack) {
        return stack.is(ItemTags.BOOKSHELF_BOOKS) ? MAX_BOOKS : MAX_ITEMS;
    }
    private static final String ITEMS_KEY = "Items";
    private static final String LIGHT_KEY = "Light";

    private ItemStack items = ItemStack.EMPTY;
    private ItemStack light = ItemStack.EMPTY;

    public SideTableBlockEntity(BlockPos pos, BlockState state) {
        super(EntityTypeRegistry.SIDE_TABLE_BLOCK_ENTITY.get(), pos, state);
    }

    public ItemStack getItems() {
        return items;
    }

    public void setItems(ItemStack items) {
        this.items = items;
        sync();
    }

    public ItemStack getLight() {
        return light;
    }

    public void setLight(ItemStack light) {
        this.light = light;
        sync();
    }

    private void sync() {
        setChanged();
        if (level != null && !level.isClientSide()) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        items = ItemStack.parseOptional(provider, tag.getCompound(ITEMS_KEY));
        light = ItemStack.parseOptional(provider, tag.getCompound(LIGHT_KEY));
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        tag.put(ITEMS_KEY, items.isEmpty() ? new CompoundTag() : items.save(provider, new CompoundTag()));
        tag.put(LIGHT_KEY, light.isEmpty() ? new CompoundTag() : light.save(provider, new CompoundTag()));
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        return saveWithoutMetadata(provider);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
