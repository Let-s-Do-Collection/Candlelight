package net.satisfy.candlelight.core.registry;

import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.satisfy.foundation.block.LampBlock;
import net.satisfy.foundation.block.WallDecorationBlock;
import net.satisfy.foundation.block.CabinetBlock;
import net.minecraft.world.effect.MobEffects;
import net.satisfy.foundation.banner.CompletionistWallBannerBlock;
import net.satisfy.foundation.banner.CompletionistBannerBlock;
import net.satisfy.foundation.banner.BannerSettings;
import net.satisfy.foundation.block.EatableBoxBlock;
import net.satisfy.foundation.block.BonemealableFlowerBlock;
import net.satisfy.foundation.block.ChairBlock;
import net.satisfy.foundation.block.FacingBlock;
import net.satisfy.foundation.block.LineConnectingBlock;
import net.satisfy.foundation.block.SinkBlock;
import net.satisfy.foundation.block.StackableBlock;
import net.satisfy.foundation.util.RegistryUtil;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.PushReaction;
import net.satisfy.candlelight.Candlelight;
import net.satisfy.candlelight.core.block.*;
import net.satisfy.candlelight.core.item.*;
import net.satisfy.candlelight.core.util.CandlelightFoods;
import net.satisfy.farm_and_charm.core.block.*;
import net.satisfy.foundation.food.PlaceableEffectFoodItem;
import net.satisfy.foundation.food.PlaceableIngredientEffectFoodItem;
import net.satisfy.foundation.food.IngredientEffectFoodItem;
import net.satisfy.foundation.food.EffectFoodItem;
import net.satisfy.farm_and_charm.core.registry.ArmorMaterialRegistry;

