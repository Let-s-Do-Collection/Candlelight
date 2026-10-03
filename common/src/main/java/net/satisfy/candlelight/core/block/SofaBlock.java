package net.satisfy.candlelight.core.block;

import net.minecraft.Util;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.satisfy.foundation.block.LineConnectingType;
import net.satisfy.foundation.util.ShapeUtil;
import java.util.EnumMap;
import java.util.Map;

public class SofaBlock extends net.satisfy.foundation.block.SofaBlock {
    private static final VoxelShape SINGLE = Shapes.or(Shapes.empty(), Block.box(12, 0, 2, 15, 3, 5), Block.box(12, 0, 11, 15, 3, 14), Block.box(1, 0, 2, 4, 3, 5), Block.box(1, 0, 11, 4, 3, 14), Block.box(0, 3, 15, 16, 16, 16), Block.box(2, 3, 0, 14, 4, 15), Block.box(0, 3.001, 1, 2, 13.001, 15), Block.box(2, 4, 0, 14, 7, 15), Block.box(14, 3.001, 1, 16, 13.001, 15), Block.box(0, 13, 12, 16, 16, 15), Block.box(2, 7, 12, 14, 13, 15));
    private static final VoxelShape END = Shapes.or(Shapes.empty(), Block.box(12, 0, 2, 15, 3, 5), Block.box(12, 0, 11, 15, 3, 14), Block.box(0, 3, 15, 16, 16, 16), Block.box(0, 3, 0, 14, 4, 15), Block.box(0, 4, 0, 14, 7, 15), Block.box(14, 3.001, 1, 16, 13.001, 15), Block.box(0, 13, 12, 16, 16, 15), Block.box(0, 7, 12, 14, 13, 15));
    private static final VoxelShape END_MIRRORED = Shapes.or(Shapes.empty(), Block.box(1, 0, 2, 4, 3, 5), Block.box(1, 0, 11, 4, 3, 14), Block.box(0, 3, 15, 16, 16, 16), Block.box(2, 3, 0, 16, 4, 15), Block.box(2, 4, 0, 16, 7, 15), Block.box(0, 3.001, 1, 2, 13.001, 15), Block.box(0, 13, 12, 16, 16, 15), Block.box(2, 7, 12, 16, 13, 15));
    private static final VoxelShape MIDDLE = Shapes.or(Shapes.empty(), Block.box(0, 3, 15, 16, 16, 16), Block.box(0, 3, 0, 16, 4, 15), Block.box(0, 4, 0, 16, 7, 15), Block.box(0, 7, 12, 16, 16, 15));
    private static final Map<LineConnectingType, Map<Direction, VoxelShape>> SHAPES = Util.make(new EnumMap<>(LineConnectingType.class), map -> {
        for (LineConnectingType type : LineConnectingType.values()) {
            VoxelShape base = switch (type) {
                case MIDDLE -> MIDDLE;
                case LEFT -> END_MIRRORED;
                case RIGHT -> END;
                default -> SINGLE;
            };
            Map<Direction, VoxelShape> rotated = new EnumMap<>(Direction.class);
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                rotated.put(direction, ShapeUtil.rotateShape(Direction.NORTH, direction, base));
            }
            map.put(type, rotated);
        }
    });

    public SofaBlock(Properties settings) {
        super(settings);
    }

    @Override
    protected VoxelShape getSofaShape(LineConnectingType type, Direction facing) {
        return SHAPES.get(type).get(facing);
    }
}
