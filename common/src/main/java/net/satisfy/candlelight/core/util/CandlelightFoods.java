package net.satisfy.candlelight.core.util;

import net.minecraft.world.food.FoodProperties;
import net.satisfy.candlelight.core.config.CandlelightConfig;

public class CandlelightFoods {
    public static final FoodProperties TOMATO_SOUP = food(CandlelightConfig.tomatoSoupNutrition, CandlelightConfig.tomatoSoupSaturation);
    public static final FoodProperties MUSHROOM_SOUP = food(CandlelightConfig.mushroomSoupNutrition, CandlelightConfig.mushroomSoupSaturation);
    public static final FoodProperties PASTA_WITH_MOZZARELLA = food(CandlelightConfig.pastaWithMozzarellaNutrition, CandlelightConfig.pastaWithMozzarellaSaturation);
    public static final FoodProperties BOLOGNESE = food(CandlelightConfig.bologneseNutrition, CandlelightConfig.bologneseSaturation);
    public static final FoodProperties BEEF_WITH_MUSHROOM_IN_WINE_AND_POTATOES = food(CandlelightConfig.beefWithMushroomInWineAndPotatoesNutrition, CandlelightConfig.beefWithMushroomInWineAndPotatoesSaturation);
    public static final FoodProperties PASTA_WITH_BOLOGNESE = food(CandlelightConfig.pastaWithBologneseNutrition, CandlelightConfig.pastaWithBologneseSaturation);
    public static final FoodProperties ROASTBEEF_WITH_GLAZED_CARROTS = food(CandlelightConfig.roastbeefWithGlazedCarrotsNutrition, CandlelightConfig.roastbeefWithGlazedCarrotsSaturation);
    public static final FoodProperties ROASTED_LAMB_WITH_LETTUCE = food(CandlelightConfig.roastedLambWithLettuceNutrition, CandlelightConfig.roastedLambWithLettuceSaturation);
    public static final FoodProperties FILLET_STEAK = food(CandlelightConfig.filletSteakNutrition, CandlelightConfig.filletSteakSaturation);
    public static final FoodProperties TROPICAL_FISH_SUPREME = food(CandlelightConfig.tropicalFishSupremeNutrition, CandlelightConfig.tropicalFishSupremeSaturation);
    public static final FoodProperties CHICKEN_ALFREDO = food(CandlelightConfig.chickenAlfredoNutrition, CandlelightConfig.chickenAlfredoSaturation);
    public static final FoodProperties SALMON_ON_WHITE_WINE = food(CandlelightConfig.salmonOnWhiteWineNutrition, CandlelightConfig.salmonOnWhiteWineSaturation);
    public static final FoodProperties CHICKEN_WITH_VEGETABLES = food(CandlelightConfig.chickenWithVegetablesNutrition, CandlelightConfig.chickenWithVegetablesSaturation);
    public static final FoodProperties PORK_RIBS = food(CandlelightConfig.porkRibsNutrition, CandlelightConfig.porkRibsSaturation);
    public static final FoodProperties LASAGNE = food(CandlelightConfig.lasagneNutrition, CandlelightConfig.lasagneSaturation);
    public static final FoodProperties BEEF_WELLINGTON = food(CandlelightConfig.beefWellingtonNutrition, CandlelightConfig.beefWellingtonSaturation);
    public static final FoodProperties MOZZARELLA = food(CandlelightConfig.mozzarellaNutrition, CandlelightConfig.mozzarellaSaturation);
    public static final FoodProperties POTROAST = food(CandlelightConfig.potroastNutrition, CandlelightConfig.potroastSaturation);

    private static FoodProperties food(int nutrition, double saturation) {
        return new FoodProperties.Builder().nutrition(nutrition).saturationModifier((float) saturation).build();
    }
}