import java.util.function.Consumer;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class ObjectRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Candlelight.MOD_ID, Registries.ITEM);
    public static final Registrar<Item> ITEM_REGISTRAR = ITEMS.getRegistrar();
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Candlelight.MOD_ID, Registries.BLOCK);
    public static final Registrar<Block> BLOCK_REGISTRAR = BLOCKS.getRegistrar();

    public static final RegistrySupplier<Item> TOMATO_SOUP = registerItem("tomato_soup", () -> new IngredientEffectFoodItem(getSettings().food(CandlelightFoods.TOMATO_SOUP), 1));
    public static final RegistrySupplier<Item> MUSHROOM_SOUP = registerItem("mushroom_soup", () -> new IngredientEffectFoodItem(getSettings().food(CandlelightFoods.MUSHROOM_SOUP), 1));
    public static final RegistrySupplier<Item> PASTA_WITH_MOZZARELLA = registerItem("pasta_with_mozzarella", () -> new IngredientEffectFoodItem(getSettings().food(CandlelightFoods.PASTA), 2));
    public static final RegistrySupplier<Item> BOLOGNESE = registerItem("bolognese", () -> new IngredientEffectFoodItem(getSettings().food(CandlelightFoods.BOLOGNESE), 1));
    public static final RegistrySupplier<Item> BEEF_WITH_MUSHROOM_IN_WINE_AND_POTATOES = registerItem("beef_with_mushroom_in_wine_and_potatoes", () -> new IngredientEffectFoodItem(getSettings().food(CandlelightFoods.BEEF_WITH_MUSHROOM_IN_WINE_AND_POTATOES), 2));
    public static final RegistrySupplier<Item> PASTA_WITH_BOLOGNESE = registerItem("pasta_with_bolognese", () -> new IngredientEffectFoodItem(getSettings().food(CandlelightFoods.PASTA_WITH_BOLOGNESE), 2));
    public static final RegistrySupplier<Item> ROASTBEEF_WITH_GLAZED_CARROTS = registerItem("roastbeef_with_glazed_carrots", () -> new IngredientEffectFoodItem(getSettings().food(CandlelightFoods.ROASTBEEF_WITH_GLAZED_CARROTS), 2));
    public static final RegistrySupplier<Item> ROASTED_LAMB_WITH_LETTUCE = registerItem("roasted_lamb_with_lettuce", () -> new IngredientEffectFoodItem(getSettings().food(CandlelightFoods.ROASTED_LAMB_WITH_LETTUCE), 2));
    public static final RegistrySupplier<Item> FILLET_STEAK = registerItem("fillet_steak", () -> new IngredientEffectFoodItem(getSettings().food(CandlelightFoods.FILLET_STEAK), 2));
    public static final RegistrySupplier<Item> TROPICAL_FISH_SUPREME = registerItem("tropical_fish_supreme", () -> new IngredientEffectFoodItem(getSettings().food(Foods.GOLDEN_CARROT), 1));
    public static final RegistrySupplier<Item> CHICKEN_ALFREDO = registerItem("chicken_alfredo", () -> new IngredientEffectFoodItem(getSettings().food(CandlelightFoods.CHICKEN_ALFREDO), 2));
    public static final RegistrySupplier<Item> SALMON_ON_WHITE_WINE = registerItem("salmon_on_white_wine", () -> new IngredientEffectFoodItem(getSettings().food(CandlelightFoods.SALMON_ON_WHITE_WINE), 2));
    public static final RegistrySupplier<Item> CHICKEN_WITH_VEGETABLES = registerItem("chicken_with_vegetables", () -> new IngredientEffectFoodItem(getSettings().food(Foods.GOLDEN_CARROT), 2));
    public static final RegistrySupplier<Block> FRESH_GARDEN_SALAD_BLOCK = registerWithoutItem("fresh_garden_salad_block", () -> new EffectFoodTrayBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), 4, new FoodProperties.Builder().nutrition(6).saturationModifier(0.9F).build()));
    public static final RegistrySupplier<Block> LASAGNE_BLOCK = registerWithoutItem("lasagne_block", () -> new CEffectFoodBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), 3, CandlelightFoods.LASAGNE));
    public static final RegistrySupplier<Block> BEEF_WELLINGTON_BLOCK = registerWithoutItem("beef_wellington_block", () -> new CEffectFoodBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), 2, CandlelightFoods.BEEF_WELLINGTON));
    public static final RegistrySupplier<Block> PORK_RIBS_BLOCK = registerWithoutItem("pork_ribs_block", () -> new CEffectFoodBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), 2, CandlelightFoods.PORK_RIBS));
    public static final RegistrySupplier<Block> TOMATO_MOZZARELLA_BLOCK = registerWithoutItem("tomato_mozzarella_block", () -> new EffectFoodTrayBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), 4, new FoodProperties.Builder().nutrition(6).saturationModifier(0.7F).build()));
    public static final RegistrySupplier<Item> PORK_RIBS = registerItem("pork_ribs", () -> new PlaceableIngredientEffectFoodItem(PORK_RIBS_BLOCK.get(), getSettings().food(CandlelightFoods.PORK_RIBS), 2));
    public static final RegistrySupplier<Item> LASAGNE = registerItem("lasagne", () -> new PlaceableIngredientEffectFoodItem(LASAGNE_BLOCK.get(), getSettings().food(CandlelightFoods.LASAGNE), 3));
    public static final RegistrySupplier<Item> BEEF_WELLINGTON = registerItem("beef_wellington", () -> new PlaceableIngredientEffectFoodItem(BEEF_WELLINGTON_BLOCK.get(), getSettings().food(CandlelightFoods.BEEF_WELLINGTON), 2));
    public static final RegistrySupplier<Item> CLOCHE = registerItem("cloche", () -> new Item(getSettings()));
    public static final RegistrySupplier<Item> NAPKIN = registerItem("napkin", () -> new NapkinItem(getSettings()));
    public static final RegistrySupplier<Item> MOZZARELLA = registerItem("mozzarella", () -> new Item(getSettings().food(Foods.BREAD)));
    public static final RegistrySupplier<Item> POTROAST = registerItem("potroast", () -> new IngredientEffectFoodItem(getSettings().food(CandlelightFoods.POTROAST), 2));
    public static final RegistrySupplier<Item> BEETROOT_SALAD = registerItem("beetroot_salad", () -> new EffectFoodItem(getFoodItemSettings(5, 0.6f, MobEffectRegistry.REFRESHED.get(), 2400), 2400, true));
    public static final RegistrySupplier<Item> CHICKEN_TERIYAKI = registerItem("chicken_teriyaki", () -> new EffectFoodItem(getFoodItemSettings(8, 0.8f, MobEffectRegistry.WELL_SERVED.get(), 6000), 6000, true));
    public static final RegistrySupplier<Item> SALAD = registerItem("salad", () -> new EffectFoodItem(getFoodItemSettings(5, 0.7f, MobEffectRegistry.REFRESHED.get(), 3600), 3600, true));
    public static final RegistrySupplier<Item> BEEF_TARTARE = registerItem("beef_tartare", () -> new EffectFoodItem(getFoodItemSettings(8, 0.9f, MobEffectRegistry.WELL_SERVED.get(), 3000), 3000, true));
    public static final RegistrySupplier<Item> PASTA_WITH_LETTUCE = registerItem("pasta_with_lettuce", () -> new EffectFoodItem(getFoodItemSettings(8, 0.9f, MobEffectRegistry.REFRESHED.get(), 3600), 3600, true));
    public static final RegistrySupplier<Item> OMELET = registerItem("omelet", () -> new EffectFoodItem(getFoodItemSettings(8, 0.4f, MobEffectRegistry.WELL_SERVED.get(), 4800), 4800, true));
    public static final RegistrySupplier<Item> HARVEST_PLATE = registerItem("harvest_plate", () -> new EffectFoodItem(getFoodItemSettings(7, 0.8f, MobEffectRegistry.REFRESHED.get(), 4800), 4800, true));
    public static final RegistrySupplier<Item> CHOCOLATE_MOUSSE = registerItem("chocolate_mousse", () -> new EffectFoodItem(getFoodItemSettings(4, 0.3f, MobEffectRegistry.WELL_SERVED.get(), 2400), 2400, true));
    public static final RegistrySupplier<Item> GOLD_RING = registerItem("gold_ring", () -> new RingItem(getSettings().rarity(Rarity.EPIC).stacksTo(1)));
    public static final RegistrySupplier<Item> COOKING_HAT = registerItem("cooking_hat", () -> new CandlelightHatItem(ArmorMaterialRegistry.withTextureNoOverlay(ArmorMaterialRegistry.CLOTH, Candlelight.identifier("textures/models/armor/cooking_hat.png")), ArmorItem.Type.HELMET, getSettings().rarity(Rarity.UNCOMMON), Candlelight.identifier("textures/models/armor/cooking_hat.png")));
    public static final RegistrySupplier<Item> CHEFS_JACKET = registerItem("chefs_jacket", () -> new CandlelightChestItem(ArmorMaterialRegistry.withTextureNoOverlay(ArmorMaterialRegistry.CLOTH, Candlelight.identifier("textures/models/armor/cook.png")), ArmorItem.Type.CHESTPLATE, getSettings().rarity(Rarity.UNCOMMON), Candlelight.identifier("textures/models/armor/cook.png")));
    public static final RegistrySupplier<Item> CHEFS_PANTS = registerItem("chefs_pants", () -> new CandlelightLegsItem(ArmorMaterialRegistry.withTextureNoOverlay(ArmorMaterialRegistry.CLOTH, Candlelight.identifier("textures/models/armor/cook.png")), ArmorItem.Type.LEGGINGS, getSettings().rarity(Rarity.UNCOMMON), Candlelight.identifier("textures/models/armor/cook.png")));
    public static final RegistrySupplier<Item> CHEFS_BOOTS = registerItem("chefs_boots", () -> new CandlelightBootsItem(ArmorMaterialRegistry.withTextureNoOverlay(ArmorMaterialRegistry.CLOTH, Candlelight.identifier("textures/models/armor/cook.png")), ArmorItem.Type.BOOTS, getSettings().rarity(Rarity.UNCOMMON), Candlelight.identifier("textures/models/armor/cook.png")));
    public static final RegistrySupplier<Item> FLOWER_CROWN = registerItem("flower_crown", () -> new CandlelightHatItem(ArmorMaterialRegistry.withTextureNoOverlay(ArmorMaterialRegistry.CLOTH, Candlelight.identifier("textures/models/armor/flower_crown.png")), ArmorItem.Type.HELMET, getSettings().rarity(Rarity.UNCOMMON), Candlelight.identifier("textures/models/armor/flower_crown.png")));
    public static final RegistrySupplier<Item> DRESS = registerItem("dress", () -> new DyeableCandlelightArmorItem(ArmorMaterialRegistry.withTextureNoOverlay(ArmorMaterialRegistry.CLOTH, Candlelight.identifier("textures/models/armor/dress.png")), ArmorItem.Type.CHESTPLATE, 16744576, getSettings().rarity(Rarity.UNCOMMON), Candlelight.identifier("textures/models/armor/dress.png")));
    public static final RegistrySupplier<Item> SHIRT = registerItem("shirt", () -> new CandlelightChestItem(ArmorMaterialRegistry.withTextureNoOverlay(ArmorMaterialRegistry.CLOTH, Candlelight.identifier("textures/models/armor/shirt.png")), ArmorItem.Type.CHESTPLATE, getSettings().rarity(Rarity.UNCOMMON), Candlelight.identifier("textures/models/armor/shirt.png")));
    public static final RegistrySupplier<Item> FORMAL_SHIRT = registerItem("formal_shirt", () -> new CandlelightChestItem(ArmorMaterialRegistry.withTextureNoOverlay(ArmorMaterialRegistry.CLOTH, Candlelight.identifier("textures/models/armor/formal_shirt.png")), ArmorItem.Type.CHESTPLATE, getSettings().rarity(Rarity.UNCOMMON), Candlelight.identifier("textures/models/armor/formal_shirt.png")));
    public static final RegistrySupplier<Item> TROUSERS_AND_VEST = registerItem("trousers_and_vest", () -> new DyeableCandlelightArmorItem(ArmorMaterialRegistry.withTextureNoOverlay(ArmorMaterialRegistry.CLOTH, Candlelight.identifier("textures/models/armor/suit.png")), ArmorItem.Type.LEGGINGS, 0x333399, getSettings().rarity(Rarity.UNCOMMON), Candlelight.identifier("textures/models/armor/suit.png")));
    public static final RegistrySupplier<Item> NECKTIE = registerItem("necktie", () -> new CandlelightHatItem(ArmorMaterialRegistry.withTextureNoOverlay(ArmorMaterialRegistry.CLOTH, Candlelight.identifier("textures/models/armor/tie.png")), ArmorItem.Type.HELMET, getSettings().rarity(Rarity.COMMON), Candlelight.identifier("textures/models/armor/tie.png")));
    public static final RegistrySupplier<Item> NOTE_PAPER_WRITEABLE = registerItem("note_paper_writeable", () -> new WriteablePaperItem(getSettings().stacksTo(1)));
    public static final RegistrySupplier<Item> NOTE_PAPER_WRITTEN = registerItem("note_paper_written", () -> new WrittenPaperItem(getSettingsWithoutTab()));
    public static final RegistrySupplier<Item> LETTER_OPEN = registerItem("letter_open", () -> new LetterItem(getSettings()));
    public static final RegistrySupplier<Item> LETTER_CLOSED = registerItem("letter_closed", () -> new ClosedLetterItem(getSettingsWithoutTab().stacksTo(1)));
    public static final RegistrySupplier<Item> LOVE_LETTER_OPEN = registerItem("love_letter_open", () -> new LetterItem(getSettings()));
    public static final RegistrySupplier<Item> LOVE_LETTER_CLOSED = registerItem("love_letter", () -> new ClosedLetterItem(getSettingsWithoutTab().stacksTo(1)));
    private static final BannerSettings BANNER_SETTINGS = new BannerSettings(
            () -> EntityTypeRegistry.CANDLELIGHT_BANNER_ENTITY.get(),
            () -> ObjectRegistry.CANDLELIGHT_WALL_BANNER.get(),
            Candlelight.identifier("textures/banner/candlelight_banner.png"),
            "tooltip.candlelight.banner",
            MobEffects.FIRE_RESISTANCE);
    public static final RegistrySupplier<Block> CANDLELIGHT_BANNER = registerWithItem("candlelight_banner", () -> new CompletionistBannerBlock(BlockBehaviour.Properties.of().strength(1F).instrument(NoteBlockInstrument.BASS).noCollission().sound(SoundType.WOOD), BANNER_SETTINGS));
    public static final RegistrySupplier<Block> CANDLELIGHT_WALL_BANNER = registerWithoutItem("candlelight_wall_banner", () -> new CompletionistWallBannerBlock(BlockBehaviour.Properties.of().strength(1F).instrument(NoteBlockInstrument.BASS).noCollission().sound(SoundType.WOOD), BANNER_SETTINGS));
    public static final RegistrySupplier<Block> FLOORBOARD = registerWithItem("floorboard", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> DRAWER = registerDrawer("drawer", Blocks.OAK_PLANKS);
    public static final RegistrySupplier<Block> CABINET = registerCabinet("cabinet", Blocks.OAK_PLANKS);
    public static final RegistrySupplier<Block> SIDEBOARD = registerWithItem("sideboard", () -> new SideBoardBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.5f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> CHAIR = registerWithItem("chair", () -> new ChairBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> SOFA = registerWithItem("sofa", () -> new SofaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> TABLE = registerWithItem("table", () -> new TableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));
    private static final VoxelShape LAMP_SHAPE = Shapes.or(
            Shapes.box(0.3125, 0, 0.3125, 0.6875, 0.375, 0.6875),
            Shapes.box(0.1875, 0.5, 0.1875, 0.8125, 1, 0.8125),
            Shapes.box(0.4375, 0.3125, 0.4375, 0.5625, 0.9375, 0.5625));
    private static final VoxelShape HANGING_LAMP_SHAPE = Shapes.or(
            Shapes.box(0.3125, 0.875, 0.3125, 0.6875, 1, 0.6875),
            Shapes.box(0.4375, 0.625, 0.4375, 0.5625, 0.875, 0.5625),
            Shapes.box(0.1875, 0.125, 0.1875, 0.8125, 0.625, 0.8125));
    public static final RegistrySupplier<Block> LAMP = registerWithItem("lamp", () -> new LampBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN).lightLevel(LampBlock.lightWhenOn(15)).sound(SoundType.WOOD), LAMP_SHAPE, HANGING_LAMP_SHAPE));
    public static final RegistrySupplier<Block> SIDE_TABLE = registerWithItem("side_table", () -> new SideTableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> DINNER_BELL = registerWithItem("dinner_bell", () -> new DinnerBellBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistrySupplier<Block> COOKING_POT = registerWithItem("cooking_pot", () -> new LargeCookingPotBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistrySupplier<Block> COOKING_PAN = registerWithoutItem("cooking_pan", () -> new CookingPanBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));
    public static final RegistrySupplier<Item> COOKING_PAN_ITEM = registerItem("cooking_pan", () -> new CookingPanItem(COOKING_PAN.get(), getSettings().attributes(CookingPanItem.createAttributes())));
    public static final RegistrySupplier<Block> TABLE_SET = registerWithoutItem("table_set", () -> new TableSetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.FLOWER_POT)));
    public static final RegistrySupplier<Item> PLATE = registerItem("plate", () -> new TableSetBlockItem(TABLE_SET.get(), getSettings(), TableSetBlock.PlateType.PLATE));
    public static final RegistrySupplier<Item> BOWL = registerItem("bowl", () -> new TableSetBlockItem(TABLE_SET.get(), getSettings(), TableSetBlock.PlateType.BOWL));
    public static final RegistrySupplier<Block> GLASS_BLOCK = registerWithoutItem("glass", () -> new StackableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).noTerrainParticles(), 4));
    public static final RegistrySupplier<Item> GLASS = registerItem("glass", () -> new BlockItem(GLASS_BLOCK.get(), getSettings()));
    public static final RegistrySupplier<Block> WINE_GLASS_BLOCK = registerWithoutItem("wine_glass", () -> new StackableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).noTerrainParticles(), 4));
    public static final RegistrySupplier<Item> WINE_GLASS = registerItem("wine_glass", () -> new BlockItem(WINE_GLASS_BLOCK.get(), getSettings()));
    public static final RegistrySupplier<Item> FRESH_GARDEN_SALAD = registerItem("fresh_garden_salad", () -> new PlaceableEffectFoodItem(FRESH_GARDEN_SALAD_BLOCK.get(), getFoodItemSettings(6, 0.9f, MobEffectRegistry.WELL_SERVED.get(), 3600)));
    public static final RegistrySupplier<Item> TOMATO_MOZZARELLA_SALAD = registerItem("tomato_mozzarella_salad", () -> new PlaceableEffectFoodItem(TOMATO_MOZZARELLA_BLOCK.get(), getFoodItemSettings(5, 0.7f, MobEffectRegistry.WELL_SERVED.get(), 4800)));
    public static final RegistrySupplier<Block> TABLE_SIGN = registerWithoutItem("table_sign", () -> new TableSignBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.FLOWER_POT)));
    public static final RegistrySupplier<Item> TABLE_SIGN_ITEM = registerItem("table_sign", () -> new TableSignItem(TABLE_SIGN.get(), getSettings()));
    public static final RegistrySupplier<Block> PAINTING = registerWithItem("painting", () -> new SmallPaintingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.FLOWER_POT).noCollission()));
    public static final RegistrySupplier<Block> HEART = registerWithItem("heart", () -> new WallDecorationBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.FLOWER_POT).noCollission(), EntityTypeRegistry.WALL_DECORATION, Candlelight.identifier("textures/item/heart.png"), 12));
    public static final RegistrySupplier<Block> ROSE = registerWithItem("rose", () -> new BonemealableFlowerBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DANDELION)));
    public static final RegistrySupplier<Block> POTTED_ROSE = registerWithoutItem("potted_rose", () -> new FlowerPotBlock(ROSE.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_POPPY)));
    public static final RegistrySupplier<Block> JEWELRY_BOX = registerWithItem("jewelry_box", () -> new JewelryBoxBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.FLOWER_POT)));
    public static final RegistrySupplier<Block> CHOCOLATE_BOX = registerWithItem("chocolate_box", () -> new EatableBoxBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)));
    public static final RegistrySupplier<Block> TYPEWRITER_IRON = registerWithItem("typewriter_iron", () -> new TypewriterBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> TYPEWRITER_GOLD = registerWithItem("typewriter_gold", () -> new TypewriterBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_BLOCK).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> NOTE_PAPER_BLOCK = registerWithoutItem("note_paper", () -> new StackableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DRIED_KELP_BLOCK), 8));
    public static final RegistrySupplier<Item> NOTE_PAPER = registerItem("note_paper", () -> new BlockItem(NOTE_PAPER_BLOCK.get(), getSettings()));
    public static final RegistrySupplier<Block> COBBLESTONE_STOVE = registerWithItem("cobblestone_stove", () -> new CStoveBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).lightLevel(s -> 12)));
    public static final RegistrySupplier<Block> COBBLESTONE_KITCHEN_SINK = registerWithItem("cobblestone_kitchen_sink", () -> new SinkBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).noOcclusion().pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> COBBLESTONE_COUNTER = registerWithItem("cobblestone_counter", () -> new LineConnectingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).noOcclusion()));
    public static final RegistrySupplier<Block> OAK_CABINET = registerCabinet("oak_cabinet", Blocks.OAK_PLANKS);
    public static final RegistrySupplier<Block> OAK_DRAWER = registerDrawer("oak_drawer", Blocks.OAK_PLANKS);
    public static final RegistrySupplier<Block> OAK_TABLE = registerWithItem("oak_table", () -> new TableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> OAK_CHAIR = registerWithItem("oak_chair", () -> new ChairBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> OAK_SHELF = registerWithItem("oak_shelf", () -> new ShelfBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> OAK_BIG_TABLE = registerWithItem("oak_big_table", () -> new LargeTableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2.0F, 2.0F).pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> SANDSTONE_STOVE = registerWithItem("sandstone_stove", () -> new CStoveBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).lightLevel(s -> 12)));
    public static final RegistrySupplier<Block> SANDSTONE_KITCHEN_SINK = registerWithItem("sandstone_kitchen_sink", () -> new SinkBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).noOcclusion().pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> SANDSTONE_COUNTER = registerWithItem("sandstone_counter", () -> new LineConnectingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).noOcclusion()));
    public static final RegistrySupplier<Block> BIRCH_CABINET = registerCabinet("birch_cabinet", Blocks.OAK_PLANKS);
    public static final RegistrySupplier<Block> BIRCH_DRAWER = registerDrawer("birch_drawer", Blocks.OAK_PLANKS);
    public static final RegistrySupplier<Block> BIRCH_TABLE = registerWithItem("birch_table", () -> new TableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> BIRCH_CHAIR = registerWithItem("birch_chair", () -> new ChairBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> BIRCH_SHELF = registerWithItem("birch_shelf", () -> new ShelfBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> BIRCH_BIG_TABLE = registerWithItem("birch_big_table", () -> new LargeTableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2.0F, 2.0F).pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> STONE_BRICKS_STOVE = registerWithItem("stone_bricks_stove", () -> new CStoveBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).lightLevel(s -> 12)));
    public static final RegistrySupplier<Block> STONE_BRICKS_KITCHEN_SINK = registerWithItem("stone_bricks_kitchen_sink", () -> new SinkBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).noOcclusion().pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> STONE_BRICKS_COUNTER = registerWithItem("stone_bricks_counter", () -> new LineConnectingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).noOcclusion()));
    public static final RegistrySupplier<Block> SPRUCE_CABINET = registerCabinet("spruce_cabinet", Blocks.OAK_PLANKS);
    public static final RegistrySupplier<Block> SPRUCE_DRAWER = registerDrawer("spruce_drawer", Blocks.OAK_PLANKS);
    public static final RegistrySupplier<Block> SPRUCE_TABLE = registerWithItem("spruce_table", () -> new TableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> SPRUCE_CHAIR = registerWithItem("spruce_chair", () -> new ChairBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> SPRUCE_SHELF = registerWithItem("spruce_shelf", () -> new ShelfBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> SPRUCE_BIG_TABLE = registerWithItem("spruce_big_table", () -> new LargeTableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2.0F, 2.0F).pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> DEEPSLATE_STOVE = registerWithItem("deepslate_stove", () -> new CStoveBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).lightLevel(s -> 12)));
    public static final RegistrySupplier<Block> DEEPSLATE_KITCHEN_SINK = registerWithItem("deepslate_kitchen_sink", () -> new SinkBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).noOcclusion().pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> DEEPSLATE_COUNTER = registerWithItem("deepslate_counter", () -> new FacingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).noOcclusion()));
    public static final RegistrySupplier<Block> DARK_OAK_CABINET = registerCabinet("dark_oak_cabinet", Blocks.OAK_PLANKS);
    public static final RegistrySupplier<Block> DARK_OAK_DRAWER = registerDrawer("dark_oak_drawer", Blocks.OAK_PLANKS);
    public static final RegistrySupplier<Block> DARK_OAK_TABLE = registerWithItem("dark_oak_table", () -> new TableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ACACIA_PLANKS)));
    public static final RegistrySupplier<Block> DARK_OAK_CHAIR = registerWithItem("dark_oak_chair", () -> new ChairBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> DARK_OAK_SHELF = registerWithItem("dark_oak_shelf", () -> new ShelfBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> DARK_OAK_BIG_TABLE = registerWithItem("dark_oak_big_table", () -> new LargeTableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2.0F, 2.0F).pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> GRANITE_STOVE = registerWithItem("granite_stove", () -> new CStoveBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).lightLevel(s -> 12)));
    public static final RegistrySupplier<Block> GRANITE_KITCHEN_SINK = registerWithItem("granite_kitchen_sink", () -> new SinkBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).noOcclusion().pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> GRANITE_COUNTER = registerWithItem("granite_counter", () -> new LineConnectingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).noOcclusion()));
    public static final RegistrySupplier<Block> ACACIA_CABINET = registerCabinet("acacia_cabinet", Blocks.OAK_PLANKS);
    public static final RegistrySupplier<Block> ACACIA_DRAWER = registerDrawer("acacia_drawer", Blocks.OAK_PLANKS);
    public static final RegistrySupplier<Block> ACACIA_TABLE = registerWithItem("acacia_table", () -> new TableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ACACIA_PLANKS)));
    public static final RegistrySupplier<Block> ACACIA_CHAIR = registerWithItem("acacia_chair", () -> new ChairBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> ACACIA_SHELF = registerWithItem("acacia_shelf", () -> new ShelfBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> ACACIA_BIG_TABLE = registerWithItem("acacia_big_table", () -> new LargeTableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2.0F, 2.0F).pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> END_STOVE = registerWithItem("end_stove", () -> new CStoveBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).lightLevel(s -> 12)));
    public static final RegistrySupplier<Block> END_KITCHEN_SINK = registerWithItem("end_kitchen_sink", () -> new SinkBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).noOcclusion().pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> END_COUNTER = registerWithItem("end_counter", () -> new LineConnectingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).noOcclusion()));
    public static final RegistrySupplier<Block> JUNGLE_CABINET = registerCabinet("jungle_cabinet", Blocks.OAK_PLANKS);
    public static final RegistrySupplier<Block> JUNGLE_DRAWER = registerDrawer("jungle_drawer", Blocks.OAK_PLANKS);
    public static final RegistrySupplier<Block> JUNGLE_TABLE = registerWithItem("jungle_table", () -> new TableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> JUNGLE_CHAIR = registerWithItem("jungle_chair", () -> new ChairBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> JUNGLE_SHELF = registerWithItem("jungle_shelf", () -> new ShelfBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> JUNGLE_BIG_TABLE = registerWithItem("jungle_big_table", () -> new LargeTableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2.0F, 2.0F).pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> MUD_STOVE = registerWithItem("mud_stove", () -> new CStoveBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).lightLevel(s -> 12)));
    public static final RegistrySupplier<Block> MUD_KITCHEN_SINK = registerWithItem("mud_kitchen_sink", () -> new SinkBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).noOcclusion().pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> MUD_COUNTER = registerWithItem("mud_counter", () -> new LineConnectingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).noOcclusion()));
    public static final RegistrySupplier<Block> MANGROVE_CABINET = registerCabinet("mangrove_cabinet", Blocks.OAK_PLANKS);
    public static final RegistrySupplier<Block> MANGROVE_DRAWER = registerDrawer("mangrove_drawer", Blocks.OAK_PLANKS);
    public static final RegistrySupplier<Block> MANGROVE_TABLE = registerWithItem("mangrove_table", () -> new TableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> MANGROVE_CHAIR = registerWithItem("mangrove_chair", () -> new ChairBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> MANGROVE_SHELF = registerWithItem("mangrove_shelf", () -> new ShelfBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> MANGROVE_BIG_TABLE = registerWithItem("mangrove_big_table", () -> new LargeTableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2.0F, 2.0F).pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> QUARTZ_STOVE = registerWithItem("quartz_stove", () -> new CStoveBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).lightLevel(s -> 12)));
    public static final RegistrySupplier<Block> QUARTZ_KITCHEN_SINK = registerWithItem("quartz_kitchen_sink", () -> new SinkBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).noOcclusion().pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> QUARTZ_COUNTER = registerWithItem("quartz_counter", () -> new FacingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).noOcclusion()));
    public static final RegistrySupplier<Block> WARPED_CABINET = registerCabinet("warped_cabinet", Blocks.OAK_PLANKS);
    public static final RegistrySupplier<Block> WARPED_DRAWER = registerDrawer("warped_drawer", Blocks.OAK_PLANKS);
    public static final RegistrySupplier<Block> WARPED_TABLE = registerWithItem("warped_table", () -> new TableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> WARPED_CHAIR = registerWithItem("warped_chair", () -> new ChairBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> WARPED_SHELF = registerWithItem("warped_shelf", () -> new ShelfBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> WARPED_BIG_TABLE = registerWithItem("warped_big_table", () -> new LargeTableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2.0F, 2.0F).pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> RED_NETHER_BRICKS_STOVE = registerWithItem("red_nether_bricks_stove", () -> new CStoveBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS).lightLevel(s -> 12)));
    public static final RegistrySupplier<Block> RED_NETHER_BRICKS_KITCHEN_SINK = registerWithItem("red_nether_bricks_kitchen_sink", () -> new SinkBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).noOcclusion().pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> RED_NETHER_BRICKS_COUNTER = registerWithItem("red_nether_bricks_counter", () -> new LineConnectingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).noOcclusion()));
    public static final RegistrySupplier<Block> CRIMSON_CABINET = registerCabinet("crimson_cabinet", Blocks.OAK_PLANKS);
    public static final RegistrySupplier<Block> CRIMSON_DRAWER = registerDrawer("crimson_drawer", Blocks.OAK_PLANKS);
    public static final RegistrySupplier<Block> CRIMSON_TABLE = registerWithItem("crimson_table", () -> new TableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> CRIMSON_CHAIR = registerWithItem("crimson_chair", () -> new ChairBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> CRIMSON_SHELF = registerWithItem("crimson_shelf", () -> new ShelfBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> CRIMSON_BIG_TABLE = registerWithItem("crimson_big_table", () -> new LargeTableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2.0F, 2.0F).pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> BASALT_STOVE = registerWithItem("basalt_stove", () -> new CStoveBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BASALT).lightLevel(s -> 12)));
    public static final RegistrySupplier<Block> BASALT_KITCHEN_SINK = registerWithItem("basalt_kitchen_sink", () -> new SinkBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BASALT).noOcclusion()));
    public static final RegistrySupplier<Block> BASALT_COUNTER = registerWithItem("basalt_counter", () -> new LineConnectingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BASALT).noOcclusion()));
    public static final RegistrySupplier<Block> CHERRY_CABINET = registerCabinet("cherry_cabinet", Blocks.CHERRY_PLANKS);
    public static final RegistrySupplier<Block> CHERRY_DRAWER = registerDrawer("cherry_drawer", Blocks.CHERRY_PLANKS);
    public static final RegistrySupplier<Block> CHERRY_TABLE = registerWithItem("cherry_table", () -> new TableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_PLANKS)));
    public static final RegistrySupplier<Block> CHERRY_CHAIR = registerWithItem("cherry_chair", () -> new ChairBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_PLANKS).strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> CHERRY_SHELF = registerWithItem("cherry_shelf", () -> new ShelfBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> CHERRY_BIG_TABLE = registerWithItem("cherry_big_table", () -> new LargeTableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_PLANKS).strength(2.0F, 2.0F).pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> BAMBOO_STOVE = registerWithItem("bamboo_stove", () -> new BambooStoveBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO_PLANKS).lightLevel(s -> 10)));
    public static final RegistrySupplier<Block> BAMBOO_KITCHEN_SINK = registerWithItem("bamboo_kitchen_sink", () -> new SinkBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO_PLANKS).noOcclusion()));
    public static final RegistrySupplier<Block> BAMBOO_COUNTER = registerWithItem("bamboo_counter", () -> new FacingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO_PLANKS).noOcclusion()));
    public static final RegistrySupplier<Block> BAMBOO_CABINET = registerCabinet("bamboo_cabinet", Blocks.BAMBOO_PLANKS);
    public static final RegistrySupplier<Block> BAMBOO_DRAWER = registerDrawer("bamboo_drawer", Blocks.BAMBOO_PLANKS);
    public static final RegistrySupplier<Block> BAMBOO_TABLE = registerWithItem("bamboo_table", () -> new TableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO_PLANKS)));
    public static final RegistrySupplier<Block> BAMBOO_CHAIR = registerWithItem("bamboo_chair", () -> new ChairBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO_PLANKS).strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> BAMBOO_SHELF = registerWithItem("bamboo_shelf", () -> new ShelfBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> BAMBOO_BIG_TABLE = registerWithItem("bamboo_big_table", () -> new LargeTableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO_PLANKS).strength(2.0F, 2.0F).pushReaction(PushReaction.IGNORE)));

    public static void init() {
        ITEMS.register();
        BLOCKS.register();
    }

    private static Item.Properties getSettings(Consumer<Item.Properties> consumer) {
        Item.Properties settings = new Item.Properties();
        consumer.accept(settings);
        return settings;
    }

    private static Item.Properties getSettingsWithoutTab(Consumer<Item.Properties> consumer) {
        Item.Properties settings = new Item.Properties();
        consumer.accept(settings);
        return settings;
    }

    private static Item.Properties getSettings() {
        return getSettings(settings -> {
        });
    }

    private static Item.Properties getSettingsWithoutTab() {
        return getSettingsWithoutTab(settings -> {
        });
    }

    private static Item.Properties getFoodItemSettings(int nutrition, float saturationModifier, MobEffect effect, int duration) {
        return getFoodItemSettings(nutrition, saturationModifier, effect, duration, true, false);
    }

    @SuppressWarnings("all")
    private static Item.Properties getFoodItemSettings(int nutrition, float saturationModifier, MobEffect effect, int duration, boolean alwaysEat, boolean fast) {
        return getSettings().food(createFood(nutrition, saturationModifier, effect, duration, alwaysEat, fast));
    }

    private static FoodProperties createFood(int nutrition, float saturationModifier, MobEffect effect, int duration, boolean alwaysEat, boolean fast) {
        FoodProperties.Builder food = new FoodProperties.Builder().nutrition(nutrition).saturationModifier(saturationModifier);
        if (alwaysEat) food.alwaysEdible();
        if (fast) food.fast();
        if (effect != null) food.effect(new MobEffectInstance(BuiltInRegistries.MOB_EFFECT.wrapAsHolder(effect), duration), 1.0f);
        return food.build();
    }

    private static RegistrySupplier<Block> registerCabinet(String name, Block planks) {
        return registerWithItem(name, () -> new CabinetBlock(BlockBehaviour.Properties.ofFullCopy(planks).strength(2.0F, 3.0F).sound(SoundType.WOOD), EntityTypeRegistry.CABINET_BLOCK_ENTITY, SoundEventRegistry.CABINET_OPEN.get(), SoundEventRegistry.CABINET_CLOSE.get()));
    }

    private static RegistrySupplier<Block> registerDrawer(String name, Block planks) {
        return registerWithItem(name, () -> new CabinetBlock(BlockBehaviour.Properties.ofFullCopy(planks).strength(2.0F, 3.0F).sound(SoundType.WOOD), EntityTypeRegistry.CABINET_BLOCK_ENTITY, SoundEventRegistry.DRAWER_OPEN.get(), SoundEventRegistry.DRAWER_CLOSE.get()));
    }

    public static <T extends Block> RegistrySupplier<T> registerWithItem(String name, Supplier<T> block) {
        return RegistryUtil.registerWithItem(BLOCKS, BLOCK_REGISTRAR, ITEMS, ITEM_REGISTRAR, Candlelight.identifier(name), block);
    }

    public static <T extends Block> RegistrySupplier<T> registerWithoutItem(String path, Supplier<T> block) {
        return RegistryUtil.registerWithoutItem(BLOCKS, BLOCK_REGISTRAR, Candlelight.identifier(path), block);
    }

    public static <T extends Item> RegistrySupplier<T> registerItem(String path, Supplier<T> itemSupplier) {
        return RegistryUtil.registerItem(ITEMS, ITEM_REGISTRAR, Candlelight.identifier(path), itemSupplier);
    }
}