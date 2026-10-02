package net.satisfy.candlelight.core.block;

import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.satisfy.foundation.block.LampBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.satisfy.candlelight.core.util.DinnerMenu;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.satisfy.candlelight.core.registry.EntityTypeRegistry;
import net.satisfy.foundation.storage.StorageBlock;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.satisfy.candlelight.Candlelight;
import net.satisfy.foundation.food.IngredientEffects;
import net.satisfy.foundation.storage.StorageBlockEntity;
import net.satisfy.candlelight.core.block.entity.TableSetBlockEntity;
import net.satisfy.candlelight.core.registry.MobEffectRegistry;
import net.satisfy.candlelight.core.registry.ObjectRegistry;
import net.satisfy.candlelight.core.util.Wearables;
import net.satisfy.candlelight.core.registry.StorageTypeRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("deprecation")
public class TableSetBlock extends StorageBlock {
    @Override
    public BlockEntityType<?> blockEntityType() {
        return EntityTypeRegistry.TABLE_SET_BLOCK_ENTITY.get();
    }

    public static final EnumProperty<PlateType> PLATE_TYPE = EnumProperty.create("plate_type", PlateType.class);
    public static final BooleanProperty WINE_GLASS = BooleanProperty.create("wine_glass");
    public static final BooleanProperty GLASS = BooleanProperty.create("glass");
    public static final BooleanProperty CLOCHE = BooleanProperty.create("cloche");
    public static final BooleanProperty NAPKIN = BooleanProperty.create("napkin");
    public static final BooleanProperty GLASS_DRINK = BooleanProperty.create("glass_drink");
    public static final BooleanProperty WINE_GLASS_DRINK = BooleanProperty.create("wine_glass_drink");
    private static final TagKey<Item> GLASS_DRINKS = TagKey.create(Registries.ITEM, Candlelight.identifier("glass_drinks"));
    private static final TagKey<Item> WINE_GLASS_DRINKS = TagKey.create(Registries.ITEM, Candlelight.identifier("wine_glass_drinks"));
    private static final TagKey<Block> TABLE_LIGHTS = TagKey.create(Registries.BLOCK, Candlelight.identifier("table_lights"));
    private static final TagKey<Item> SERVED_DISHES = TagKey.create(Registries.ITEM, Candlelight.identifier("served_dishes"));
    private static final int WELL_SERVED_TICKS_PER_COURSE = 1200;
    public static final int MAX_COURSES = 7;
    private static final int MENU_BONUS_TICKS = 6000;
    private static final int MAX_MENU_WELL_SERVED_TICKS = 18000;
    private static final VoxelShape GLASS_SHAPE = Block.box(0, 0, 12, 4, 8, 16);
    private static final VoxelShape WINE_GLASS_SHAPE = Block.box(4, 0, 12, 8, 14, 16);
    private static final VoxelShape NAPKIN_SHAPE = Block.box(1, 0, 3, 3, 1, 11);
    private static final float TABLE_FOOD_BONUS = 1.3F;
    private static final float ELEGANT_FOOD_BONUS = 1.5F;
    private static final int MAX_WELL_SERVED_TICKS = 12000;

