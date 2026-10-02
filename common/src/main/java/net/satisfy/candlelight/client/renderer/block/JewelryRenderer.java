package net.satisfy.candlelight.client.renderer.block;

import net.satisfy.foundation.storage.StorageTypeRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.satisfy.foundation.storage.StorageBlockEntity;
import net.satisfy.foundation.render.ClientUtil;

@Environment(EnvType.CLIENT)
public class JewelryRenderer implements StorageTypeRenderer {
    @Override
    public void render(StorageBlockEntity entity, PoseStack matrices, MultiBufferSource vertexConsumers, NonNullList<ItemStack> itemStacks) {
        if (!itemStacks.isEmpty()) {
            ItemStack stack = itemStacks.get(0);
            if (!stack.isEmpty()) {
                matrices.pushPose();
                matrices.mulPose(Axis.ZN.rotationDegrees(45f));
                matrices.translate(-0.1, 0.1, 0);
                matrices.scale(0.25f, 0.25f, 0.25f);
                ClientUtil.renderItem(stack, matrices, vertexConsumers, entity);
                matrices.popPose();
            }
        }
    }
}
