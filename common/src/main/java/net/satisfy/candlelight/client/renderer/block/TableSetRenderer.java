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
public class TableSetRenderer implements StorageTypeRenderer {
    @Override
    public void render(StorageBlockEntity entity, PoseStack matrices, MultiBufferSource vertexConsumers, NonNullList<ItemStack> itemStacks) {
        ItemStack stack = itemStacks.get(0);
        if (stack.isEmpty()) return;
        float oP = (float) 1 / 16;
        matrices.translate(oP, oP, -oP);
        matrices.scale(0.5f, 0.5f, 0.5f);
        matrices.mulPose(Axis.XP.rotationDegrees(90f));
        ClientUtil.renderItem(stack, matrices, vertexConsumers, entity);
    }
}
