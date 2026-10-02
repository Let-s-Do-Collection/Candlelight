package net.satisfy.candlelight.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemStack;
import net.satisfy.candlelight.core.item.CandlelightHatItem;
import net.satisfy.candlelight.core.registry.ArmorRegistry;
import net.satisfy.candlelight.core.registry.ObjectRegistry;

public final class WornAccessoryRenderer {
    private WornAccessoryRenderer() {
    }

    public static boolean canRender(ItemStack stack) {
        return stack.is(ObjectRegistry.FLOWER_CROWN.get()) || stack.is(ObjectRegistry.NECKTIE.get());
    }

    public static void render(ItemStack stack, HumanoidModel<?> parent, PoseStack poseStack, MultiBufferSource buffers, int light) {
        if (!(stack.getItem() instanceof CandlelightHatItem hat)) return;

        Model model;
        if (stack.is(ObjectRegistry.FLOWER_CROWN.get())) {
            model = ArmorRegistry.getCrownModel(hat, parent.head, parent);
        } else if (stack.is(ObjectRegistry.NECKTIE.get())) {
            model = ArmorRegistry.getTieModel(hat, parent.head, parent.body, parent);
        } else {
            return;
        }
        if (model == parent) return;

        model.renderToBuffer(poseStack, buffers.getBuffer(model.renderType(hat.getHatTexture())), light, OverlayTexture.NO_OVERLAY);
    }
}
