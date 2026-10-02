package net.satisfy.candlelight.core.block;

import net.satisfy.foundation.util.ShapeUtil;
import com.mojang.serialization.MapCodec;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.satisfy.candlelight.core.block.entity.TypewriterEntity;
import net.satisfy.candlelight.core.registry.ObjectRegistry;
import net.satisfy.candlelight.core.util.CandlelightUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class TypewriterBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING;

    public static final IntegerProperty FULL = IntegerProperty.create("full", 0, 2);

    public static final Map<Direction, VoxelShape> SHAPE;

    private static final Supplier<VoxelShape> voxelShapeSupplier = () -> {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.0625, 0.9375, 0.125, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.3125, 0.125, 0.0625, 0.6875, 0.1875, 0.125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.1875, 0.125, 0.1875, 0.3125, 0.1875, 0.3125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.375, 0.125, 0.1875, 0.5, 0.1875, 0.3125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.5625, 0.125, 0.1875, 0.6875, 0.1875, 0.3125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.75, 0.125, 0.1875, 0.875, 0.1875, 0.3125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.53125, 0.125, 0.3125, 0.65625, 0.25, 0.4375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.71875, 0.125, 0.3125, 0.84375, 0.25, 0.4375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.34375, 0.125, 0.3125, 0.46875, 0.25, 0.4375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.15625, 0.125, 0.3125, 0.28125, 0.25, 0.4375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.125, 0.4375, 0.9375, 0.4375, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.8125, 0.4375, 0.75, 0.9375, 0.6875, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.4375, 0.75, 0.1875, 0.6875, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.1875, 0.5, 0.75, 0.8125, 0.5625, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.1875, 0.53125, 0.875, 0.8125, 0.65625, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.25, 0.375, 0.84375, 0.75, 0.75, 0.84375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 0.125, 0.1875, 0.1875, 0.1875, 0.3125), BooleanOp.OR);

        return shape;
    };

    static {
        FACING = HorizontalDirectionalBlock.FACING;
        SHAPE = Util.make(new HashMap<>(), map -> {
            for (Direction direction : Direction.Plane.HORIZONTAL.stream().toList()) {
                map.put(direction, ShapeUtil.rotateShape(Direction.NORTH, direction, voxelShapeSupplier.get()));
            }
        });
    }

    public TypewriterBlock(Properties settings) {
        super(settings);
    }

    public static final MapCodec<TypewriterBlock> CODEC = simpleCodec(TypewriterBlock::new);

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(ItemStack itemStack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.getItem() == ObjectRegistry.NOTE_PAPER.get() && state.getValue(FULL) == 0) {
            world.setBlock(pos, state.setValue(FULL, 1), 2);
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof TypewriterEntity typeWriterEntity) {
                typeWriterEntity.addPaper(new ItemStack(ObjectRegistry.NOTE_PAPER_WRITEABLE.get()));
                stack.setCount(stack.getCount() - 1);
            }
            return ItemInteractionResult.SUCCESS;
        } else if (state.getValue(FULL) == 1) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof TypewriterEntity typeWriterEntity) {
                if (world.isClientSide)
                    CandlelightUtil.setTypeWriterScreen(player, typeWriterEntity);
            }
            return ItemInteractionResult.SUCCESS;
        } else if (state.getValue(FULL) == 2) {
            world.setBlock(pos, state.setValue(FULL, 0), 2);
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof TypewriterEntity typeWriterEntity) {
                ItemStack paper = typeWriterEntity.getPaper();
                ItemStack result = new ItemStack(ObjectRegistry.NOTE_PAPER_WRITTEN.get());

                if (paper.has(DataComponents.CUSTOM_DATA))
                    result.set(DataComponents.CUSTOM_DATA, paper.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY));
                result.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().put("author", StringTag.valueOf(player.getName().getString()));
                player.addItem(result);

                typeWriterEntity.removePaper();
            }
            return ItemInteractionResult.SUCCESS;
        }
        return super.useItemOn(itemStack, state, world, pos, player, hand, hit);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE.get(state.getValue(FACING));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite()).setValue(FULL, 0);
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (!blockState.is(blockState2.getBlock())) {
            BlockEntity blockEntity = level.getBlockEntity(blockPos);
            if (blockEntity instanceof TypewriterEntity typeWriterEntity) {
                ItemStack dropStack = typeWriterEntity.getPaper();
                Containers.dropItemStack(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), dropStack);
            }
        }
        super.onRemove(blockState, level, blockPos, blockState2, bl);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING).add(FULL);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag tooltipFlag) {
        tooltip.add(Component.translatable("tooltip.farm_and_charm.canbeplaced").withStyle(ChatFormatting.GRAY));
    }

    public boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TypewriterEntity(pos, state);
    }

    @Override
    public @NotNull RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }
}
