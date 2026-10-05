package net.satisfy.candlelight.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.candlelight.Candlelight;
import net.satisfy.candlelight.client.model.TypewriterModel;
import net.satisfy.candlelight.core.block.TypewriterBlock;
import net.satisfy.candlelight.core.block.entity.TypewriterEntity;
import net.satisfy.candlelight.core.registry.ObjectRegistry;

public class TypewriterRenderer implements BlockEntityRenderer<TypewriterEntity> {
    private static final ResourceLocation IRON_TEXTURE = Candlelight.identifier("textures/entity/typewriter_iron.png");
    private static final ResourceLocation GOLD_TEXTURE = Candlelight.identifier("textures/entity/typewriter_gold.png");
    private final ModelPart typewriter;
    private final ModelPart space;
    private final ModelPart enter;
    private final ModelPart roller;
    private final ModelPart paper;
    private final ModelPart paperWritten;
    private final List<ModelPart> keyParts;

    public TypewriterRenderer(BlockEntityRendererProvider.Context context) {
        ModelPart root = context.bakeLayer(TypewriterModel.LAYER_LOCATION);
        this.typewriter = root.getChild("typewriter");
        ModelPart keyboard = this.typewriter.getChild("keyboard");
        ModelPart sp;
        try {
            sp = keyboard.getChild("space");
        } catch (Exception e) {
            sp = keyboard.getChild("spacebar");
        }
        this.space = sp;
        ModelPart en;
        try {
            en = keyboard.getChild("enter");
        } catch (Exception e) {
            en = keyboard.getChild("return");
        }
        this.enter = en;
        ModelPart rl;
        try {
            rl = this.typewriter.getChild("roller");
        } catch (Exception e) {
            rl = this.typewriter.getChild("carriage");
        }
        this.roller = rl;
        ModelPart pp;
        try {
            pp = this.typewriter.getChild("paper");
        } catch (Exception e) {
            pp = this.typewriter.getChild("sheet");
        }
        this.paper = pp;
        ModelPart pw;
        try {
            pw = this.typewriter.getChild("paper_written");
        } catch (Exception e) {
            pw = this.typewriter.getChild("sheet_written");
        }
        this.paperWritten = pw;
        List<ModelPart> keys = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            try {
                keys.add(keyboard.getChild("button_" + i));
            } catch (Exception ignored) {
            }
        }
        if (keys.isEmpty()) {
            for (int i = 0; i < 8; i++) {
                try {
                    keys.add(keyboard.getChild("key" + i));
                } catch (Exception ignored) {
                }
            }
        }
        this.keyParts = keys;
    }

    @Override
    public void render(TypewriterEntity be, float partialTicks, PoseStack poseStack, MultiBufferSource buffers, int light, int overlay) {
        Level level = be.getLevel();
        if (level == null) return;
        BlockState state = level.getBlockState(be.getBlockPos());
        if (!(state.getBlock() instanceof TypewriterBlock)) return;

        poseStack.pushPose();

        Direction facing = state.getValue(TypewriterBlock.FACING);

        float rotY = switch (facing) {
            case EAST -> 270f;
            case SOUTH -> 0f;
            case WEST -> 90f;
            default -> 180f;
        };

        float offsetX;
        float offsetZ;

        switch (facing) {
            case SOUTH -> {
                offsetX = 0.5f;
                offsetZ = -0.5f;
            }
            case EAST -> {
                offsetX = -0.5f;
                offsetZ = -0.5f;
            }
            case WEST -> {
                offsetX = 0.5f;
                offsetZ = 0.5f;
            }
            default -> {
                offsetX = -0.5f;
                offsetZ = 0.5f;
            }
        }

        poseStack.mulPose(Axis.XP.rotationDegrees(180f));
        poseStack.mulPose(Axis.YP.rotationDegrees(rotY));
        poseStack.translate(offsetX, -1.5f, offsetZ);

        float shake = be.getShake();
        poseStack.translate(shake * 0.006f, Math.abs(shake) * 0.004f, 0f);

        VertexConsumer vc = buffers.getBuffer(RenderType.entityCutoutNoCull(getTexture(state)));

        float rx = 0.15f + be.getCarriage() * 4.5f;
        float sy = be.getSpacePress() * 0.6f;
        float ey = be.getEnterPress() * 0.6f;
        float feed = be.getLineFeed();

        this.space.y += sy;
        this.enter.y += ey;
        float[] ky = new float[this.keyParts.size()];
        for (int i = 0; i < ky.length; i++) {
            ky[i] = i < TypewriterEntity.KEY_COUNT ? be.getKeyPress(i) * 0.7f : 0f;
            this.keyParts.get(i).y += ky[i];
        }
        this.roller.x += rx;
        this.roller.xRot -= feed * 0.5f;
        this.paper.x += rx;
        this.paperWritten.x += rx;
        this.paper.y -= feed * 0.4f;

        int full = state.getValue(TypewriterBlock.FULL);
        this.paper.visible = full == 1;
        this.paperWritten.visible = full == 2;

        this.typewriter.render(poseStack, vc, light, overlay);

        this.space.y -= sy;
        this.enter.y -= ey;
        for (int i = 0; i < ky.length; i++) this.keyParts.get(i).y -= ky[i];
        this.roller.x -= rx;
        this.roller.xRot += feed * 0.5f;
        this.paper.x -= rx;
        this.paperWritten.x -= rx;
        this.paper.y += feed * 0.4f;

        poseStack.popPose();
    }

    private ResourceLocation getTexture(BlockState state) {
        if (state.is(ObjectRegistry.TYPEWRITER_IRON.get())) {
            return IRON_TEXTURE;
        } else if (state.is(ObjectRegistry.TYPEWRITER_GOLD.get())) {
            return GOLD_TEXTURE;
        }
        return IRON_TEXTURE;
    }
}
