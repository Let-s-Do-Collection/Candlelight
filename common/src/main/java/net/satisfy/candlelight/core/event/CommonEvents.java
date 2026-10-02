package net.satisfy.candlelight.core.event;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.BlockEvent;
import dev.architectury.event.events.common.PlayerEvent;
import dev.architectury.event.events.common.TickEvent;
import net.minecraft.world.effect.MobEffects;
import net.satisfy.candlelight.core.util.Wearables;
import dev.architectury.utils.value.IntValue;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.satisfy.candlelight.core.registry.MobEffectRegistry;
import net.satisfy.candlelight.core.registry.ObjectRegistry;
import net.satisfy.candlelight.core.registry.SoundEventRegistry;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.minecraft.world.item.Item.BASE_ATTACK_SPEED_ID;

public class CommonEvents {
    private static final String REFRESHED_PREFIX = "candlelight_refreshed_";
    private static final int REFRESHED_MAX_CHARGES = 10;
    private static final int WELL_SERVED_FOOD_FLOOR = 8;

    public static void init() {
        PlayerEvent.ATTACK_ENTITY.register(CommonEvents::attack);
        BlockEvent.BREAK.register(CommonEvents::onBlockBreak);
        TickEvent.PLAYER_POST.register(CommonEvents::onPlayerTick);
    }

    private static void onPlayerTick(net.minecraft.world.entity.player.Player player) {
        if (player.level().isClientSide) {
            return;
        }

        MobEffectInstance refreshedInstance = player.getEffect(MobEffectRegistry.holder(MobEffectRegistry.REFRESHED));
        if (refreshedInstance != null && getRefreshedCharges(player) == 0) {
            setRefreshedCharges(player, REFRESHED_MAX_CHARGES);
        }

        MobEffectInstance wellServedInstance = player.getEffect(MobEffectRegistry.holder(MobEffectRegistry.WELL_SERVED));
        if (wellServedInstance != null && player.getFoodData().getFoodLevel() < WELL_SERVED_FOOD_FLOOR) {
            player.getFoodData().setFoodLevel(WELL_SERVED_FOOD_FLOOR);
        }

        if (player.tickCount % 20 == 0 && (Wearables.isWearing(player, ObjectRegistry.GOLD_RING.get()) || player.getOffhandItem().is(ObjectRegistry.GOLD_RING.get()))) {
            player.addEffect(new MobEffectInstance(MobEffects.LUCK, 60, 1, true, false, true));
        }

        ItemStack main = player.getMainHandItem();
        ItemStack off = player.getOffhandItem();
        boolean holdingLoveLetter = main.is(ObjectRegistry.LOVE_LETTER_CLOSED.get()) || off.is(ObjectRegistry.LOVE_LETTER_CLOSED.get());

        if (holdingLoveLetter && player.tickCount % 5 == 0) {
            double dx = player.getX() - player.xOld;
            double dz = player.getZ() - player.zOld;
            boolean isMoving = dx * dx + dz * dz > 0.001;

            if (isMoving && !player.isShiftKeyDown()) {
                double yaw = Math.toRadians(player.getYRot());
                double behindX = player.getX() - (Math.sin(yaw) * 0.5);
                double behindZ = player.getZ() + (Math.cos(yaw) * 0.5);
                double y = player.getY() + 0.1;

                player.level().addParticle(ParticleTypes.HEART, behindX, y, behindZ, 0, 0.02, 0);
            }
        }
    }

    private static EventResult onBlockBreak(Level level, BlockPos blockPos, BlockState blockState, ServerPlayer player, @Nullable IntValue xp) {
        if (level.isClientSide) {
            return EventResult.pass();
        }

        if (!(level instanceof ServerLevel serverLevel)) {
            return EventResult.pass();
        }

        MobEffectInstance refreshedInstance = player.getEffect(MobEffectRegistry.holder(MobEffectRegistry.REFRESHED));
        if (refreshedInstance == null) {
            return EventResult.pass();
        }

        if (!isRipeCrop(blockState)) {
            return EventResult.pass();
        }

        int charges = getRefreshedCharges(player);
        if (charges <= 0) {
            clearRefreshedTags(player);
            player.removeEffect(MobEffectRegistry.holder(MobEffectRegistry.REFRESHED));
            return EventResult.pass();
        }

        ItemStack toolStack = player.getMainHandItem();

        ItemStack bonusDrop = getBonusDrop(serverLevel, blockPos, blockState, player, toolStack);
        if (!bonusDrop.isEmpty()) {
            Block.popResource(serverLevel, blockPos, bonusDrop);
        }

        charges--;
        if (charges <= 0) {
            clearRefreshedTags(player);
            player.removeEffect(MobEffectRegistry.holder(MobEffectRegistry.REFRESHED));
        } else {
            setRefreshedCharges(player, charges);
        }

        return EventResult.pass();
    }

