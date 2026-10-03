package net.satisfy.candlelight.core.registry;

import net.satisfy.candlelight.core.config.CandlelightConfig;
import net.satisfy.candlelight.core.util.Wearables;
import net.satisfy.foundation.armor.ArmorSet;

public final class ArmorSetRegistry {
    private ArmorSetRegistry() {
    }

    public static void init() {
        ArmorSet.builder("tooltip.candlelight.set.chef")
                .piece(ObjectRegistry.COOKING_HAT)
                .piece(ObjectRegistry.CHEFS_JACKET)
                .piece(ObjectRegistry.CHEFS_PANTS)
                .piece(ObjectRegistry.CHEFS_BOOTS)
                .bonus("tooltip.candlelight.set.chef.bonus")
                .bonusActive(Wearables::hasChefSet)
                .tooltipVisible(() -> CandlelightConfig.showSetBonusTooltips)
                .register();
        ArmorSet.builder("tooltip.candlelight.set.suit")
                .piece(ObjectRegistry.SHIRT, ObjectRegistry.FORMAL_SHIRT)
                .piece(ObjectRegistry.TROUSERS_AND_VEST)
                .piece(ObjectRegistry.NECKTIE)
                .bonus("tooltip.candlelight.set.elegant.bonus")
                .bonusActive(Wearables::isElegantlyDressed)
                .tooltipVisible(() -> CandlelightConfig.showSetBonusTooltips)
                .register();
        ArmorSet.builder("tooltip.candlelight.set.dress")
                .piece(ObjectRegistry.DRESS)
                .piece(ObjectRegistry.FLOWER_CROWN)
                .bonus("tooltip.candlelight.set.elegant.bonus")
                .bonusActive(Wearables::isElegantlyDressed)
                .tooltipVisible(() -> CandlelightConfig.showSetBonusTooltips)
                .register();
    }
}
