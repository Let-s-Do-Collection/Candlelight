package net.satisfy.candlelight.fabric.client.renderer;

import net.satisfy.foundation.armor.DyeableArmorItem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.satisfy.candlelight.core.registry.ArmorRegistry;

public class DyeableCandlelightChestplateRenderer implements ArmorRenderer {
    @Override
    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, HumanoidModel<LivingEntity> contextModel) {
        if (slot != EquipmentSlot.CHEST) return;
        if (!(stack.getItem() instanceof DyeableArmorItem item)) return;

        CompoundTag tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        if (tag.contains("Visible") && !tag.getBoolean("Visible")) return;

        Model model = ArmorRegistry.getDressModel(item, contextModel.body, contextModel.leftArm, contextModel.rightArm, contextModel.leftLeg, contextModel.rightLeg, contextModel);
        if (model == null) return;

        ResourceLocation base = item.getTexture();
        int packedColor = 0xFF000000 | item.getColor(stack);
        model.renderToBuffer(matrices, vertexConsumers.getBuffer(model.renderType(base)), light, OverlayTexture.NO_OVERLAY, packedColor);

        ResourceLocation overlay = item.getOverlayTexture();
        if (overlay != null) model.renderToBuffer(matrices, vertexConsumers.getBuffer(model.renderType(overlay)), light, OverlayTexture.NO_OVERLAY, 0xFFFFFFFF);
    }
}