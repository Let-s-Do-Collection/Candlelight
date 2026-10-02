package net.satisfy.candlelight.forge.client.extensions;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.satisfy.candlelight.core.item.CandlelightHatItem;
import net.satisfy.candlelight.core.registry.ArmorRegistry;
import net.satisfy.candlelight.core.registry.ObjectRegistry;
import org.jetbrains.annotations.NotNull;

public class CandlelightHatExtensions implements IClientItemExtensions {
    @Override
    public @NotNull Model getGenericArmorModel(@NotNull LivingEntity entity, @NotNull ItemStack stack, @NotNull EquipmentSlot slot, @NotNull HumanoidModel<?> original) {
        if (slot != EquipmentSlot.HEAD) return original;

        Item item = stack.getItem();

        if (item == ObjectRegistry.NECKTIE.get()) return ArmorRegistry.getTieModel(item, original.head, original.body, original);

        if (item == ObjectRegistry.FLOWER_CROWN.get()) return ArmorRegistry.getCrownModel(item, original.head, original);

        if (item instanceof CandlelightHatItem) return ArmorRegistry.getHatModel(item, original.head, original);

        return original;
    }
}