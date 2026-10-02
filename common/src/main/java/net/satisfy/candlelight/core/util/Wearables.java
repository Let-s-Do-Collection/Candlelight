package net.satisfy.candlelight.core.util;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.satisfy.candlelight.core.registry.ObjectRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

public final class Wearables {
    private static final List<BiPredicate<LivingEntity, Item>> SLOT_PROVIDERS = new ArrayList<>();

    private Wearables() {
    }

    public static void registerSlotProvider(BiPredicate<LivingEntity, Item> provider) {
        SLOT_PROVIDERS.add(provider);
    }

    public static boolean isWearing(LivingEntity entity, Item item) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.isArmor() && entity.getItemBySlot(slot).is(item)) return true;
        }
        for (BiPredicate<LivingEntity, Item> provider : SLOT_PROVIDERS) {
            if (provider.test(entity, item)) return true;
        }
        return false;
    }

    public static boolean hasChefSet(LivingEntity entity) {
        return isWearing(entity, ObjectRegistry.COOKING_HAT.get())
                && isWearing(entity, ObjectRegistry.CHEFS_JACKET.get())
                && isWearing(entity, ObjectRegistry.CHEFS_PANTS.get())
                && isWearing(entity, ObjectRegistry.CHEFS_BOOTS.get());
    }

    public static boolean isElegantlyDressed(LivingEntity entity) {
        boolean suit = (isWearing(entity, ObjectRegistry.SHIRT.get()) || isWearing(entity, ObjectRegistry.FORMAL_SHIRT.get()))
                && isWearing(entity, ObjectRegistry.TROUSERS_AND_VEST.get())
                && isWearing(entity, ObjectRegistry.NECKTIE.get());
        boolean dress = isWearing(entity, ObjectRegistry.DRESS.get())
                && isWearing(entity, ObjectRegistry.FLOWER_CROWN.get());
        return suit || dress;
    }
}
