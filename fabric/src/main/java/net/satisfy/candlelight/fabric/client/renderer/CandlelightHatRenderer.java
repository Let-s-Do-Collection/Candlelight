package net.satisfy.candlelight.fabric.client.renderer;

import net.satisfy.candlelight.client.renderer.WornAccessoryRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.satisfy.candlelight.client.model.CookingHatModel;
import net.satisfy.candlelight.core.item.CandlelightHatItem;
import net.satisfy.candlelight.core.registry.ArmorRegistry;

public class CandlelightHatRenderer implements ArmorRenderer {
    @Override
    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, HumanoidModel<LivingEntity> contextModel) {
        if (slot != EquipmentSlot.HEAD) return;
        if (!(stack.getItem() instanceof CandlelightHatItem hat)) return;

        Model hatModel = ArmorRegistry.getHatModel(hat, contextModel.head, contextModel);
        if (hatModel instanceof CookingHatModel<?> cookingHatModel) {
            cookingHatModel.copyHead(contextModel.head);
            hatModel.renderToBuffer(
                    matrices,
                    vertexConsumers.getBuffer(hatModel.renderType(hat.getHatTexture())),
                    light,
                    OverlayTexture.NO_OVERLAY
            );
        }

        if (WornAccessoryRenderer.canRender(stack)) {
            WornAccessoryRenderer.render(stack, contextModel, matrices, vertexConsumers, light);
        }
    }
}