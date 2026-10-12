package net.satisfy.candlelight.fabric.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "candlelight")
@Config.Gui.Background("minecraft:textures/block/dark_oak_planks.png")
public class CandlelightFabricConfig implements ConfigData {
    @ConfigEntry.Gui.CollapsibleObject
    public DinnerSettings dinner = new DinnerSettings();

    @ConfigEntry.Gui.CollapsibleObject
    public DinnerGuestsSettings dinnerGuests = new DinnerGuestsSettings();

    @ConfigEntry.Gui.CollapsibleObject
    public ClothingSettings clothing = new ClothingSettings();

    @ConfigEntry.Gui.CollapsibleObject
    public InfoTooltipSettings infoTooltips = new InfoTooltipSettings();

    @ConfigEntry.Gui.CollapsibleObject
    public EffectsSettings effects = new EffectsSettings();

    @ConfigEntry.Gui.CollapsibleObject
    public MiscSettings misc = new MiscSettings();

    @ConfigEntry.Gui.CollapsibleObject
    public FoodSettings food = new FoodSettings();

    public static class DinnerSettings {
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
        public boolean enableDinnerMenu = true;

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
        public boolean enableTableLights = true;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 1, max = 4)
        public int tableLightRadius = 1;

        @ConfigEntry.Gui.Tooltip
        public boolean enableTableDrinks = true;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 1, max = 12)
        public int tableSignMaxLines = 8;
    }

    public static class DinnerGuestsSettings {
        @ConfigEntry.Gui.Tooltip
        public boolean enableDinnerGuests = true;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
        public int dinnerGuestChance = 30;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 4, max = 64)
        public int dinnerGuestRange = 16;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 20, max = 6000)
        public int dinnerGuestInterval = 200;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 24000)
        public int dinnerStart = 9000;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 24000)
        public int dinnerEnd = 12000;

        @ConfigEntry.Gui.Tooltip
        public boolean nitwitsEat = true;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
        public int dinnerDiscountPercent = 10;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 1200, max = 240000)
        public int dinnerDiscountTicks = 24000;

        @ConfigEntry.Gui.Tooltip
        public boolean discountOnlyHost = false;
    }

    public static class ClothingSettings {
        @ConfigEntry.Gui.Tooltip
        public boolean enableChefSetBonus = true;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
        public int chefSetExtraPortionChance = 25;

        @ConfigEntry.Gui.Tooltip
        public boolean enableElegantSetBonus = true;

        @ConfigEntry.Gui.Tooltip
        public boolean showSetBonusTooltips = true;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
        public int zombieCookingHatChance = 3;
    }

    public static class InfoTooltipSettings {
        @ConfigEntry.Gui.Tooltip
        public boolean showTableSetInfo = true;

        @ConfigEntry.Gui.Tooltip
        public boolean showSideTableInfo = true;

        @ConfigEntry.Gui.Tooltip
        public boolean showTableSignInfo = true;
    }

    public static class EffectsSettings {
        @ConfigEntry.Gui.CollapsibleObject
        public WellServedEffectSettings wellServedEffect = new WellServedEffectSettings();

        @ConfigEntry.Gui.CollapsibleObject
        public RefreshedEffectSettings refreshedEffect = new RefreshedEffectSettings();

        @ConfigEntry.Gui.CollapsibleObject
        public GoldRingEffectSettings goldRingEffect = new GoldRingEffectSettings();

        @ConfigEntry.Gui.CollapsibleObject
        public CompletionistBannerEffectSettings completionistBannerEffect = new CompletionistBannerEffectSettings();

        public static class WellServedEffectSettings {
            @ConfigEntry.Gui.Tooltip
            @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
            public int wellServedFoodFloor = 8;
        }

        public static class RefreshedEffectSettings {
            @ConfigEntry.Gui.Tooltip
            @ConfigEntry.BoundedDiscrete(min = 1, max = 64)
            public int refreshedMaxCharges = 10;
        }

        public static class GoldRingEffectSettings {
            @ConfigEntry.Gui.Tooltip
            public boolean enableRingLuck = true;

            @ConfigEntry.Gui.Tooltip
            @ConfigEntry.BoundedDiscrete(min = 0, max = 4)
            public int ringLuckAmplifier = 1;
        }

        public static class CompletionistBannerEffectSettings {
            @ConfigEntry.Gui.Tooltip
            public boolean bannerGiveEffect = true;

            @ConfigEntry.Gui.Tooltip
            @ConfigEntry.BoundedDiscrete(min = 1, max = 32)
            public int bannerEffectRadius = 8;

            @ConfigEntry.Gui.Tooltip
            @ConfigEntry.BoundedDiscrete(min = 0, max = 4)
            public int bannerEffectAmplifier = 0;
        }
    }

    public static class MiscSettings {
        @ConfigEntry.Gui.Tooltip
        public boolean enableNapkinSound = true;

        @ConfigEntry.Gui.Tooltip
        public boolean enableDishSteam = true;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 200, max = 12000)
        public int dishSteamTicks = 2400;
    }

    public static class FoodSettings {
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public int tomatoSoupNutrition = 5;

        @ConfigEntry.Gui.Tooltip
        public double tomatoSoupSaturation = 0.6;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public int mushroomSoupNutrition = 6;

        @ConfigEntry.Gui.Tooltip
        public double mushroomSoupSaturation = 0.6;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public int pastaWithMozzarellaNutrition = 6;

        @ConfigEntry.Gui.Tooltip
        public double pastaWithMozzarellaSaturation = 0.6;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public int bologneseNutrition = 7;

        @ConfigEntry.Gui.Tooltip
        public double bologneseSaturation = 0.7;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public int beefWithMushroomInWineAndPotatoesNutrition = 8;

        @ConfigEntry.Gui.Tooltip
        public double beefWithMushroomInWineAndPotatoesSaturation = 0.8;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public int pastaWithBologneseNutrition = 12;

        @ConfigEntry.Gui.Tooltip
        public double pastaWithBologneseSaturation = 0.8;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public int roastbeefWithGlazedCarrotsNutrition = 6;

        @ConfigEntry.Gui.Tooltip
        public double roastbeefWithGlazedCarrotsSaturation = 0.6;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public int roastedLambWithLettuceNutrition = 7;

        @ConfigEntry.Gui.Tooltip
        public double roastedLambWithLettuceSaturation = 0.8;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public int filletSteakNutrition = 11;

        @ConfigEntry.Gui.Tooltip
        public double filletSteakSaturation = 0.8;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public int tropicalFishSupremeNutrition = 5;

        @ConfigEntry.Gui.Tooltip
        public double tropicalFishSupremeSaturation = 0.6;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public int chickenAlfredoNutrition = 6;

        @ConfigEntry.Gui.Tooltip
        public double chickenAlfredoSaturation = 0.6;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public int salmonOnWhiteWineNutrition = 6;

        @ConfigEntry.Gui.Tooltip
        public double salmonOnWhiteWineSaturation = 0.8;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public int chickenWithVegetablesNutrition = 6;

        @ConfigEntry.Gui.Tooltip
        public double chickenWithVegetablesSaturation = 0.7;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public int porkRibsNutrition = 5;

        @ConfigEntry.Gui.Tooltip
        public double porkRibsSaturation = 0.8;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public int lasagneNutrition = 5;

        @ConfigEntry.Gui.Tooltip
        public double lasagneSaturation = 0.8;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public int beefWellingtonNutrition = 9;

        @ConfigEntry.Gui.Tooltip
        public double beefWellingtonSaturation = 0.8;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public int mozzarellaNutrition = 3;

        @ConfigEntry.Gui.Tooltip
        public double mozzarellaSaturation = 0.4;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public int potroastNutrition = 12;

        @ConfigEntry.Gui.Tooltip
        public double potroastSaturation = 0.9;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public int beetrootSaladNutrition = 5;

        @ConfigEntry.Gui.Tooltip
        public double beetrootSaladSaturation = 0.6;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 72000)
        public int beetrootSaladEffectDuration = 2400;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public int chickenTeriyakiNutrition = 5;

        @ConfigEntry.Gui.Tooltip
        public double chickenTeriyakiSaturation = 0.6;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 72000)
        public int chickenTeriyakiEffectDuration = 6000;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public int saladNutrition = 5;

        @ConfigEntry.Gui.Tooltip
        public double saladSaturation = 0.6;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 72000)
        public int saladEffectDuration = 3600;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public int beefTartareNutrition = 7;

        @ConfigEntry.Gui.Tooltip
        public double beefTartareSaturation = 0.8;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 72000)
        public int beefTartareEffectDuration = 3000;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public int pastaWithLettuceNutrition = 9;

        @ConfigEntry.Gui.Tooltip
        public double pastaWithLettuceSaturation = 0.8;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 72000)
        public int pastaWithLettuceEffectDuration = 3600;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public int omeletNutrition = 5;

        @ConfigEntry.Gui.Tooltip
        public double omeletSaturation = 0.5;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 72000)
        public int omeletEffectDuration = 4800;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public int harvestPlateNutrition = 5;

        @ConfigEntry.Gui.Tooltip
        public double harvestPlateSaturation = 0.6;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 72000)
        public int harvestPlateEffectDuration = 4800;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public int chocolateMousseNutrition = 4;

        @ConfigEntry.Gui.Tooltip
        public double chocolateMousseSaturation = 0.3;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 72000)
        public int chocolateMousseEffectDuration = 2400;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public int freshGardenSaladNutrition = 3;

        @ConfigEntry.Gui.Tooltip
        public double freshGardenSaladSaturation = 0.6;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 72000)
        public int freshGardenSaladEffectDuration = 3600;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public int tomatoMozzarellaSaladNutrition = 3;

        @ConfigEntry.Gui.Tooltip
        public double tomatoMozzarellaSaladSaturation = 0.6;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 72000)
        public int tomatoMozzarellaSaladEffectDuration = 4800;
    }

    @Override
    public void validatePostLoad() {
        dinner.tableSetFoodBonus = Math.clamp(dinner.tableSetFoodBonus, 1.0, 3.0);
        dinner.elegantFoodBonus = Math.clamp(dinner.elegantFoodBonus, 1.0, 3.0);
        food.tomatoSoupSaturation = Math.clamp(food.tomatoSoupSaturation, 0.0, 2.0);
        food.mushroomSoupSaturation = Math.clamp(food.mushroomSoupSaturation, 0.0, 2.0);
        food.pastaWithMozzarellaSaturation = Math.clamp(food.pastaWithMozzarellaSaturation, 0.0, 2.0);
        food.bologneseSaturation = Math.clamp(food.bologneseSaturation, 0.0, 2.0);
        food.beefWithMushroomInWineAndPotatoesSaturation = Math.clamp(food.beefWithMushroomInWineAndPotatoesSaturation, 0.0, 2.0);
        food.pastaWithBologneseSaturation = Math.clamp(food.pastaWithBologneseSaturation, 0.0, 2.0);
        food.roastbeefWithGlazedCarrotsSaturation = Math.clamp(food.roastbeefWithGlazedCarrotsSaturation, 0.0, 2.0);
        food.roastedLambWithLettuceSaturation = Math.clamp(food.roastedLambWithLettuceSaturation, 0.0, 2.0);
        food.filletSteakSaturation = Math.clamp(food.filletSteakSaturation, 0.0, 2.0);
        food.tropicalFishSupremeSaturation = Math.clamp(food.tropicalFishSupremeSaturation, 0.0, 2.0);
        food.chickenAlfredoSaturation = Math.clamp(food.chickenAlfredoSaturation, 0.0, 2.0);
        food.salmonOnWhiteWineSaturation = Math.clamp(food.salmonOnWhiteWineSaturation, 0.0, 2.0);
        food.chickenWithVegetablesSaturation = Math.clamp(food.chickenWithVegetablesSaturation, 0.0, 2.0);
        food.porkRibsSaturation = Math.clamp(food.porkRibsSaturation, 0.0, 2.0);
        food.lasagneSaturation = Math.clamp(food.lasagneSaturation, 0.0, 2.0);
        food.beefWellingtonSaturation = Math.clamp(food.beefWellingtonSaturation, 0.0, 2.0);
        food.mozzarellaSaturation = Math.clamp(food.mozzarellaSaturation, 0.0, 2.0);
        food.potroastSaturation = Math.clamp(food.potroastSaturation, 0.0, 2.0);
        food.beetrootSaladSaturation = Math.clamp(food.beetrootSaladSaturation, 0.0, 2.0);
        food.chickenTeriyakiSaturation = Math.clamp(food.chickenTeriyakiSaturation, 0.0, 2.0);
        food.saladSaturation = Math.clamp(food.saladSaturation, 0.0, 2.0);
        food.beefTartareSaturation = Math.clamp(food.beefTartareSaturation, 0.0, 2.0);
        food.pastaWithLettuceSaturation = Math.clamp(food.pastaWithLettuceSaturation, 0.0, 2.0);
        food.omeletSaturation = Math.clamp(food.omeletSaturation, 0.0, 2.0);
        food.harvestPlateSaturation = Math.clamp(food.harvestPlateSaturation, 0.0, 2.0);
        food.chocolateMousseSaturation = Math.clamp(food.chocolateMousseSaturation, 0.0, 2.0);
        food.freshGardenSaladSaturation = Math.clamp(food.freshGardenSaladSaturation, 0.0, 2.0);
        food.tomatoMozzarellaSaladSaturation = Math.clamp(food.tomatoMozzarellaSaladSaturation, 0.0, 2.0);
    }
}
