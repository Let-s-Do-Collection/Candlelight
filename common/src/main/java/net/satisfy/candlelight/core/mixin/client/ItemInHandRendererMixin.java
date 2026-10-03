package net.satisfy.candlelight.core.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.satisfy.candlelight.core.registry.ObjectRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public abstract class ItemInHandRendererMixin {
    @Unique
    private boolean candlelight$dabbing;

    @Inject(method = "renderArmWithItem", at = @At("HEAD"))
    private void candlelight$startDab(AbstractClientPlayer player, float partialTicks, float pitch, InteractionHand hand, float swingProgress, ItemStack stack, float equippedProgress, PoseStack poseStack, MultiBufferSource buffer, int light, CallbackInfo ci) {
        candlelight$dabbing = player.isUsingItem() && player.getUsedItemHand() == hand && stack.is(ObjectRegistry.NAPKIN.get());
        if (!candlelight$dabbing) {
            return;
        }
        HumanoidArm side = hand == InteractionHand.MAIN_HAND ? player.getMainArm() : player.getMainArm().getOpposite();
        float direction = side == HumanoidArm.RIGHT ? 1.0F : -1.0F;
        float time = player.tickCount + partialTicks;
        float dab = Mth.sin(time * 0.6F) * 0.04F;
        poseStack.pushPose();
        poseStack.translate(-0.28F * direction, 0.22F + dab, 0.0F);
    }

    @Inject(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"))
    private void candlelight$tiltNapkin(AbstractClientPlayer player, float partialTicks, float pitch, InteractionHand hand, float swingProgress, ItemStack stack, float equippedProgress, PoseStack poseStack, MultiBufferSource buffer, int light, CallbackInfo ci) {
        if (!candlelight$dabbing) {
            return;
        }
        HumanoidArm side = hand == InteractionHand.MAIN_HAND ? player.getMainArm() : player.getMainArm().getOpposite();
        float direction = side == HumanoidArm.RIGHT ? 1.0F : -1.0F;
        poseStack.mulPose(Axis.YP.rotationDegrees(-25.0F * direction));
        poseStack.mulPose(Axis.ZP.rotationDegrees(25.0F * direction));
    }

    @Inject(method = "renderArmWithItem", at = @At("TAIL"))
    private void candlelight$endDab(AbstractClientPlayer player, float partialTicks, float pitch, InteractionHand hand, float swingProgress, ItemStack stack, float equippedProgress, PoseStack poseStack, MultiBufferSource buffer, int light, CallbackInfo ci) {
        if (candlelight$dabbing) {
            poseStack.popPose();
            candlelight$dabbing = false;
        }
    }
}
