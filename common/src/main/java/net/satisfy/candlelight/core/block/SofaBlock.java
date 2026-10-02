package net.satisfy.candlelight.core.block;

import net.satisfy.foundation.util.ShapeUtil;
import net.minecraft.Util;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.Shapes;
import net.satisfy.foundation.block.LineConnectingType;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class SofaBlock extends net.satisfy.foundation.block.SofaBlock {
    public static final Map<Direction, VoxelShape> SHAPE;
    public static final Map<Direction, VoxelShape> MIDDLE_SHAPE;
    public static final Map<Direction, VoxelShape> LEFT_SHAPE;
    public static final Map<Direction, VoxelShape> RIGHT_SHAPE;

    private static final Supplier<VoxelShape> noneShapeSupplier = () -> {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.or(shape, Shapes.box(0.0625, 0.1875, 0, 0.9375, 0.4375, 1));
        shape = Shapes.or(shape, Shapes.box(0.6875, 0, 0.125, 0.875, 0.1875, 0.3125));
        shape = Shapes.or(shape, Shapes.box(0.6875, 0, 0.6875, 0.875, 0.1875, 0.875));
        shape = Shapes.or(shape, Shapes.box(0.125, 0, 0.125, 0.3125, 0.1875, 0.3125));
        shape = Shapes.or(shape, Shapes.box(0.125, 0, 0.6875, 0.3125, 0.1875, 0.875));
        shape = Shapes.or(shape, Shapes.box(0, 0.1875625, 0.0625, 0.125, 0.8125625, 0.9375));
        shape = Shapes.or(shape, Shapes.box(0.875, 0.1875625, 0.0625, 1, 0.8125625, 0.9375));
        shape = Shapes.or(shape, Shapes.box(0.0625, 0.4375, 0.875, 0.9375, 1, 1));

        return shape;
    };

    private static final Supplier<VoxelShape> middleShapeSupplier = () -> {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.or(shape, Shapes.box(0.0625, 0.1875, 0, 1, 0.4375, 1));
        shape = Shapes.or(shape, Shapes.box(0.0625, 0.4375, 0.875, 1, 1, 1));
        shape = Shapes.or(shape, Shapes.box(0, 0.1875, 0, 0.0625, 0.4375, 1));
        shape = Shapes.or(shape, Shapes.box(0, 0.4375, 0.875, 0.0625, 1, 1));
        return shape;
    };

    private static final Supplier<VoxelShape> leftShapeSupplier = () -> {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.or(shape, Shapes.box(0.0625, 0.1875, 0, 1, 0.4375, 1));
        shape = Shapes.or(shape, Shapes.box(0.125, 0, 0.125, 0.3125, 0.1875, 0.3125));
        shape = Shapes.or(shape, Shapes.box(0.125, 0, 0.6875, 0.3125, 0.1875, 0.875));
        shape = Shapes.or(shape, Shapes.box(0, 0.1875625, 0.0625, 0.125, 0.8125625, 0.9375));
        shape = Shapes.or(shape, Shapes.box(0.0625, 0.4375, 0.875, 1, 1, 1));

        return shape;
    };

    private static final Supplier<VoxelShape> rightShapeSupplier = () -> {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.or(shape, Shapes.box(0, 0.1875, 0, 0.9375, 0.4375, 1));
        shape = Shapes.or(shape, Shapes.box(0.6875, 0, 0.125, 0.875, 0.1875, 0.3125));
        shape = Shapes.or(shape, Shapes.box(0.6875, 0, 0.6875, 0.875, 0.1875, 0.875));
        shape = Shapes.or(shape, Shapes.box(0.875, 0.1875625, 0.0625, 1, 0.8125625, 0.9375));
        shape = Shapes.or(shape, Shapes.box(0, 0.4375, 0.875, 0.9375, 1, 1));

        return shape;
    };

    static {
        SHAPE = Util.make(new HashMap<>(), map -> {
            for (Direction direction : Direction.Plane.HORIZONTAL.stream().toList()) {
                map.put(direction, ShapeUtil.rotateShape(Direction.NORTH, direction, noneShapeSupplier.get()));
            }
        });
        MIDDLE_SHAPE = Util.make(new HashMap<>(), map -> {
            for (Direction direction : Direction.Plane.HORIZONTAL.stream().toList()) {
                map.put(direction, ShapeUtil.rotateShape(Direction.NORTH, direction, middleShapeSupplier.get()));
            }
        });
        LEFT_SHAPE = Util.make(new HashMap<>(), map -> {
            for (Direction direction : Direction.Plane.HORIZONTAL.stream().toList()) {
                map.put(direction, ShapeUtil.rotateShape(Direction.NORTH, direction, leftShapeSupplier.get()));
            }
        });
        RIGHT_SHAPE = Util.make(new HashMap<>(), map -> {
            for (Direction direction : Direction.Plane.HORIZONTAL.stream().toList()) {
                map.put(direction, ShapeUtil.rotateShape(Direction.NORTH, direction, rightShapeSupplier.get()));
            }
        });
    }

    public SofaBlock(Properties settings) {
        super(settings);
    }

    @Override
    protected VoxelShape getSofaShape(LineConnectingType type, Direction facing) {
        Map<Direction, VoxelShape> shapes = switch (type) {
            case MIDDLE -> MIDDLE_SHAPE;
            case LEFT -> LEFT_SHAPE;
            case RIGHT -> RIGHT_SHAPE;
            default -> SHAPE;
        };
        return shapes.get(facing);
    }
}
