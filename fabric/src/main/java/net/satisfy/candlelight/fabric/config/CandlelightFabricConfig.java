package net.satisfy.candlelight.fabric.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import net.satisfy.candlelight.core.config.CandlelightConfig;

@Config(name = "candlelight")
@Config.Gui.Background("minecraft:textures/block/dark_oak_planks.png")
public class CandlelightFabricConfig implements ConfigData {
    @ConfigEntry.Gui.CollapsibleObject
    public Effects effects = new Effects();

    @ConfigEntry.Gui.CollapsibleObject
    public Misc misc = new Misc();

    @ConfigEntry.Gui.CollapsibleObject
    public Food food = new Food();

    public static class Effects {
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public int wellServedFoodFloor = 8;
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 1, max = 64)
        public int refreshedMaxCharges = 10;
        @ConfigEntry.Gui.Tooltip
        public boolean ringLuckEnabled = true;
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 4)
        public int ringLuckAmplifier = 1;
        @ConfigEntry.Gui.Tooltip
        public boolean bannerGiveEffect = true;
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 1, max = 32)
        public int bannerRadius = 8;
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 4)
        public int bannerAmplifier = 0;
    }

    public static class Misc {
        @ConfigEntry.Gui.Tooltip
        public double tableSetFoodBonus = 1.3;
        @ConfigEntry.Gui.Tooltip
        public double elegantFoodBonus = 1.5;
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 12000)
        public int wellServedTicksPerCourse = 1200;
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 72000)
        public int maxWellServedTicks = 12000;
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 72000)
        public int menuBonusTicks = 6000;
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 72000)
        public int maxMenuWellServedTicks = 18000;
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 200, max = 72000)
        public int menuMaxTicksBetweenCourses = 6000;
        @ConfigEntry.Gui.Tooltip
        public boolean dinnerGuests = true;
        @ConfigEntry.Gui.Tooltip
        public double dinnerGuestChance = 0.3;
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 4, max = 64)
        public int dinnerGuestRange = 16;
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
        public int dinnerDiscountPercent = 10;
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 1200, max = 240000)
        public int dinnerDiscountTicks = 24000;
        @ConfigEntry.Gui.Tooltip
        public double chefSetExtraPortionChance = 0.25;
        @ConfigEntry.Gui.Tooltip
        public boolean tableLightsEnabled = true;
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 1, max = 4)
        public int tableLightRadius = 1;
        @ConfigEntry.Gui.Tooltip
        public boolean dinnerMenuEnabled = true;
        @ConfigEntry.Gui.Tooltip
        public boolean tableDrinksEnabled = true;
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 24000)
        public int dinnerStart = 9000;
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 24000)
        public int dinnerEnd = 12000;
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 20, max = 6000)
        public int dinnerGuestInterval = 200;
        @ConfigEntry.Gui.Tooltip
        public boolean nitwitsEat = true;
        @ConfigEntry.Gui.Tooltip
        public boolean discountOnlyHost = false;
        @ConfigEntry.Gui.Tooltip
        public boolean chefSetBonusEnabled = true;
        @ConfigEntry.Gui.Tooltip
        public boolean elegantSetBonusEnabled = true;
        @ConfigEntry.Gui.Tooltip
        public double zombieCookingHatChance = 0.03;
        @ConfigEntry.Gui.Tooltip
        public boolean napkinSound = true;
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 1, max = 12)
        public int tableSignMaxLines = 8;
        @ConfigEntry.Gui.Tooltip
        public boolean showTableSetInfo = true;
        @ConfigEntry.Gui.Tooltip
        public boolean showSetBonusTooltips = true;
    }

    public static class Food {
        @ConfigEntry.BoundedDiscrete(min = 1, max = 20)
        public int tomatoSoupNutrition = 6;
        public double tomatoSoupSaturation = 0.6;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 20)
        public int mushroomSoupNutrition = 4;
        public double mushroomSoupSaturation = 0.5;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 20)
        public int pastaWithMozzarellaNutrition = 10;
        public double pastaWithMozzarellaSaturation = 0.7;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 20)
        public int bologneseNutrition = 10;
        public double bologneseSaturation = 0.7;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 20)
        public int beefWithMushroomInWineAndPotatoesNutrition = 10;
        public double beefWithMushroomInWineAndPotatoesSaturation = 0.7;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 20)
        public int pastaWithBologneseNutrition = 12;
        public double pastaWithBologneseSaturation = 0.8;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 20)
        public int roastbeefWithGlazedCarrotsNutrition = 8;
        public double roastbeefWithGlazedCarrotsSaturation = 0.8;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 20)
        public int roastedLambWithLettuceNutrition = 8;
        public double roastedLambWithLettuceSaturation = 0.8;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 20)
        public int filletSteakNutrition = 8;
        public double filletSteakSaturation = 0.8;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 20)
        public int tropicalFishSupremeNutrition = 6;
        public double tropicalFishSupremeSaturation = 1.2;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 20)
        public int chickenAlfredoNutrition = 10;
        public double chickenAlfredoSaturation = 0.8;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 20)
        public int salmonOnWhiteWineNutrition = 5;
        public double salmonOnWhiteWineSaturation = 0.8;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 20)
        public int chickenWithVegetablesNutrition = 6;
        public double chickenWithVegetablesSaturation = 1.2;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 20)
        public int porkRibsNutrition = 7;
        public double porkRibsSaturation = 0.8;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 20)
        public int lasagneNutrition = 10;
        public double lasagneSaturation = 0.7;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 20)
        public int beefWellingtonNutrition = 10;
        public double beefWellingtonSaturation = 0.7;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 20)
        public int mozzarellaNutrition = 5;
        public double mozzarellaSaturation = 0.6;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 20)
        public int potroastNutrition = 12;
        public double potroastSaturation = 0.9;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 20)
        public int beetrootSaladNutrition = 5;
        public double beetrootSaladSaturation = 0.6;
        @ConfigEntry.BoundedDiscrete(min = 0, max = 72000)
        public int beetrootSaladEffectDuration = 2400;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 20)
        public int chickenTeriyakiNutrition = 8;
        public double chickenTeriyakiSaturation = 0.8;
        @ConfigEntry.BoundedDiscrete(min = 0, max = 72000)
        public int chickenTeriyakiEffectDuration = 6000;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 20)
        public int saladNutrition = 5;
        public double saladSaturation = 0.7;
        @ConfigEntry.BoundedDiscrete(min = 0, max = 72000)
        public int saladEffectDuration = 3600;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 20)
        public int beefTartareNutrition = 8;
        public double beefTartareSaturation = 0.9;
        @ConfigEntry.BoundedDiscrete(min = 0, max = 72000)
        public int beefTartareEffectDuration = 3000;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 20)
        public int pastaWithLettuceNutrition = 8;
        public double pastaWithLettuceSaturation = 0.9;
        @ConfigEntry.BoundedDiscrete(min = 0, max = 72000)
        public int pastaWithLettuceEffectDuration = 3600;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 20)
        public int omeletNutrition = 8;
        public double omeletSaturation = 0.4;
        @ConfigEntry.BoundedDiscrete(min = 0, max = 72000)
        public int omeletEffectDuration = 4800;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 20)
        public int harvestPlateNutrition = 7;
        public double harvestPlateSaturation = 0.8;
        @ConfigEntry.BoundedDiscrete(min = 0, max = 72000)
        public int harvestPlateEffectDuration = 4800;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 20)
        public int chocolateMousseNutrition = 4;
        public double chocolateMousseSaturation = 0.3;
        @ConfigEntry.BoundedDiscrete(min = 0, max = 72000)
        public int chocolateMousseEffectDuration = 2400;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 20)
        public int freshGardenSaladNutrition = 6;
        public double freshGardenSaladSaturation = 0.9;
        @ConfigEntry.BoundedDiscrete(min = 0, max = 72000)
        public int freshGardenSaladEffectDuration = 3600;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 20)
        public int tomatoMozzarellaSaladNutrition = 5;
        public double tomatoMozzarellaSaladSaturation = 0.7;
        @ConfigEntry.BoundedDiscrete(min = 0, max = 72000)
        public int tomatoMozzarellaSaladEffectDuration = 4800;
    }

    public void apply() {
        CandlelightConfig.wellServedFoodFloor = effects.wellServedFoodFloor;
        CandlelightConfig.refreshedMaxCharges = effects.refreshedMaxCharges;
        CandlelightConfig.ringLuckEnabled = effects.ringLuckEnabled;
        CandlelightConfig.ringLuckAmplifier = effects.ringLuckAmplifier;
        CandlelightConfig.bannerGiveEffect = effects.bannerGiveEffect;
        CandlelightConfig.bannerRadius = effects.bannerRadius;
        CandlelightConfig.bannerAmplifier = effects.bannerAmplifier;
        CandlelightConfig.tableSetFoodBonus = misc.tableSetFoodBonus;
        CandlelightConfig.elegantFoodBonus = misc.elegantFoodBonus;
        CandlelightConfig.wellServedTicksPerCourse = misc.wellServedTicksPerCourse;
        CandlelightConfig.maxWellServedTicks = misc.maxWellServedTicks;
        CandlelightConfig.menuBonusTicks = misc.menuBonusTicks;
        CandlelightConfig.maxMenuWellServedTicks = misc.maxMenuWellServedTicks;
        CandlelightConfig.menuMaxTicksBetweenCourses = misc.menuMaxTicksBetweenCourses;
        CandlelightConfig.dinnerGuests = misc.dinnerGuests;
        CandlelightConfig.dinnerGuestChance = misc.dinnerGuestChance;
        CandlelightConfig.dinnerGuestRange = misc.dinnerGuestRange;
        CandlelightConfig.dinnerDiscountPercent = misc.dinnerDiscountPercent;
        CandlelightConfig.dinnerDiscountTicks = misc.dinnerDiscountTicks;
        CandlelightConfig.chefSetExtraPortionChance = misc.chefSetExtraPortionChance;
        CandlelightConfig.tableLightsEnabled = misc.tableLightsEnabled;
        CandlelightConfig.tableLightRadius = misc.tableLightRadius;
        CandlelightConfig.dinnerMenuEnabled = misc.dinnerMenuEnabled;
        CandlelightConfig.tableDrinksEnabled = misc.tableDrinksEnabled;
        CandlelightConfig.dinnerStart = misc.dinnerStart;
        CandlelightConfig.dinnerEnd = misc.dinnerEnd;
        CandlelightConfig.dinnerGuestInterval = misc.dinnerGuestInterval;
        CandlelightConfig.nitwitsEat = misc.nitwitsEat;
        CandlelightConfig.discountOnlyHost = misc.discountOnlyHost;
        CandlelightConfig.chefSetBonusEnabled = misc.chefSetBonusEnabled;
        CandlelightConfig.elegantSetBonusEnabled = misc.elegantSetBonusEnabled;
        CandlelightConfig.zombieCookingHatChance = misc.zombieCookingHatChance;
        CandlelightConfig.napkinSound = misc.napkinSound;
        CandlelightConfig.tableSignMaxLines = misc.tableSignMaxLines;
        CandlelightConfig.showTableSetInfo = misc.showTableSetInfo;
        CandlelightConfig.showSetBonusTooltips = misc.showSetBonusTooltips;
        CandlelightConfig.tomatoSoupNutrition = food.tomatoSoupNutrition;
        CandlelightConfig.tomatoSoupSaturation = food.tomatoSoupSaturation;
        CandlelightConfig.mushroomSoupNutrition = food.mushroomSoupNutrition;
        CandlelightConfig.mushroomSoupSaturation = food.mushroomSoupSaturation;
        CandlelightConfig.pastaWithMozzarellaNutrition = food.pastaWithMozzarellaNutrition;
        CandlelightConfig.pastaWithMozzarellaSaturation = food.pastaWithMozzarellaSaturation;
        CandlelightConfig.bologneseNutrition = food.bologneseNutrition;
        CandlelightConfig.bologneseSaturation = food.bologneseSaturation;
        CandlelightConfig.beefWithMushroomInWineAndPotatoesNutrition = food.beefWithMushroomInWineAndPotatoesNutrition;
        CandlelightConfig.beefWithMushroomInWineAndPotatoesSaturation = food.beefWithMushroomInWineAndPotatoesSaturation;
        CandlelightConfig.pastaWithBologneseNutrition = food.pastaWithBologneseNutrition;
        CandlelightConfig.pastaWithBologneseSaturation = food.pastaWithBologneseSaturation;
        CandlelightConfig.roastbeefWithGlazedCarrotsNutrition = food.roastbeefWithGlazedCarrotsNutrition;
        CandlelightConfig.roastbeefWithGlazedCarrotsSaturation = food.roastbeefWithGlazedCarrotsSaturation;
        CandlelightConfig.roastedLambWithLettuceNutrition = food.roastedLambWithLettuceNutrition;
        CandlelightConfig.roastedLambWithLettuceSaturation = food.roastedLambWithLettuceSaturation;
        CandlelightConfig.filletSteakNutrition = food.filletSteakNutrition;
        CandlelightConfig.filletSteakSaturation = food.filletSteakSaturation;
        CandlelightConfig.tropicalFishSupremeNutrition = food.tropicalFishSupremeNutrition;
        CandlelightConfig.tropicalFishSupremeSaturation = food.tropicalFishSupremeSaturation;
        CandlelightConfig.chickenAlfredoNutrition = food.chickenAlfredoNutrition;
        CandlelightConfig.chickenAlfredoSaturation = food.chickenAlfredoSaturation;
        CandlelightConfig.salmonOnWhiteWineNutrition = food.salmonOnWhiteWineNutrition;
        CandlelightConfig.salmonOnWhiteWineSaturation = food.salmonOnWhiteWineSaturation;
        CandlelightConfig.chickenWithVegetablesNutrition = food.chickenWithVegetablesNutrition;
        CandlelightConfig.chickenWithVegetablesSaturation = food.chickenWithVegetablesSaturation;
        CandlelightConfig.porkRibsNutrition = food.porkRibsNutrition;
        CandlelightConfig.porkRibsSaturation = food.porkRibsSaturation;
        CandlelightConfig.lasagneNutrition = food.lasagneNutrition;
        CandlelightConfig.lasagneSaturation = food.lasagneSaturation;
        CandlelightConfig.beefWellingtonNutrition = food.beefWellingtonNutrition;
        CandlelightConfig.beefWellingtonSaturation = food.beefWellingtonSaturation;
        CandlelightConfig.mozzarellaNutrition = food.mozzarellaNutrition;
        CandlelightConfig.mozzarellaSaturation = food.mozzarellaSaturation;
        CandlelightConfig.potroastNutrition = food.potroastNutrition;
        CandlelightConfig.potroastSaturation = food.potroastSaturation;
        CandlelightConfig.beetrootSaladNutrition = food.beetrootSaladNutrition;
        CandlelightConfig.beetrootSaladSaturation = food.beetrootSaladSaturation;
        CandlelightConfig.beetrootSaladEffectDuration = food.beetrootSaladEffectDuration;
        CandlelightConfig.chickenTeriyakiNutrition = food.chickenTeriyakiNutrition;
        CandlelightConfig.chickenTeriyakiSaturation = food.chickenTeriyakiSaturation;
        CandlelightConfig.chickenTeriyakiEffectDuration = food.chickenTeriyakiEffectDuration;
        CandlelightConfig.saladNutrition = food.saladNutrition;
        CandlelightConfig.saladSaturation = food.saladSaturation;
        CandlelightConfig.saladEffectDuration = food.saladEffectDuration;
        CandlelightConfig.beefTartareNutrition = food.beefTartareNutrition;
        CandlelightConfig.beefTartareSaturation = food.beefTartareSaturation;
        CandlelightConfig.beefTartareEffectDuration = food.beefTartareEffectDuration;
        CandlelightConfig.pastaWithLettuceNutrition = food.pastaWithLettuceNutrition;
        CandlelightConfig.pastaWithLettuceSaturation = food.pastaWithLettuceSaturation;
        CandlelightConfig.pastaWithLettuceEffectDuration = food.pastaWithLettuceEffectDuration;
        CandlelightConfig.omeletNutrition = food.omeletNutrition;
        CandlelightConfig.omeletSaturation = food.omeletSaturation;
        CandlelightConfig.omeletEffectDuration = food.omeletEffectDuration;
        CandlelightConfig.harvestPlateNutrition = food.harvestPlateNutrition;
        CandlelightConfig.harvestPlateSaturation = food.harvestPlateSaturation;
        CandlelightConfig.harvestPlateEffectDuration = food.harvestPlateEffectDuration;
        CandlelightConfig.chocolateMousseNutrition = food.chocolateMousseNutrition;
        CandlelightConfig.chocolateMousseSaturation = food.chocolateMousseSaturation;
        CandlelightConfig.chocolateMousseEffectDuration = food.chocolateMousseEffectDuration;
        CandlelightConfig.freshGardenSaladNutrition = food.freshGardenSaladNutrition;
        CandlelightConfig.freshGardenSaladSaturation = food.freshGardenSaladSaturation;
        CandlelightConfig.freshGardenSaladEffectDuration = food.freshGardenSaladEffectDuration;
        CandlelightConfig.tomatoMozzarellaSaladNutrition = food.tomatoMozzarellaSaladNutrition;
        CandlelightConfig.tomatoMozzarellaSaladSaturation = food.tomatoMozzarellaSaladSaturation;
        CandlelightConfig.tomatoMozzarellaSaladEffectDuration = food.tomatoMozzarellaSaladEffectDuration;
    }
}
