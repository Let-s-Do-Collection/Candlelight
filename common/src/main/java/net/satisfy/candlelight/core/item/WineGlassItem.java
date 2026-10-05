package net.satisfy.candlelight.core.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.satisfy.candlelight.core.block.TableSetBlock;
import net.satisfy.candlelight.core.block.entity.TableSetBlockEntity;
import net.satisfy.candlelight.core.registry.ObjectRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;

public class WineGlassItem extends BlockItem {
    public static final int RAISE_TICKS = 8;
    public static final int DRINK_TICKS = 40;
    private static final String TABLE_KEY = "CandlelightTableSet";
    private static final String TIME_KEY = "CandlelightBorrowedAt";
    private static final String FILLED_KEY = "CandlelightFilled";
    private static final long RETURN_GRACE_TICKS = 10L;
    private static final double TOAST_RANGE = 3.5;
    private static final Map<UUID, Long> LAST_TOAST = new WeakHashMap<>();

    public WineGlassItem(Block block, Properties settings) {
        super(block, settings);
    }

    public static ItemStack borrowedFrom(BlockPos tablePos, long gameTime, boolean filled) {
        ItemStack stack = new ItemStack(ObjectRegistry.WINE_GLASS.get());
        CompoundTag tag = new CompoundTag();
        tag.putLong(TABLE_KEY, tablePos.asLong());
        tag.putLong(TIME_KEY, gameTime);
        tag.putBoolean(FILLED_KEY, filled);
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
        return stack;
    }

    public static boolean isBorrowed(ItemStack stack) {
        return stack.is(ObjectRegistry.WINE_GLASS.get()) && stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).contains(TABLE_KEY);
    }

    public static boolean isFilled(ItemStack stack) {
        return isBorrowed(stack) && stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBoolean(FILLED_KEY);
    }

    public static boolean isRaising(LivingEntity entity) {
        return entity.isUsingItem() && isBorrowed(entity.getUseItem());
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        if (isBorrowed(context.getItemInHand())) {
            return InteractionResult.PASS;
        }
        return super.useOn(context);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (isBorrowed(player.getItemInHand(hand))) {
            return ItemUtils.startUsingInstantly(level, player, hand);
        }
        return super.use(level, player, hand);
    }

    @Override
    public @NotNull UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.NONE;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return isBorrowed(stack) ? 72000 : super.getUseDuration(stack, entity);
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int remainingUseDuration) {
        if (level.isClientSide() || !isBorrowed(stack)) {
            return;
        }
        int ticks = entity.getTicksUsingItem();
        if (ticks == RAISE_TICKS) {
            tryToast(level, entity);
        }
        if (ticks >= DRINK_TICKS && isFilled(stack)) {
            drinkFromTable(stack, level, entity);
            entity.stopUsingItem();
            returnToTable(stack, level, entity);
        }
    }

    private static void tryToast(Level level, LivingEntity entity) {
        long now = level.getGameTime();
        for (Player other : level.getEntitiesOfClass(Player.class, entity.getBoundingBox().inflate(TOAST_RANGE), p -> p != entity && isRaising(p) && p.getTicksUsingItem() >= RAISE_TICKS)) {
            if (now - LAST_TOAST.getOrDefault(other.getUUID(), -100L) < DRINK_TICKS) {
                continue;
            }
            LAST_TOAST.put(entity.getUUID(), now);
            LAST_TOAST.put(other.getUUID(), now);
            Vec3 middle = entity.getEyePosition().add(other.getEyePosition()).scale(0.5);
            level.playSound(null, middle.x, middle.y, middle.z, SoundEvents.AMETHYST_CLUSTER_HIT, SoundSource.PLAYERS, 0.9F, 1.7F + level.random.nextFloat() * 0.2F);
            level.playSound(null, middle.x, middle.y, middle.z, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS, 0.7F, 1.5F);
            if (level instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.HEART, middle.x, middle.y + 0.2, middle.z, 3, 0.25, 0.15, 0.25, 0.0);
            }
            return;
        }
    }

    private static void drinkFromTable(ItemStack stack, Level level, LivingEntity entity) {
        BlockPos tablePos = tablePos(stack);
        level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.GENERIC_DRINK, SoundSource.PLAYERS, 1.0F, 1.0F);
        if (!(level.getBlockEntity(tablePos) instanceof TableSetBlockEntity tableSet)) {
            return;
        }
        ItemStack drink = tableSet.getDrink(true);
        tableSet.setDrink(true, ItemStack.EMPTY);
        CompoundTag tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        tag.putBoolean(FILLED_KEY, false);
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
        if (drink.isEmpty() || !(entity instanceof Player player)) {
            return;
        }
        ItemStack remainder = drink.getItem().finishUsingItem(drink.copy(), level, player);
        if (!remainder.isEmpty() && !ItemStack.isSameItem(remainder, drink) && !player.getInventory().add(remainder)) {
            player.drop(remainder, false);
        }
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        if (!level.isClientSide() && isBorrowed(stack)) {
            returnToTable(stack, level, entity);
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        if (level.isClientSide() || !isBorrowed(stack) || !(entity instanceof LivingEntity living)) {
            return;
        }
        boolean raising = living.isUsingItem() && living.getUseItem() == stack;
        long borrowedAt = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getLong(TIME_KEY);
        if (!raising && level.getGameTime() - borrowedAt > RETURN_GRACE_TICKS) {
            returnToTable(stack, level, living);
        }
    }

    private static BlockPos tablePos(ItemStack stack) {
        return BlockPos.of(stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getLong(TABLE_KEY));
    }

    private static void returnToTable(ItemStack stack, Level level, LivingEntity entity) {
        if (stack.isEmpty()) {
            return;
        }
        BlockPos tablePos = tablePos(stack);
        BlockState table = level.getBlockState(tablePos);
        stack.shrink(1);
        if (table.getBlock() instanceof TableSetBlock && !table.getValue(TableSetBlock.WINE_GLASS)) {
            boolean filled = level.getBlockEntity(tablePos) instanceof TableSetBlockEntity tableSet && !tableSet.getDrink(true).isEmpty();
            level.setBlockAndUpdate(tablePos, table.setValue(TableSetBlock.WINE_GLASS, true).setValue(TableSetBlock.WINE_GLASS_DRINK, filled));
            level.playSound(null, tablePos, SoundEvents.GLASS_STEP, SoundSource.BLOCKS, 0.5F, 1.6F);
        } else if (entity instanceof Player player && !player.getInventory().add(new ItemStack(ObjectRegistry.WINE_GLASS.get()))) {
            player.drop(new ItemStack(ObjectRegistry.WINE_GLASS.get()), false);
        }
    }
}
