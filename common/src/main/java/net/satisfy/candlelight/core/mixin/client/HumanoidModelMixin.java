package net.satisfy.candlelight.core.mixin.client;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.satisfy.candlelight.core.item.WineGlassItem;
import net.satisfy.candlelight.core.registry.ObjectRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidModel.class)
public abstract class HumanoidModelMixin {
    @Unique
    private static final float candlelight$ARM_LIFT = 1.75F;
    @Unique
    private static final float candlelight$ARM_INWARD = 0.65F;
    @Unique
    private static final float candlelight$DAB_SPEED = 0.6F;
    @Unique
    private static final float candlelight$DAB_AMOUNT = 0.12F;

    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At("TAIL"))
    private void candlelight$dabWithNapkin(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        if (WineGlassItem.isRaising(entity)) {
            candlelight$raiseGlass(entity, ageInTicks);
            return;
        }
        if (!entity.isUsingItem() || !entity.getUseItem().is(ObjectRegistry.NAPKIN.get())) {
            return;
        }
        HumanoidModel<?> model = (HumanoidModel<?>) (Object) this;
        HumanoidArm side = entity.getUsedItemHand() == InteractionHand.MAIN_HAND ? entity.getMainArm() : entity.getMainArm().getOpposite();
        ModelPart arm = side == HumanoidArm.RIGHT ? model.rightArm : model.leftArm;
        float dab = Mth.sin(ageInTicks * candlelight$DAB_SPEED) * candlelight$DAB_AMOUNT;
        arm.xRot = Mth.clamp(model.head.xRot - candlelight$ARM_LIFT + dab, -2.4F, 3.3F);
        arm.yRot = model.head.yRot + (side == HumanoidArm.RIGHT ? -candlelight$ARM_INWARD : candlelight$ARM_INWARD);
        arm.zRot = 0.0F;
    }

    @Unique
    private void candlelight$raiseGlass(LivingEntity entity, float ageInTicks) {
        HumanoidModel<?> model = (HumanoidModel<?>) (Object) this;
        HumanoidArm side = entity.getUsedItemHand() == InteractionHand.MAIN_HAND ? entity.getMainArm() : entity.getMainArm().getOpposite();
        ModelPart arm = side == HumanoidArm.RIGHT ? model.rightArm : model.leftArm;
        float inward = side == HumanoidArm.RIGHT ? -1.0F : 1.0F;
        float ticks = entity.getTicksUsingItem() + (ageInTicks - Mth.floor(ageInTicks));
        float raise = Mth.clamp(ticks / WineGlassItem.RAISE_TICKS, 0.0F, 1.0F);
        raise = raise * raise * (3.0F - 2.0F * raise);
        float drink = WineGlassItem.isFilled(entity.getUseItem()) ? Mth.clamp((ticks - WineGlassItem.RAISE_TICKS - 8.0F) / 10.0F, 0.0F, 1.0F) : 0.0F;
        float toastX = model.head.xRot * 0.3F - 1.95F;
        float drinkX = model.head.xRot - candlelight$ARM_LIFT;
        arm.xRot = Mth.lerp(raise, arm.xRot, Mth.lerp(drink, toastX, drinkX));
        arm.yRot = Mth.lerp(raise, arm.yRot, inward * Mth.lerp(drink, 0.2F, candlelight$ARM_INWARD) + model.head.yRot * drink);
        arm.zRot = Mth.lerp(raise, arm.zRot, 0.0F);
    }
}
