package net.satisfy.candlelight.platform.fabric;

import me.shedaniel.autoconfig.AutoConfig;
import net.satisfy.candlelight.fabric.config.CandlelightFabricConfig;

public class PlatformHelperImpl {
    private static CandlelightFabricConfig config() {
        return AutoConfig.getConfigHolder(CandlelightFabricConfig.class).getConfig();
    }

    public static double tableSetFoodBonus() {
        return config().dinner.tableSetFoodBonus;
    }

    public static double elegantFoodBonus() {
        return config().dinner.elegantFoodBonus;
    }

    public static int wellServedTicksPerCourse() {
        return config().dinner.wellServedTicksPerCourse;
    }

    public static int maxWellServedTicks() {
        return config().dinner.maxWellServedTicks;
    }

    public static boolean enableDinnerMenu() {
        return config().dinner.enableDinnerMenu;
    }

    public static int menuBonusTicks() {
        return config().dinner.menuBonusTicks;
    }

    public static int maxMenuWellServedTicks() {
        return config().dinner.maxMenuWellServedTicks;
    }

    public static int menuMaxTicksBetweenCourses() {
        return config().dinner.menuMaxTicksBetweenCourses;
    }

    public static boolean enableTableLights() {
        return config().dinner.enableTableLights;
    }

    public static int tableLightRadius() {
        return config().dinner.tableLightRadius;
    }

    public static boolean enableTableDrinks() {
        return config().dinner.enableTableDrinks;
    }

    public static int tableSignMaxLines() {
        return config().dinner.tableSignMaxLines;
    }

    public static boolean enableDinnerGuests() {
        return config().dinnerGuests.enableDinnerGuests;
    }

    public static double dinnerGuestChance() {
        return config().dinnerGuests.dinnerGuestChance / 100.0;
    }

    public static int dinnerGuestRange() {
        return config().dinnerGuests.dinnerGuestRange;
    }

    public static int dinnerGuestInterval() {
        return config().dinnerGuests.dinnerGuestInterval;
    }

    public static int dinnerStart() {
        return config().dinnerGuests.dinnerStart;
    }

    public static int dinnerEnd() {
        return config().dinnerGuests.dinnerEnd;
    }

    public static boolean nitwitsEat() {
        return config().dinnerGuests.nitwitsEat;
    }

    public static int dinnerDiscountPercent() {
        return config().dinnerGuests.dinnerDiscountPercent;
    }

    public static int dinnerDiscountTicks() {
        return config().dinnerGuests.dinnerDiscountTicks;
    }

    public static boolean discountOnlyHost() {
        return config().dinnerGuests.discountOnlyHost;
    }

    public static boolean enableChefSetBonus() {
        return config().clothing.enableChefSetBonus;
    }

    public static double chefSetExtraPortionChance() {
        return config().clothing.chefSetExtraPortionChance / 100.0;
    }

    public static boolean enableElegantSetBonus() {
        return config().clothing.enableElegantSetBonus;
    }

    public static boolean showSetBonusTooltips() {
        return config().clothing.showSetBonusTooltips;
    }

    public static double zombieCookingHatChance() {
        return config().clothing.zombieCookingHatChance / 100.0;
    }

    public static boolean showTableSetInfo() {
        return config().infoTooltips.showTableSetInfo;
    }

    public static boolean showSideTableInfo() {
        return config().infoTooltips.showSideTableInfo;
    }

    public static boolean showTableSignInfo() {
        return config().infoTooltips.showTableSignInfo;
    }

    public static int wellServedFoodFloor() {
        return config().effects.wellServedEffect.wellServedFoodFloor;
    }

    public static int refreshedMaxCharges() {
        return config().effects.refreshedEffect.refreshedMaxCharges;
    }

    public static boolean enableRingLuck() {
        return config().effects.goldRingEffect.enableRingLuck;
    }

    public static int ringLuckAmplifier() {
        return config().effects.goldRingEffect.ringLuckAmplifier;
    }

    public static boolean bannerGiveEffect() {
        return config().effects.completionistBannerEffect.bannerGiveEffect;
    }

    public static int bannerEffectRadius() {
        return config().effects.completionistBannerEffect.bannerEffectRadius;
    }

    public static int bannerEffectAmplifier() {
        return config().effects.completionistBannerEffect.bannerEffectAmplifier;
    }

    public static boolean enableNapkinSound() {
        return config().misc.enableNapkinSound;
    }

    public static boolean enableDishSteam() {
        return config().misc.enableDishSteam;
    }

    public static int dishSteamTicks() {
        return config().misc.dishSteamTicks;
    }

    public static int tomatoSoupNutrition() {
        return config().food.tomatoSoupNutrition;
    }

    public static double tomatoSoupSaturation() {
        return config().food.tomatoSoupSaturation;
    }

    public static int mushroomSoupNutrition() {
        return config().food.mushroomSoupNutrition;
    }

    public static double mushroomSoupSaturation() {
        return config().food.mushroomSoupSaturation;
    }

    public static int pastaWithMozzarellaNutrition() {
        return config().food.pastaWithMozzarellaNutrition;
    }

    public static double pastaWithMozzarellaSaturation() {
        return config().food.pastaWithMozzarellaSaturation;
    }

    public static int bologneseNutrition() {
        return config().food.bologneseNutrition;
    }

    public static double bologneseSaturation() {
        return config().food.bologneseSaturation;
    }

    public static int beefWithMushroomInWineAndPotatoesNutrition() {
        return config().food.beefWithMushroomInWineAndPotatoesNutrition;
    }

    public static double beefWithMushroomInWineAndPotatoesSaturation() {
        return config().food.beefWithMushroomInWineAndPotatoesSaturation;
    }

    public static int pastaWithBologneseNutrition() {
        return config().food.pastaWithBologneseNutrition;
    }

