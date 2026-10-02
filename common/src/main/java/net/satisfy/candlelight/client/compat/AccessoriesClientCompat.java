package net.satisfy.candlelight.client.compat;

import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import io.wispforest.accessories.api.client.AccessoryRenderer;
import io.wispforest.accessories.api.slot.SlotReference;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.satisfy.candlelight.client.renderer.WornAccessoryRenderer;
import net.satisfy.candlelight.core.registry.ObjectRegistry;

public final class AccessoriesClientCompat {
    private AccessoriesClientCompat() {
    }

    public static void init() {
        AccessoriesRendererRegistry.registerRenderer(ObjectRegistry.FLOWER_CROWN.get(), WornRenderer::new);
        AccessoriesRendererRegistry.registerRenderer(ObjectRegistry.NECKTIE.get(), WornRenderer::new);
        AccessoriesRendererRegistry.registerNoRenderer(ObjectRegistry.GOLD_RING.get());
    }

    private static class WornRenderer implements AccessoryRenderer {
        @Override
        public <M extends LivingEntity> void render(ItemStack stack, SlotReference reference, PoseStack poseStack, EntityModel<M> model, MultiBufferSource buffers, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            if (model instanceof HumanoidModel<?> humanoid) {
                WornAccessoryRenderer.render(stack, humanoid, poseStack, buffers, light);
            }
        }
    }
}
