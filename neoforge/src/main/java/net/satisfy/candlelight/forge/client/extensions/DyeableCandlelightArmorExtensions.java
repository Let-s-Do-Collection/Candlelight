package net.satisfy.candlelight.forge.client.extensions;

import net.satisfy.foundation.armor.DyeableArmorItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.satisfy.candlelight.core.registry.ArmorRegistry;
import org.jetbrains.annotations.NotNull;

public class DyeableCandlelightArmorExtensions implements IClientItemExtensions {
    @Override
    public @NotNull Model getGenericArmorModel(@NotNull LivingEntity entity, @NotNull ItemStack stack, @NotNull EquipmentSlot slot, @NotNull HumanoidModel<?> original) {
        if (!(stack.getItem() instanceof DyeableArmorItem item)) return original;
        if (slot != item.getEquipmentSlot()) return original;

        CompoundTag tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        if (tag.contains("Visible") && !tag.getBoolean("Visible")) return original;

        if (slot == EquipmentSlot.CHEST) return ArmorRegistry.getDressModel(item, original.body, original.leftArm, original.rightArm, original.leftLeg, original.rightLeg, original);
        if (slot == EquipmentSlot.LEGS) return ArmorRegistry.getSuitModel(item, original.rightLeg, original.leftLeg, original);

        return original;
    }

    @Override
    public int getArmorLayerTintColor(@NotNull ItemStack stack, @NotNull LivingEntity entity, @NotNull ArmorMaterial.Layer layer, int layerIdx, int fallbackColor) {
        if (!(stack.getItem() instanceof DyeableArmorItem item)) return 0;

        CompoundTag tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        if (tag.contains("Visible") && !tag.getBoolean("Visible")) return 0;

        if (layerIdx == 0) return 0xFF000000 | item.getColor(stack);
        return 0xFFFFFFFF;
    }
}