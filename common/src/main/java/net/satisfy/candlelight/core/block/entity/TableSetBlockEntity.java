package net.satisfy.candlelight.core.block.entity;

import java.util.UUID;
import java.util.Comparator;
import net.satisfy.candlelight.core.util.DinnerGuest;
import net.satisfy.candlelight.core.block.TableSetBlock;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.candlelight.core.registry.EntityTypeRegistry;
import net.satisfy.foundation.storage.StorageBlockEntity;

public class TableSetBlockEntity extends StorageBlockEntity {
    private static final String GLASS_DRINK_KEY = "EffectStack";
    private static final String WINE_GLASS_DRINK_KEY = "WineGlassDrink";

    private static final int INVITE_INTERVAL = 200;
    private static final float INVITE_CHANCE = 0.3F;
    private static final double INVITE_RANGE = 16.0;
    private static final int GUEST_TIMEOUT = 600;
    private static final double EAT_DISTANCE_SQR = 4.0;
    private static final float GUEST_SPEED = 0.6F;
    private static final long DINNER_START = 9000L;
    private static final long DINNER_END = 12000L;
    private static final long DISCOUNT_TICKS = 24000L;

    private ItemStack glassDrink = ItemStack.EMPTY;
    private UUID guest;
    private int guestTicks;
    private ItemStack wineGlassDrink = ItemStack.EMPTY;

    public TableSetBlockEntity(BlockPos pos, BlockState state) {
        super(EntityTypeRegistry.TABLE_SET_BLOCK_ENTITY.get(), pos, state, 1);
    }

    public TableSetBlockEntity(BlockPos pos, BlockState state, int size) {
        super(EntityTypeRegistry.TABLE_SET_BLOCK_ENTITY.get(), pos, state, size);
    }

    public ItemStack getDrink(boolean wineGlass) {
        return wineGlass ? wineGlassDrink : glassDrink;
    }

    public void setDrink(boolean wineGlass, ItemStack stack) {
        if (wineGlass) {
            wineGlassDrink = stack;
        } else {
            glassDrink = stack;
        }
        setChanged();
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        glassDrink = ItemStack.parseOptional(provider, tag.getCompound(GLASS_DRINK_KEY));
        wineGlassDrink = ItemStack.parseOptional(provider, tag.getCompound(WINE_GLASS_DRINK_KEY));
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        if (!glassDrink.isEmpty()) {
            tag.put(GLASS_DRINK_KEY, glassDrink.save(provider, new CompoundTag()));
        }
        if (!wineGlassDrink.isEmpty()) {
            tag.put(WINE_GLASS_DRINK_KEY, wineGlassDrink.save(provider, new CompoundTag()));
        }
    }

    public void serverTick(ServerLevel level, BlockPos pos, BlockState state) {
        ItemStack dish = getInventory().isEmpty() ? ItemStack.EMPTY : getInventory().get(0);
        if (dish.isEmpty() || state.getValue(TableSetBlock.CLOCHE)) {
            guest = null;
            return;
        }
        if (guest != null) {
            tickGuest(level, pos);
            return;
        }
        if (level.getGameTime() % INVITE_INTERVAL != 0 || !isDinnerTime(level) || level.random.nextFloat() >= INVITE_CHANCE) {
            return;
        }
        Vec3 center = Vec3.atCenterOf(pos);
        level.getEntitiesOfClass(Villager.class, new AABB(pos).inflate(INVITE_RANGE),
                        villager -> !villager.isBaby() && !villager.isSleeping() && villager.getTradingPlayer() == null)
                .stream()
                .min(Comparator.comparingDouble(villager -> villager.distanceToSqr(center)))
                .ifPresent(villager -> {
                    guest = villager.getUUID();
                    guestTicks = 0;
                });
    }

    private void tickGuest(ServerLevel level, BlockPos pos) {
        if (!(level.getEntity(guest) instanceof Villager villager) || !villager.isAlive() || villager.isSleeping() || ++guestTicks > GUEST_TIMEOUT) {
            guest = null;
            return;
        }
        Vec3 center = Vec3.atCenterOf(pos);
        if (villager.distanceToSqr(center) > EAT_DISTANCE_SQR) {
            villager.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(pos, GUEST_SPEED, 1));
            return;
        }
        guest = null;
        ItemStack dish = removeStack(0);
        villager.getLookControl().setLookAt(center);
        level.playSound(null, pos, SoundEvents.GENERIC_EAT, SoundSource.NEUTRAL, 1.0F, 1.0F);
        level.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, dish), center.x, center.y + 0.2, center.z, 8, 0.15, 0.1, 0.15, 0.05);
        if (villager.getVillagerData().getProfession() == VillagerProfession.NITWIT) {
            return;
        }
        ((DinnerGuest) villager).candlelight$setDinnerDiscountUntil(level.getGameTime() + DISCOUNT_TICKS);
        level.sendParticles(ParticleTypes.HAPPY_VILLAGER, villager.getX(), villager.getEyeY() + 0.3, villager.getZ(), 6, 0.3, 0.2, 0.3, 0.0);
    }

    private static boolean isDinnerTime(Level level) {
        long time = level.getDayTime() % 24000L;
        return time >= DINNER_START && time < DINNER_END;
    }
}
