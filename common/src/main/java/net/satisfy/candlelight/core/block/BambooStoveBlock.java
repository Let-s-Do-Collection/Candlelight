package net.satisfy.candlelight.core.block;

import net.satisfy.foundation.util.ShapeUtil;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class BambooStoveBlock extends StoveBlock {
    private static final Supplier<VoxelShape> voxelShapeSupplier = () -> {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0, 0, 0.8125, 0.1875, 0.125, 1), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0, 0, 0, 0.1875, 0.125, 0.1875), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.8125, 0, 0.8125, 1, 0.125, 1), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.8125, 0, 0, 1, 0.125, 0.1875), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0, 0.125, 0, 1, 1, 1), BooleanOp.OR);
        return shape;
    };
    public static final Map<Direction, VoxelShape> SHAPE = Util.make(new HashMap<>(), map -> {
        for (Direction direction : Direction.Plane.HORIZONTAL.stream().toList()) {
            map.put(direction, ShapeUtil.rotateShape(Direction.NORTH, direction, voxelShapeSupplier.get()));
        }
    });

    public BambooStoveBlock(Properties settings) {
        super(settings);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE.get(state.getValue(FACING));
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (state.getValue(LIT)) {
            double d = (double) pos.getX() + 0.5;
            double e = pos.getY() + 0.24;
            double f = (double) pos.getZ() + 0.5;
            if (random.nextDouble() < 0.1)
                world.playLocalSound(d, e, f, SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS, 1.0f, 1.0f, false);
            world.playLocalSound(d, e, f, SoundEvents.SMOKER_SMOKE, SoundSource.BLOCKS, 1.0f, 1.0f, false);
            world.playLocalSound(d, e, f, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0f, 1.0f, false);

            for (Direction direction : Direction.values()) {
                if (direction != Direction.DOWN && direction != state.getValue(FACING)) {
                    Direction.Axis axis = direction.getAxis();
                    double h = random.nextDouble() * 0.6 - 0.3;
                    double i = axis == Direction.Axis.X ? (double) direction.getStepX() * 0.52 : h;
                    double j = random.nextDouble() * 6.0 / 16.0;
                    double k = axis == Direction.Axis.Z ? (double) direction.getStepZ() * 0.52 : h;
                    world.addParticle(ParticleTypes.SMOKE, d + i, e + j, f + k, 0.0, 0.0, 0.0);
                    world.addParticle(ParticleTypes.FLAME, d + i, e + j, f + k, 0.0, 0.0, 0.0);
                }
            }

            double particleHeight = pos.getY() + 0.5 + 1.0;
            world.addParticle(ParticleTypes.SMOKE, d, particleHeight, f, 0.0, 0.0, 0.0);
        }
    }
}