package net.satisfy.candlelight.forge.client.compat;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.satisfy.candlelight.client.renderer.WornAccessoryRenderer;
import net.satisfy.candlelight.core.registry.ObjectRegistry;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public final class CuriosClientCompat {
    private CuriosClientCompat() {
    }

    public static void init() {
        CuriosRendererRegistry.register(ObjectRegistry.FLOWER_CROWN.get(), WornRenderer::new);
        CuriosRendererRegistry.register(ObjectRegistry.NECKTIE.get(), WornRenderer::new);
    }

    private static class WornRenderer implements ICurioRenderer {
        @Override
        public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack poseStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource buffers, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            if (renderLayerParent.getModel() instanceof HumanoidModel<?> humanoid) {
                WornAccessoryRenderer.render(stack, humanoid, poseStack, buffers, light);
            }
        }
    }
}
