package net.satisfy.candlelight.client.renderer;

import net.satisfy.foundation.client.armor.ArmorModels;
import net.minecraft.world.entity.EquipmentSlot;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemStack;
import net.satisfy.candlelight.core.registry.ObjectRegistry;

public final class WornAccessoryRenderer {
    private WornAccessoryRenderer() {
    }

    public static boolean canRender(ItemStack stack) {
        return stack.is(ObjectRegistry.FLOWER_CROWN.get()) || stack.is(ObjectRegistry.NECKTIE.get());
    }

    public static void render(ItemStack stack, HumanoidModel<?> parent, PoseStack poseStack, MultiBufferSource buffers, int light) {
        ArmorModels.render(poseStack, buffers, stack, EquipmentSlot.HEAD, light, parent);
    }
}
