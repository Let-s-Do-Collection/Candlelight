package net.satisfy.candlelight.core.block;

import net.satisfy.foundation.block.LineConnectingType;
import java.util.EnumMap;
import org.jetbrains.annotations.Nullable;
import net.satisfy.candlelight.core.block.entity.SideTableBlockEntity;
import net.minecraft.world.Containers;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.EntityBlock;
import net.satisfy.foundation.util.ShapeUtil;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.satisfy.candlelight.core.registry.ObjectRegistry;
import net.satisfy.foundation.block.LineConnectingBlock;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class SideTableBlock extends LineConnectingBlock implements EntityBlock {
    public static final EnumProperty<Light> LIGHT = EnumProperty.create("light", Light.class);
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    private static final VoxelShape SINGLE_SHAPE = Shapes.or(Block.box(2, 8, 2, 14, 10, 14), Block.box(3, 0, 3, 5, 8, 5), Block.box(11, 0, 3, 13, 8, 5), Block.box(3, 0, 11, 5, 8, 13), Block.box(11, 0, 11, 13, 8, 13), Block.box(4, 2, 4, 12, 3, 12));
    private static final VoxelShape END_SHAPE = Shapes.or(Block.box(2, 8, 2, 16, 10, 14), Block.box(3, 0, 3, 5, 8, 5), Block.box(3, 0, 11, 5, 8, 13), Block.box(4, 2, 4, 16, 3, 12));
    private static final VoxelShape MIDDLE_SHAPE = Shapes.or(Block.box(0, 8, 2, 16, 10, 14), Block.box(0, 2, 4, 16, 3, 12));
    private static final Map<LineConnectingType, Map<Direction, VoxelShape>> SHAPES = Util.make(new EnumMap<>(LineConnectingType.class), map -> {
        for (LineConnectingType type : LineConnectingType.values()) {
            VoxelShape base = switch (type) {
                case NONE -> SINGLE_SHAPE;
                case MIDDLE -> MIDDLE_SHAPE;
                default -> END_SHAPE;
            };
            Map<Direction, VoxelShape> rotated = new EnumMap<>(Direction.class);
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                Direction modelDirection = type == LineConnectingType.RIGHT ? direction.getOpposite() : direction;
                rotated.put(direction, ShapeUtil.rotateShape(Direction.NORTH, modelDirection, base));
            }
            map.put(type, rotated);
        }
    });

    public SideTableBlock(Properties properties) {
        super(properties.lightLevel(state -> state.getValue(LIGHT) != Light.NONE && state.getValue(LIT) ? 15 : 0));
        registerDefaultState(stateDefinition.any().setValue(LIGHT, Light.NONE).setValue(LIT, false));
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPES.get(state.getValue(TYPE)).get(state.getValue(FACING));
    }

    @Override
    public @NotNull BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (!state.canSurvive(world, pos)) {
            world.scheduleTick(pos, this, 1);
        }
        return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(LIGHT, LIT);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SideTableBlockEntity(pos, state);
    }

    private static Light lightFor(ItemStack stack) {
        if (stack.is(ObjectRegistry.LAMP.get().asItem())) return Light.LAMP;
        if (stack.is(Items.LANTERN) || stack.is(Items.SOUL_LANTERN)) return Light.LANTERN;
        return Light.NONE;
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!(level.getBlockEntity(pos) instanceof SideTableBlockEntity table)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        boolean hasLight = state.getValue(LIGHT) != Light.NONE;
        ItemStack items = table.getItems();
        if (stack.isEmpty()) {
            if (level.isClientSide()) return ItemInteractionResult.SUCCESS;
            if (player.isShiftKeyDown()) {
                if (hasLight) {
                    giveBack(player, table.getLight());
                    table.setLight(ItemStack.EMPTY);
                    level.setBlockAndUpdate(pos, state.setValue(LIGHT, Light.NONE).setValue(LIT, false));
                    level.playSound(null, pos, SoundEvents.LANTERN_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
                    return ItemInteractionResult.SUCCESS;
                }
                if (!items.isEmpty()) {
                    giveBack(player, items);
                    table.setItems(ItemStack.EMPTY);
                    level.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 0.6F, 1.0F);
                    return ItemInteractionResult.SUCCESS;
                }
                return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
            }
            if (hasLight) {
                boolean lit = !state.getValue(LIT);
                level.setBlockAndUpdate(pos, state.setValue(LIT, lit));
                level.playSound(null, pos, SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_ON, SoundSource.BLOCKS, 1.0F, lit ? 1.1F : 0.9F);
                return ItemInteractionResult.SUCCESS;
            }
            if (!items.isEmpty()) {
                giveBack(player, items.split(1));
                table.setItems(items);
                level.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 0.6F, 1.0F);
                return ItemInteractionResult.SUCCESS;
            }
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        if (hasLight) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        Light light = lightFor(stack);
        if (light != Light.NONE && items.isEmpty()) {
            if (!level.isClientSide()) {
                table.setLight(stack.copyWithCount(1));
                level.setBlockAndUpdate(pos, state.setValue(LIGHT, light).setValue(LIT, true));
                level.playSound(null, pos, SoundEvents.LANTERN_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
                stack.consume(1, player);
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide());
        }
        boolean canStack = items.isEmpty() || ItemStack.isSameItemSameComponents(items, stack) && items.getCount() < SideTableBlockEntity.maxFor(items);
        if (!canStack) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        if (!level.isClientSide()) {
            ItemStack placed = items.isEmpty() ? stack.copyWithCount(1) : items.copyWithCount(items.getCount() + 1);
            table.setItems(placed);
            level.playSound(null, pos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 0.7F, 1.2F);
            stack.consume(1, player);
        }
        return ItemInteractionResult.sidedSuccess(level.isClientSide());
    }

    private static void giveBack(Player player, ItemStack stack) {
        if (!stack.isEmpty() && !player.getInventory().add(stack)) {
            player.drop(stack, false);
        }
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.is(newState.getBlock()) && level.getBlockEntity(pos) instanceof SideTableBlockEntity table) {
            Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), table.getItems());
            Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), table.getLight());
        }
        super.onRemove(state, level, pos, newState, moved);
    }

    public enum Light implements StringRepresentable {
        NONE("none"),
        LAMP("lamp"),
        LANTERN("lantern");

        private final String name;

        Light(String name) {
            this.name = name;
        }

        @Override
        public @NotNull String getSerializedName() {
            return name;
        }
    }
}
