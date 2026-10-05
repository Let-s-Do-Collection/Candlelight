package net.satisfy.candlelight.core.registry;

import net.satisfy.candlelight.core.block.entity.SideTableBlockEntity;
import net.satisfy.foundation.block.WallDecorationBlockEntity;
import net.satisfy.foundation.block.CabinetBlockEntity;
import net.satisfy.foundation.banner.CompletionistBannerEntity;
import net.satisfy.foundation.storage.StorageBlockEntity;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.satisfy.candlelight.Candlelight;
import net.satisfy.candlelight.core.block.entity.*;
import net.satisfy.foundation.food.IngredientEffectFoodBlockEntity;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

import static net.satisfy.candlelight.core.registry.ObjectRegistry.*;

public class EntityTypeRegistry {
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Candlelight.MOD_ID, Registries.BLOCK_ENTITY_TYPE);
    private static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Candlelight.MOD_ID, Registries.ENTITY_TYPE);

    public static final RegistrySupplier<BlockEntityType<StorageBlockEntity>> STORAGE_BLOCK_ENTITY = registerBlockEntity("storage", () -> BlockEntityType.Builder.of((pos, state) -> new StorageBlockEntity(EntityTypeRegistry.STORAGE_BLOCK_ENTITY.get(), pos, state), StorageTypeRegistry.registerBlocks(new HashSet<>()).toArray(new Block[0])).build(null));
    public static final RegistrySupplier<BlockEntityType<SideBoardBlockEntity>> SIDEBOARD_BLOCK_ENTITY = registerBlockEntity("sideboard", () -> BlockEntityType.Builder.of(SideBoardBlockEntity::new, SIDEBOARD.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<LargeCookingPotBlockEntity>> LARGE_COOKING_POT_BLOCK_ENTITY = registerBlockEntity("large_cooking_pot", () -> BlockEntityType.Builder.of(LargeCookingPotBlockEntity::new, COOKING_POT.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<TypewriterEntity>> TYPE_WRITER_BLOCK_ENTITY = registerBlockEntity("type_writer", () -> BlockEntityType.Builder.of(TypewriterEntity::new, TYPEWRITER_IRON.get(), TYPEWRITER_GOLD.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<CookingPanBlockEntity>> COOKING_PAN_BLOCK_ENTITY = registerBlockEntity("cooking_pan", () -> BlockEntityType.Builder.of(CookingPanBlockEntity::new, COOKING_PAN.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<DinnerBellBlockEntity>> DINNER_BELL_BLOCK_ENTITY = registerBlockEntity("dinner_bell", () -> BlockEntityType.Builder.of(DinnerBellBlockEntity::new, DINNER_BELL.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<CandlelightStoveBlockEntity>> STOVE_BLOCK_ENTITY = registerBlockEntity("stove_block", () -> BlockEntityType.Builder.of(CandlelightStoveBlockEntity::new, COBBLESTONE_STOVE.get(), MUD_STOVE.get(), GRANITE_STOVE.get(), SANDSTONE_STOVE.get(), STONE_BRICKS_STOVE.get(), RED_NETHER_BRICKS_STOVE.get(), DEEPSLATE_STOVE.get(), QUARTZ_STOVE.get(), END_STOVE.get(), BASALT_STOVE.get(), BAMBOO_STOVE.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<CabinetBlockEntity>> CABINET_BLOCK_ENTITY = registerBlockEntity("cabinet", () -> BlockEntityType.Builder.of((pos, state) -> new CabinetBlockEntity(EntityTypeRegistry.CABINET_BLOCK_ENTITY.get(), pos, state), addCabinet(new HashSet<>()).toArray(new Block[0])).build(null));
    public static final RegistrySupplier<BlockEntityType<CompletionistBannerEntity>> CANDLELIGHT_BANNER_ENTITY = registerBlockEntity("candlelight_banner_entity", () -> BlockEntityType.Builder.of(CompletionistBannerEntity::new, CANDLELIGHT_BANNER.get(), CANDLELIGHT_WALL_BANNER.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<StorageBlockEntity>> TABLE_SET_BLOCK_ENTITY = registerBlockEntity("table_set", () -> BlockEntityType.Builder.<StorageBlockEntity>of(TableSetBlockEntity::new, TABLE_SET.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<IngredientEffectFoodBlockEntity>> EFFECT_FOOD_BLOCK_ENTITY = registerBlockEntity("effect_food_block", () -> BlockEntityType.Builder.of((pos, state) -> new IngredientEffectFoodBlockEntity(EntityTypeRegistry.EFFECT_FOOD_BLOCK_ENTITY.get(), pos, state), LASAGNE_BLOCK.get(), TOMATO_MOZZARELLA_BLOCK.get(), PORK_RIBS_BLOCK.get(), FRESH_GARDEN_SALAD_BLOCK.get(), BEEF_WELLINGTON_BLOCK.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<SideTableBlockEntity>> SIDE_TABLE_BLOCK_ENTITY = registerBlockEntity("side_table", () -> BlockEntityType.Builder.of(SideTableBlockEntity::new, SIDE_TABLE.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<TableSignBlockEntity>> TABLE_SIGN_BLOCK_ENTITY = registerBlockEntity("table_sign", () -> BlockEntityType.Builder.of(TableSignBlockEntity::new, TABLE_SIGN.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<WallDecorationBlockEntity>> WALL_DECORATION = registerBlockEntity("wall_decoration", () -> BlockEntityType.Builder.of((pos, state) -> new WallDecorationBlockEntity(EntityTypeRegistry.WALL_DECORATION.get(), pos, state), HEART.get()).build(null));

    private static <T extends BlockEntityType<?>> RegistrySupplier<T> registerBlockEntity(final String path, final Supplier<T> type) {
        return BLOCK_ENTITY_TYPES.register(Candlelight.identifier(path), type);
    }

    public static void init() {
        ENTITY_TYPES.register();
        BLOCK_ENTITY_TYPES.register();
    }

    private static Set<Block> addCabinet(Set<Block> blocks) {
        blocks.add(DRAWER.get());
        blocks.add(OAK_DRAWER.get());
        blocks.add(BIRCH_DRAWER.get());
        blocks.add(SPRUCE_DRAWER.get());
        blocks.add(DARK_OAK_DRAWER.get());
        blocks.add(ACACIA_DRAWER.get());
        blocks.add(JUNGLE_DRAWER.get());
        blocks.add(MANGROVE_DRAWER.get());
        blocks.add(WARPED_DRAWER.get());
        blocks.add(CRIMSON_DRAWER.get());
        blocks.add(CHERRY_DRAWER.get());
        blocks.add(BAMBOO_DRAWER.get());
        blocks.add(CABINET.get());
        blocks.add(OAK_CABINET.get());
        blocks.add(BIRCH_CABINET.get());
        blocks.add(SPRUCE_CABINET.get());
        blocks.add(DARK_OAK_CABINET.get());
        blocks.add(ACACIA_CABINET.get());
        blocks.add(JUNGLE_CABINET.get());
        blocks.add(MANGROVE_CABINET.get());
        blocks.add(WARPED_CABINET.get());
        blocks.add(CRIMSON_CABINET.get());
        blocks.add(CHERRY_CABINET.get());
        blocks.add(BAMBOO_CABINET.get());
        return blocks;
    }
}