    public static double pastaWithBologneseSaturation() {
        return config().food.pastaWithBologneseSaturation;
    }

    public static int roastbeefWithGlazedCarrotsNutrition() {
        return config().food.roastbeefWithGlazedCarrotsNutrition;
    }

    public static double roastbeefWithGlazedCarrotsSaturation() {
        return config().food.roastbeefWithGlazedCarrotsSaturation;
    }

    public static int roastedLambWithLettuceNutrition() {
        return config().food.roastedLambWithLettuceNutrition;
    }

    public static double roastedLambWithLettuceSaturation() {
        return config().food.roastedLambWithLettuceSaturation;
    }

    public static int filletSteakNutrition() {
        return config().food.filletSteakNutrition;
    }

    public static double filletSteakSaturation() {
        return config().food.filletSteakSaturation;
    }

    public static int tropicalFishSupremeNutrition() {
        return config().food.tropicalFishSupremeNutrition;
    }

    public static double tropicalFishSupremeSaturation() {
        return config().food.tropicalFishSupremeSaturation;
    }

    public static int chickenAlfredoNutrition() {
        return config().food.chickenAlfredoNutrition;
    }

    public static double chickenAlfredoSaturation() {
        return config().food.chickenAlfredoSaturation;
    }

    public static int salmonOnWhiteWineNutrition() {
        return config().food.salmonOnWhiteWineNutrition;
    }

    public static double salmonOnWhiteWineSaturation() {
        return config().food.salmonOnWhiteWineSaturation;
    }

    public static int chickenWithVegetablesNutrition() {
        return config().food.chickenWithVegetablesNutrition;
    }

    public static double chickenWithVegetablesSaturation() {
        return config().food.chickenWithVegetablesSaturation;
    }

    public static int porkRibsNutrition() {
        return config().food.porkRibsNutrition;
    }

    public static double porkRibsSaturation() {
        return config().food.porkRibsSaturation;
    }

    public static int lasagneNutrition() {
        return config().food.lasagneNutrition;
    }

    public static double lasagneSaturation() {
        return config().food.lasagneSaturation;
    }

    public static int beefWellingtonNutrition() {
        return config().food.beefWellingtonNutrition;
    }

    public static double beefWellingtonSaturation() {
        return config().food.beefWellingtonSaturation;
    }

    public static int mozzarellaNutrition() {
        return config().food.mozzarellaNutrition;
    }

    public static double mozzarellaSaturation() {
        return config().food.mozzarellaSaturation;
    }

    public static int potroastNutrition() {
        return config().food.potroastNutrition;
    }

    public static double potroastSaturation() {
        return config().food.potroastSaturation;
    }

    public static int beetrootSaladNutrition() {
        return config().food.beetrootSaladNutrition;
    }

    public static double beetrootSaladSaturation() {
        return config().food.beetrootSaladSaturation;
    }

    public static int beetrootSaladEffectDuration() {
        return config().food.beetrootSaladEffectDuration;
    }

    public static int chickenTeriyakiNutrition() {
        return config().food.chickenTeriyakiNutrition;
    }

    public static double chickenTeriyakiSaturation() {
        return config().food.chickenTeriyakiSaturation;
    }

    public static int chickenTeriyakiEffectDuration() {
        return config().food.chickenTeriyakiEffectDuration;
    }

    public static int saladNutrition() {
        return config().food.saladNutrition;
    }

    public static double saladSaturation() {
        return config().food.saladSaturation;
    }

    public static int saladEffectDuration() {
        return config().food.saladEffectDuration;
    }

    public static int beefTartareNutrition() {
        return config().food.beefTartareNutrition;
    }

    public static double beefTartareSaturation() {
        return config().food.beefTartareSaturation;
    }

    public static int beefTartareEffectDuration() {
        return config().food.beefTartareEffectDuration;
    }

    public static int pastaWithLettuceNutrition() {
        return config().food.pastaWithLettuceNutrition;
    }

    public static double pastaWithLettuceSaturation() {
        return config().food.pastaWithLettuceSaturation;
    }

    public static int pastaWithLettuceEffectDuration() {
        return config().food.pastaWithLettuceEffectDuration;
    }

    public static int omeletNutrition() {
        return config().food.omeletNutrition;
    }

    public static double omeletSaturation() {
        return config().food.omeletSaturation;
    }

    public static int omeletEffectDuration() {
        return config().food.omeletEffectDuration;
    }

    public static int harvestPlateNutrition() {
        return config().food.harvestPlateNutrition;
    }

    public static double harvestPlateSaturation() {
        return config().food.harvestPlateSaturation;
    }

    public static int harvestPlateEffectDuration() {
        return config().food.harvestPlateEffectDuration;
    }

    public static int chocolateMousseNutrition() {
        return config().food.chocolateMousseNutrition;
    }

    public static double chocolateMousseSaturation() {
        return config().food.chocolateMousseSaturation;
    }

    public static int chocolateMousseEffectDuration() {
        return config().food.chocolateMousseEffectDuration;
    }

    public static int freshGardenSaladNutrition() {
        return config().food.freshGardenSaladNutrition;
    }

    public static double freshGardenSaladSaturation() {
        return config().food.freshGardenSaladSaturation;
    }

    public static int freshGardenSaladEffectDuration() {
        return config().food.freshGardenSaladEffectDuration;
    }

    public static int tomatoMozzarellaSaladNutrition() {
        return config().food.tomatoMozzarellaSaladNutrition;
    }

    public static double tomatoMozzarellaSaladSaturation() {
        return config().food.tomatoMozzarellaSaladSaturation;
    }

    public static int tomatoMozzarellaSaladEffectDuration() {
        return config().food.tomatoMozzarellaSaladEffectDuration;
    }
}
