package net.satisfy.candlelight.core.util;

import net.minecraft.world.food.FoodProperties;
import net.satisfy.candlelight.platform.PlatformHelper;

public class CandlelightFoods {
    public static final FoodProperties TOMATO_SOUP = food(PlatformHelper.tomatoSoupNutrition(), PlatformHelper.tomatoSoupSaturation());
    public static final FoodProperties MUSHROOM_SOUP = food(PlatformHelper.mushroomSoupNutrition(), PlatformHelper.mushroomSoupSaturation());
    public static final FoodProperties PASTA_WITH_MOZZARELLA = food(PlatformHelper.pastaWithMozzarellaNutrition(), PlatformHelper.pastaWithMozzarellaSaturation());
    public static final FoodProperties BOLOGNESE = food(PlatformHelper.bologneseNutrition(), PlatformHelper.bologneseSaturation());
    public static final FoodProperties BEEF_WITH_MUSHROOM_IN_WINE_AND_POTATOES = food(PlatformHelper.beefWithMushroomInWineAndPotatoesNutrition(), PlatformHelper.beefWithMushroomInWineAndPotatoesSaturation());
    public static final FoodProperties PASTA_WITH_BOLOGNESE = food(PlatformHelper.pastaWithBologneseNutrition(), PlatformHelper.pastaWithBologneseSaturation());
    public static final FoodProperties ROASTBEEF_WITH_GLAZED_CARROTS = food(PlatformHelper.roastbeefWithGlazedCarrotsNutrition(), PlatformHelper.roastbeefWithGlazedCarrotsSaturation());
    public static final FoodProperties ROASTED_LAMB_WITH_LETTUCE = food(PlatformHelper.roastedLambWithLettuceNutrition(), PlatformHelper.roastedLambWithLettuceSaturation());
    public static final FoodProperties FILLET_STEAK = food(PlatformHelper.filletSteakNutrition(), PlatformHelper.filletSteakSaturation());
    public static final FoodProperties TROPICAL_FISH_SUPREME = food(PlatformHelper.tropicalFishSupremeNutrition(), PlatformHelper.tropicalFishSupremeSaturation());
    public static final FoodProperties CHICKEN_ALFREDO = food(PlatformHelper.chickenAlfredoNutrition(), PlatformHelper.chickenAlfredoSaturation());
    public static final FoodProperties SALMON_ON_WHITE_WINE = food(PlatformHelper.salmonOnWhiteWineNutrition(), PlatformHelper.salmonOnWhiteWineSaturation());
    public static final FoodProperties CHICKEN_WITH_VEGETABLES = food(PlatformHelper.chickenWithVegetablesNutrition(), PlatformHelper.chickenWithVegetablesSaturation());
    public static final FoodProperties PORK_RIBS = food(PlatformHelper.porkRibsNutrition(), PlatformHelper.porkRibsSaturation());
    public static final FoodProperties LASAGNE = food(PlatformHelper.lasagneNutrition(), PlatformHelper.lasagneSaturation());
    public static final FoodProperties BEEF_WELLINGTON = food(PlatformHelper.beefWellingtonNutrition(), PlatformHelper.beefWellingtonSaturation());
    public static final FoodProperties MOZZARELLA = food(PlatformHelper.mozzarellaNutrition(), PlatformHelper.mozzarellaSaturation());
    public static final FoodProperties POTROAST = food(PlatformHelper.potroastNutrition(), PlatformHelper.potroastSaturation());

    private static FoodProperties food(int nutrition, double saturation) {
        return new FoodProperties.Builder().nutrition(nutrition).saturationModifier((float) saturation).build();
    }
}
