package net.satisfy.candlelight.core.util;

import net.satisfy.foundation.armor.Wearing;
import net.satisfy.candlelight.platform.PlatformHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.satisfy.candlelight.core.registry.ObjectRegistry;


public final class Wearables {
    private Wearables() {
    }

    public static boolean isWearing(LivingEntity entity, Item item) {
        return Wearing.isWearing(entity, item);
    }

    public static boolean hasChefSet(LivingEntity entity) {
        return PlatformHelper.enableChefSetBonus() && isWearing(entity, ObjectRegistry.COOKING_HAT.get())
                && isWearing(entity, ObjectRegistry.CHEFS_JACKET.get())
                && isWearing(entity, ObjectRegistry.CHEFS_PANTS.get())
                && isWearing(entity, ObjectRegistry.CHEFS_BOOTS.get());
    }

    public static boolean isElegantlyDressed(LivingEntity entity) {
        if (!PlatformHelper.enableElegantSetBonus()) {
            return false;
        }
        boolean suit = (isWearing(entity, ObjectRegistry.SHIRT.get()) || isWearing(entity, ObjectRegistry.FORMAL_SHIRT.get()))
                && isWearing(entity, ObjectRegistry.TROUSERS_AND_VEST.get())
                && isWearing(entity, ObjectRegistry.NECKTIE.get());
        boolean dress = isWearing(entity, ObjectRegistry.DRESS.get())
                && isWearing(entity, ObjectRegistry.FLOWER_CROWN.get());
        return suit || dress;
    }
}
