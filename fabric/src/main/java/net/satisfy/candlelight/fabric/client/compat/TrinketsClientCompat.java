package net.satisfy.candlelight.fabric.client.compat;

import dev.emi.trinkets.api.client.TrinketRenderer;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import net.minecraft.client.model.HumanoidModel;
import net.satisfy.candlelight.client.renderer.WornAccessoryRenderer;
import net.satisfy.candlelight.core.registry.ObjectRegistry;

public final class TrinketsClientCompat {
    private TrinketsClientCompat() {
    }

    public static void init() {
        TrinketRenderer renderer = (stack, slotReference, contextModel, poseStack, buffers, light, entity, limbAngle, limbDistance, tickDelta, animationProgress, headYaw, headPitch) -> {
            if (contextModel instanceof HumanoidModel<?> humanoid) {
                WornAccessoryRenderer.render(stack, humanoid, poseStack, buffers, light);
            }
        };
        TrinketRendererRegistry.registerRenderer(ObjectRegistry.FLOWER_CROWN.get(), renderer);
        TrinketRendererRegistry.registerRenderer(ObjectRegistry.NECKTIE.get(), renderer);
    }
}
