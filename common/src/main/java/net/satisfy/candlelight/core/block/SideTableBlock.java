package net.satisfy.candlelight.core.block;

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

public class SideTableBlock extends LineConnectingBlock {
    public static final BooleanProperty HAS_LANTERN = BooleanProperty.create("has_lantern");
    public static final BooleanProperty HAS_BOOK = BooleanProperty.create("has_book");
    public static final BooleanProperty HAS_LAMP = BooleanProperty.create("has_lamp");
    private static final Supplier<VoxelShape> voxelShapeSupplier = () -> {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0, 0.375, 0, 1, 0.625, 1), BooleanOp.OR);
        return shape;
    };
    public static final Map<Direction, VoxelShape> SHAPE = Util.make(new HashMap<>(), map -> {
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            map.put(direction, ShapeUtil.rotateShape(Direction.NORTH, direction, voxelShapeSupplier.get()));
        }
    });

    public SideTableBlock(Properties properties) {
        super(properties.lightLevel(state -> (state.getValue(HAS_LANTERN) || state.getValue(HAS_LAMP)) ? 15 : 0));
        this.registerDefaultState(this.stateDefinition.any().setValue(HAS_LANTERN, false).setValue(HAS_BOOK, false).setValue(HAS_LAMP, false));
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE.get(state.getValue(FACING));
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
        builder.add(HAS_LANTERN, HAS_BOOK, HAS_LAMP);
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(ItemStack itemStack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult blockHitResult) {
        if (!world.isClientSide) {
            boolean isSneaking = player.isCrouching();
            if (player.getItemInHand(hand).is(Items.LANTERN) && !state.getValue(HAS_LANTERN) && !isSneaking) {
                world.setBlock(pos, state.setValue(HAS_LANTERN, true), 3);
                if (!player.getAbilities().instabuild) {
                    player.getItemInHand(hand).shrink(1);
                }
                world.playSound(null, pos, SoundEvents.LANTERN_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
                return ItemInteractionResult.sidedSuccess(false);
            } else if (player.getItemInHand(hand).is(Items.BOOK) && !state.getValue(HAS_BOOK) && !isSneaking) {
                world.setBlock(pos, state.setValue(HAS_BOOK, true), 3);
                if (!player.getAbilities().instabuild) {
                    player.getItemInHand(hand).shrink(1);
                }
                world.playSound(null, pos, SoundEvents.BOOK_PUT, SoundSource.BLOCKS, 1.0F, 1.0F);
                return ItemInteractionResult.sidedSuccess(false);


            } else if (Block.byItem(player.getItemInHand(hand).getItem()).equals(ObjectRegistry.LAMP.get()) && !state.getValue(HAS_LAMP) && !isSneaking) {
                world.setBlock(pos, state.setValue(HAS_LAMP, true), 3);
                if (!player.getAbilities().instabuild) {
                    player.getItemInHand(hand).shrink(1);
                }
                world.playSound(null, pos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
                return ItemInteractionResult.sidedSuccess(false);


            } else if (isSneaking && state.getValue(HAS_LANTERN)) {
                world.setBlock(pos, state.setValue(HAS_LANTERN, false), 3);
                popResource(world, pos, new ItemStack(Items.LANTERN));
                world.playSound(null, pos, SoundEvents.LANTERN_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
                return ItemInteractionResult.sidedSuccess(false);
            } else if (isSneaking && state.getValue(HAS_BOOK)) {
                world.setBlock(pos, state.setValue(HAS_BOOK, false), 3);
                popResource(world, pos, new ItemStack(Items.BOOK));
                world.playSound(null, pos, SoundEvents.WOOL_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
                return ItemInteractionResult.sidedSuccess(false);
            } else if (isSneaking && state.getValue(HAS_LAMP)) {
                world.setBlock(pos, state.setValue(HAS_LAMP, false), 3);
                popResource(world, pos, new ItemStack(ObjectRegistry.LAMP.get()));
                world.playSound(null, pos, SoundEvents.WOOD_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
                return ItemInteractionResult.sidedSuccess(false);
            }
        }
        return super.useItemOn(itemStack, state, world, pos, player, hand, blockHitResult);
    }

}
