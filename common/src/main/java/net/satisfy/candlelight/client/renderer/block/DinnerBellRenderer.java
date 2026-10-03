package net.satisfy.candlelight.client.renderer.block;

import net.minecraft.util.Mth;
import com.mojang.math.Axis;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.satisfy.candlelight.Candlelight;
import net.satisfy.candlelight.client.model.DinnerBellModel;
import net.satisfy.candlelight.core.block.entity.DinnerBellBlockEntity;

public class DinnerBellRenderer implements BlockEntityRenderer<DinnerBellBlockEntity> {
    private static final float PRESS_TICKS = 2.0F;
    private static final float RELEASE_TICKS = 4.0F;
    private static final float WOBBLE_TICKS = 24.0F;
    private static final float WOBBLE_SPEED = 1.6F;
    private static final float WOBBLE_DEGREES = 4.0F;
    private static final float WOBBLE_DECAY = 6.0F;
    private static final ResourceLocation BELL_TEXTURE = Candlelight.identifier("textures/entity/dinner_bell.png");

    private final ModelPart dinner_bell_base;
    private final ModelPart dinner_bell_button;

    public DinnerBellRenderer(BlockEntityRendererProvider.Context context) {
        ModelPart root = context.bakeLayer(DinnerBellModel.LAYER_LOCATION);
        this.dinner_bell_base = root.getChild("dinner_bell_base");
        this.dinner_bell_button = root.getChild("dinner_bell_button");
    }


    @Override
    public void render(DinnerBellBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
        float time = blockEntity.getRingProgress(partialTicks);
        boolean ringing = time >= 0.0F && time < WOBBLE_TICKS;
        poseStack.pushPose();
        if (ringing) {
            float wobble = Mth.sin(time * WOBBLE_SPEED) * WOBBLE_DEGREES * (float) Math.exp(-time / WOBBLE_DECAY);
            poseStack.translate(0.5F, 0.0F, 0.5F);
            poseStack.mulPose(Axis.ZP.rotationDegrees(wobble));
            poseStack.mulPose(Axis.XP.rotationDegrees(wobble * 0.5F));
            poseStack.translate(-0.5F, 0.0F, -0.5F);
        }
        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(BELL_TEXTURE));
        dinner_bell_base.render(poseStack, vertexConsumer, combinedLight, OverlayTexture.NO_OVERLAY);
        poseStack.translate(0.0F, -buttonPress(ringing ? time : -1.0F) / 16.0F, 0.0F);
        dinner_bell_button.render(poseStack, vertexConsumer, combinedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
    }

    private static float buttonPress(float time) {
        if (time < 0.0F) return 0.0F;
        if (time < PRESS_TICKS) return time / PRESS_TICKS;
        float release = (time - PRESS_TICKS) / RELEASE_TICKS;
        if (release >= 1.0F) return 0.0F;
        return 1.0F - release * release * (3.0F - 2.0F * release);
    }
}
