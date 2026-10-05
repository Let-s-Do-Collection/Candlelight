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
import net.satisfy.candlelight.core.item.WineGlassItem;
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
    @Unique
    private boolean candlelight$raising;
    @Unique
    private float candlelight$drink;

    @Inject(method = "renderArmWithItem", at = @At("HEAD"))
    private void candlelight$startDab(AbstractClientPlayer player, float partialTicks, float pitch, InteractionHand hand, float swingProgress, ItemStack stack, float equippedProgress, PoseStack poseStack, MultiBufferSource buffer, int light, CallbackInfo ci) {
        candlelight$raising = player.isUsingItem() && player.getUsedItemHand() == hand && WineGlassItem.isBorrowed(stack);
        if (candlelight$raising) {
            HumanoidArm side = hand == InteractionHand.MAIN_HAND ? player.getMainArm() : player.getMainArm().getOpposite();
            float direction = side == HumanoidArm.RIGHT ? 1.0F : -1.0F;
            float ticks = player.getTicksUsingItem() + partialTicks;
            float raise = Mth.clamp(ticks / WineGlassItem.RAISE_TICKS, 0.0F, 1.0F);
            raise = raise * raise * (3.0F - 2.0F * raise);
            candlelight$drink = WineGlassItem.isFilled(stack) ? Mth.clamp((ticks - WineGlassItem.RAISE_TICKS - 8.0F) / 10.0F, 0.0F, 1.0F) : 0.0F;
            float clink = ticks > WineGlassItem.RAISE_TICKS && ticks < WineGlassItem.RAISE_TICKS + 4.0F ? Mth.sin((ticks - WineGlassItem.RAISE_TICKS) / 4.0F * Mth.PI) * 0.03F : 0.0F;
            poseStack.pushPose();
            poseStack.translate(-0.12F * direction * raise - 0.2F * direction * candlelight$drink, 0.16F * raise + 0.1F * candlelight$drink + clink, -0.08F * raise + 0.08F * candlelight$drink);
            return;
        }
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
        if (candlelight$raising) {
            HumanoidArm side = hand == InteractionHand.MAIN_HAND ? player.getMainArm() : player.getMainArm().getOpposite();
            float direction = side == HumanoidArm.RIGHT ? 1.0F : -1.0F;
            poseStack.mulPose(Axis.ZP.rotationDegrees(55.0F * candlelight$drink * direction));
            poseStack.mulPose(Axis.XP.rotationDegrees(25.0F * candlelight$drink));
            return;
        }
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
        if (candlelight$dabbing || candlelight$raising) {
            poseStack.popPose();
            candlelight$dabbing = false;
            candlelight$raising = false;
        }
    }
}