    public TableSetBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(super.defaultBlockState()
                .setValue(WINE_GLASS, false)
                .setValue(GLASS, false)
                .setValue(CLOCHE, false)
                .setValue(NAPKIN, false)
                .setValue(GLASS_DRINK, false)
                .setValue(WINE_GLASS_DRINK, false));
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide()) {
            return null;
        }
        return (tickLevel, pos, tickState, blockEntity) -> {
            if (blockEntity instanceof TableSetBlockEntity tableSet && tickLevel instanceof ServerLevel serverLevel) {
                tableSet.serverTick(serverLevel, pos, tickState);
            }
        };
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TableSetBlockEntity(pos, state, this.size());
    }

    private static HashMap<Item, BooleanProperty> itemHashMap() {
        return Util.make(new HashMap<>(), map -> {
            map.put(ObjectRegistry.WINE_GLASS.get(), WINE_GLASS);
            map.put(ObjectRegistry.GLASS.get(), GLASS);
            map.put(ObjectRegistry.CLOCHE.get(), CLOCHE);
            map.put(ObjectRegistry.NAPKIN.get(), NAPKIN);
        });
    }

    private static Item getItemFromProperty(BooleanProperty property) {
        return itemHashMap().entrySet().stream()
                .filter(entry -> entry.getValue().equals(property))
                .map(Map.Entry::getKey)
                .findFirst().orElse(null);
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(ItemStack itemStack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);

        if (stack.isEmpty() && hasDrink(state)) {
            if (!world.isClientSide() && world.getBlockEntity(pos) instanceof TableSetBlockEntity tableSet) {
                boolean wineGlass = !state.getValue(GLASS_DRINK);
                world.setBlockAndUpdate(pos, state.setValue(wineGlass ? WINE_GLASS_DRINK : GLASS_DRINK, false));
                drink(world, pos, player, tableSet.getDrink(wineGlass));
                tableSet.setDrink(wineGlass, ItemStack.EMPTY);
            }
            return ItemInteractionResult.sidedSuccess(world.isClientSide());
        }

        boolean fitsWineGlass = stack.is(WINE_GLASS_DRINKS) && state.getValue(WINE_GLASS) && !state.getValue(WINE_GLASS_DRINK);
        boolean fitsGlass = stack.is(GLASS_DRINKS) && state.getValue(GLASS) && !state.getValue(GLASS_DRINK);
        if (fitsWineGlass || fitsGlass) {
            if (!world.isClientSide() && world.getBlockEntity(pos) instanceof TableSetBlockEntity tableSet) {
                world.setBlockAndUpdate(pos, state.setValue(fitsWineGlass ? WINE_GLASS_DRINK : GLASS_DRINK, true));
                tableSet.setDrink(fitsWineGlass, stack.copyWithCount(1));
                world.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
                if (!player.isCreative()) {
                    stack.shrink(1);
                }
            }
            return ItemInteractionResult.sidedSuccess(world.isClientSide());
        }

        HashMap<Item, BooleanProperty> items = itemHashMap();
        if (player.isShiftKeyDown() && state.getValue(CLOCHE)) {
            if (!world.isClientSide()) {
                world.setBlockAndUpdate(pos, state.setValue(CLOCHE, false));
                ItemStack clocheItem = new ItemStack(getItemFromProperty(CLOCHE));
                if (!player.getInventory().add(clocheItem)) {
                    player.drop(clocheItem, false);
                }
            }
            return ItemInteractionResult.sidedSuccess(world.isClientSide());
        }

        Item item = stack.getItem();
        if (!items.containsKey(item)) return super.useItemOn(itemStack, state, world, pos, player, hand, hit);
        BooleanProperty property = items.get(item);
        if (state.getValue(property)) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        if (!world.isClientSide()) {
            world.setBlockAndUpdate(pos, state.setValue(property, true));
            if (!player.isCreative()) stack.shrink(1);
        }
        return ItemInteractionResult.sidedSuccess(world.isClientSide());
    }

    @Override
    public void remove(Level world, BlockPos blockPos, Player player, StorageBlockEntity storageBlockEntity, int i) {
        BlockState state = world.getBlockState(blockPos);
        if (state.getValue(CLOCHE) || world.isClientSide()) return;

        ItemStack itemStack = storageBlockEntity.removeStack(i);
        world.playSound(null, blockPos, SoundEvents.GENERIC_EAT, SoundSource.BLOCKS, 1.0F, 1.0F);
        FoodProperties foodComponent = itemStack.get(DataComponents.FOOD);
        if (foodComponent != null) {
            float bonus = Wearables.isElegantlyDressed(player) ? ELEGANT_FOOD_BONUS : TABLE_FOOD_BONUS;
            player.getFoodData().eat(Math.round(foodComponent.nutrition() * bonus), foodComponent.saturation() * bonus);
            foodComponent.effects().forEach(possibleEffect -> {
                if (world.random.nextFloat() < possibleEffect.probability()) {
                    player.addEffect(new MobEffectInstance(possibleEffect.effect()));
                }
            });
        }
        for (Pair<MobEffectInstance, Float> effect : IngredientEffects.getEffects(itemStack)) {
            if (effect.getFirst() != null && world.random.nextFloat() < effect.getSecond()) {
                player.addEffect(new MobEffectInstance(effect.getFirst()));
            }
        }
        if (isServedDish(itemStack)) {
            serve(world, blockPos, state, player, itemStack);
        }
        world.gameEvent(player, GameEvent.BLOCK_CHANGE, blockPos);
    }

    public static int countCourses(Level level, BlockPos pos, BlockState state, Player player) {
        int courses = 1;
        if (hasLight(level, pos)) courses++;
        if (state.getValue(NAPKIN)) courses++;
        if (state.getValue(GLASS)) courses++;
        if (state.getValue(WINE_GLASS)) courses++;
        if (hasDrink(state)) courses++;
        if (Wearables.isElegantlyDressed(player)) courses++;
        return courses;
    }

    public static boolean hasLight(Level level, BlockPos pos) {
        for (BlockPos neighbor : BlockPos.betweenClosed(pos.offset(-1, 0, -1), pos.offset(1, 0, 1))) {
            BlockState neighborState = level.getBlockState(neighbor);
            if (neighborState.is(TABLE_LIGHTS) && isLit(neighborState)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isLit(BlockState state) {
        if (state.hasProperty(BlockStateProperties.LIT)) return state.getValue(BlockStateProperties.LIT);
        if (state.hasProperty(LampBlock.LUMINANCE)) return state.getValue(LampBlock.LUMINANCE);
        return true;
    }

    private static void drink(Level level, BlockPos pos, Player player, ItemStack drink) {
        level.playSound(null, pos, SoundEvents.GENERIC_DRINK, SoundSource.PLAYERS, 1.0F, 1.0F);
        if (drink.isEmpty()) {
            return;
        }
        ItemStack remainder = drink.getItem().finishUsingItem(drink.copy(), level, player);
        if (!remainder.isEmpty() && !ItemStack.isSameItem(remainder, drink) && !player.getInventory().add(remainder)) {
            player.drop(remainder, false);
        }
    }

    public static boolean hasDrink(BlockState state) {
        return state.getValue(GLASS_DRINK) || state.getValue(WINE_GLASS_DRINK);
    }

    public static boolean isServedDish(ItemStack stack) {
        return stack.is(SERVED_DISHES);
    }

    private static void serve(Level world, BlockPos pos, BlockState state, Player player, ItemStack dish) {
        Level level = world;
        int courses = countCourses(world, pos, state, player);

        Holder<MobEffect> wellServed = MobEffectRegistry.holder(MobEffectRegistry.WELL_SERVED);
        MobEffectInstance current = player.getEffect(wellServed);
        int duration = courses * WELL_SERVED_TICKS_PER_COURSE + (current != null ? current.getDuration() : 0);
        player.addEffect(new MobEffectInstance(wellServed, Math.min(duration, MAX_WELL_SERVED_TICKS)));
        if (world instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, pos.getX() + 0.5, pos.getY() + 0.3, pos.getZ() + 0.5, 4 + courses * 2, 0.3, 0.15, 0.3, 0.0);
        }
        DinnerMenu.Result menu = DinnerMenu.eat(player, dish);
        Component message = Component.translatable("message.candlelight.table_set.served", courses, MAX_COURSES);
        if (menu != null && menu.complete()) {
            MobEffectInstance served = player.getEffect(wellServed);
            int bonus = MENU_BONUS_TICKS + (served != null ? served.getDuration() : 0);
            player.addEffect(new MobEffectInstance(wellServed, Math.min(bonus, MAX_MENU_WELL_SERVED_TICKS)));
            player.addEffect(new MobEffectInstance(MobEffectRegistry.holder(MobEffectRegistry.REFRESHED), MENU_BONUS_TICKS));
            level.playSound(null, pos, SoundEvents.PLAYER_LEVELUP, SoundSource.PLAYERS, 0.6F, 1.4F);
            message = Component.translatable("message.candlelight.table_set.menu_complete");
        } else if (menu != null && menu.step() > 0) {
            message = Component.translatable("message.candlelight.table_set.menu_course", message, Component.translatable("hud.candlelight.table_set.course." + menu.course().name().toLowerCase()), menu.step(), DinnerMenu.COURSES);
        }
        player.displayClientMessage(message.copy().withStyle(ChatFormatting.GOLD), true);
    }

    @Override
    public @NotNull List<ItemStack> getDrops(BlockState blockState, LootParams.Builder builder) {
        List<ItemStack> list = super.getDrops(blockState, builder);
        PlateType type = blockState.getValue(PLATE_TYPE);
        switch (type) {
            case PLATE:
                list.add(new ItemStack(ObjectRegistry.PLATE.get()));
                break;
            case BOWL:
                list.add(new ItemStack(ObjectRegistry.BOWL.get()));
                break;
        }
        for (BooleanProperty property : itemHashMap().values()) {
            if (!blockState.getValue(property)) continue;
            Item item = getItemFromProperty(property);
            if (item == null) continue;
            list.add(new ItemStack(item));
        }
        return list;
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public ResourceLocation type() {
        return StorageTypeRegistry.TABLE_SET;
    }

    @Override
    public Direction[] unAllowedDirections() {
        return new Direction[0];
    }

    @Override
    public boolean canInsertStack(ItemStack stack) {
        return stack.has(DataComponents.FOOD);
    }

    @Override
    public int getSection(Float x, Float y) {
        return 0;
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (!state.canSurvive(world, pos)) {
            world.destroyBlock(pos, true);
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        VoxelShape shape = world.getBlockState(pos.below()).getShape(world, pos.below());
        return net.minecraft.world.level.block.Block.isFaceFull(shape, Direction.UP);
    }

    @Override
    public @NotNull BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (!state.canSurvive(world, pos)) {
            world.scheduleTick(pos, this, 1);
        }
        return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag tooltipFlag) {
        tooltip.add(Component.translatable("tooltip.farm_and_charm.canbeplaced").withStyle(ChatFormatting.GRAY));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(PLATE_TYPE);
        builder.add(WINE_GLASS, GLASS, CLOCHE, NAPKIN, GLASS_DRINK, WINE_GLASS_DRINK);
    }

    private VoxelShape makeBowlShape() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.25, 0, 0.125, 0.3125, 0.1875, 0.75), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.8125, 0, 0.125, 0.875, 0.1875, 0.75), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.3125, 0, 0.125, 0.8125, 0.1875, 0.1875), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.3125, 0, 0.6875, 0.8125, 0.1875, 0.75), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.3125, 0, 0.1875, 0.8125, 0.0625, 0.6875), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.875, 0.1875, 0.3125, 1, 0.1875, 0.5625), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.125, 0.1875, 0.3125, 0.25, 0.1875, 0.5625), BooleanOp.OR);
        return shape;
    }

    private VoxelShape makePlateShape() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.25, 0, 0.0625, 0.9375, 0.0625, 0.75), BooleanOp.OR);
        return shape;
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        PlateType type = state.getValue(PLATE_TYPE);
        VoxelShape shape = type.equals(PlateType.BOWL) ? makeBowlShape() : makePlateShape();
        if (state.getValue(GLASS)) shape = Shapes.or(shape, GLASS_SHAPE);
        if (state.getValue(WINE_GLASS)) shape = Shapes.or(shape, WINE_GLASS_SHAPE);
        if (state.getValue(NAPKIN)) shape = Shapes.or(shape, NAPKIN_SHAPE);
        return rotateShape(direction, shape);
    }

    private VoxelShape rotateShape(Direction direction, VoxelShape shape) {
        if (direction == Direction.NORTH) return shape;
        VoxelShape[] rotatedShapes = new VoxelShape[]{shape};
        for (int i = 0; i < (direction.get2DDataValue() - Direction.NORTH.get2DDataValue() + 4) % 4; i++) {
            rotatedShapes[0] = rotateShapeClockwise(rotatedShapes[0]);
        }
        return rotatedShapes[0];
    }

    private VoxelShape rotateShapeClockwise(VoxelShape shape) {
        VoxelShape[] result = {Shapes.empty()};
        shape.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> {
            double newMinX = 1 - maxZ;
            double newMaxX = 1 - minZ;
            result[0] = Shapes.or(result[0], Shapes.box(newMinX, minY, minX, newMaxX, maxY, maxX));
        });
        return result[0];
    }

    public enum PlateType implements StringRepresentable {
        PLATE("plate"),
        BOWL("bowl");
        private final String name;

        PlateType(String name) {
            this.name = name;
        }

        @Override
        public @NotNull String getSerializedName() {
            return this.name;
        }
    }
}