    private static boolean isRipeCrop(BlockState blockState) {
        if (!blockState.is(BlockTags.CROPS)) {
            return false;
        }
        if (blockState.getBlock() instanceof CropBlock cropBlock) {
            return cropBlock.isMaxAge(blockState);
        }
        return false;
    }

    private static ItemStack getBonusDrop(ServerLevel level, BlockPos blockPos, BlockState blockState, ServerPlayer player, ItemStack toolStack) {
        List<ItemStack> drops = Block.getDrops(blockState, level, blockPos, level.getBlockEntity(blockPos), player, toolStack);
        if (drops.isEmpty()) {
            return ItemStack.EMPTY;
        }

        ItemStack preferred = findPreferredProduce(drops);
        if (!preferred.isEmpty()) {
            ItemStack bonus = preferred.copy();
            bonus.setCount(1);
            return bonus;
        }

        ItemStack first = drops.getFirst();
        if (first.isEmpty()) {
            return ItemStack.EMPTY;
        }

        ItemStack bonus = first.copy();
        bonus.setCount(1);
        return bonus;
    }

    private static ItemStack findPreferredProduce(List<ItemStack> drops) {
        ItemStack wheat = findFirst(drops, Items.WHEAT);
        if (!wheat.isEmpty()) {
            return wheat;
        }

        ItemStack carrot = findFirst(drops, Items.CARROT);
        if (!carrot.isEmpty()) {
            return carrot;
        }

        ItemStack potato = findFirst(drops, Items.POTATO);
        if (!potato.isEmpty()) {
            return potato;
        }

        ItemStack beetroot = findFirst(drops, Items.BEETROOT);
        if (!beetroot.isEmpty()) {
            return beetroot;
        }

        ItemStack netherWart = findFirst(drops, Items.NETHER_WART);
        if (!netherWart.isEmpty()) {
            return netherWart;
        }

        ItemStack sweetBerries = findFirst(drops, Items.SWEET_BERRIES);
        if (!sweetBerries.isEmpty()) {
            return sweetBerries;
        }

        return ItemStack.EMPTY;
    }

    private static ItemStack findFirst(List<ItemStack> drops, net.minecraft.world.item.Item item) {
        for (ItemStack drop : drops) {
            if (!drop.isEmpty() && drop.is(item)) {
                return drop;
            }
        }
        return ItemStack.EMPTY;
    }

    private static int getRefreshedCharges(net.minecraft.world.entity.player.Player player) {
        for (int i = REFRESHED_MAX_CHARGES; i >= 1; i--) {
            if (player.getTags().contains(REFRESHED_PREFIX + i)) {
                return i;
            }
        }
        return 0;
    }

    private static void setRefreshedCharges(net.minecraft.world.entity.player.Player player, int charges) {
        clearRefreshedTags(player);
        player.addTag(REFRESHED_PREFIX + charges);
    }

    private static void clearRefreshedTags(net.minecraft.world.entity.player.Player player) {
        for (int i = 1; i <= REFRESHED_MAX_CHARGES; i++) {
            player.removeTag(REFRESHED_PREFIX + i);
        }
    }

    public static EventResult attack(net.minecraft.world.entity.player.Player player, Level level, Entity target, InteractionHand hand, @Nullable EntityHitResult result) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (itemStack.is(ObjectRegistry.COOKING_PAN_ITEM.get())) {
            level.playSound(null, target.getX(), target.getY(), target.getZ(), SoundEventRegistry.COOKING_POT_HIT.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
            target.hurt(level.damageSources().generic(), 5.0F);
            itemStack.hurtAndBreak(1, player, player.getEquipmentSlotForItem(itemStack));

            itemStack.applyComponents(DataComponentMap.builder().set(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.builder().add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, -2.0, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build()).build());

            if (target instanceof Mob mob) {
                mob.setTarget(player);
            }

            return EventResult.interruptTrue();
        }
        return EventResult.pass();
    }
}