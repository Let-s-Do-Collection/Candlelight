package net.satisfy.candlelight.core.mixin;

import java.util.UUID;
import net.satisfy.candlelight.core.config.CandlelightConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.trading.MerchantOffer;
import net.satisfy.candlelight.core.util.DinnerGuest;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Villager.class)
public abstract class VillagerMixin implements DinnerGuest {
    @Unique
    private static final String candlelight$DISCOUNT_KEY = "CandlelightDinnerDiscountUntil";
    @Unique
    private static final String candlelight$HOST_KEY = "CandlelightDinnerHost";

    @Unique
    private long candlelight$dinnerDiscountUntil;
    @Unique
    private UUID candlelight$dinnerHost;

    @Override
    public long candlelight$getDinnerDiscountUntil() {
        return candlelight$dinnerDiscountUntil;
    }

    @Override
    public void candlelight$setDinnerDiscountUntil(long gameTime) {
        candlelight$dinnerDiscountUntil = gameTime;
    }

    @Override
    public void candlelight$setDinnerHost(UUID host) {
        candlelight$dinnerHost = host;
    }

    @Inject(method = "updateSpecialPrices", at = @At("TAIL"))
    private void candlelight$applyDinnerDiscount(Player player, CallbackInfo ci) {
        Villager villager = (Villager) (Object) this;
        if (villager.level().getGameTime() >= candlelight$dinnerDiscountUntil) {
            return;
        }
        if (CandlelightConfig.discountOnlyHost && !player.getUUID().equals(candlelight$dinnerHost)) {
            return;
        }
        for (MerchantOffer offer : villager.getOffers()) {
            offer.addToSpecialPriceDiff(-Math.round(offer.getBaseCostA().getCount() * CandlelightConfig.dinnerDiscountPercent / 100.0F));
        }
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void candlelight$saveDinnerDiscount(CompoundTag tag, CallbackInfo ci) {
        if (candlelight$dinnerDiscountUntil > 0) {
            tag.putLong(candlelight$DISCOUNT_KEY, candlelight$dinnerDiscountUntil);
            if (candlelight$dinnerHost != null) {
                tag.putUUID(candlelight$HOST_KEY, candlelight$dinnerHost);
            }
        }
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void candlelight$loadDinnerDiscount(CompoundTag tag, CallbackInfo ci) {
        candlelight$dinnerDiscountUntil = tag.getLong(candlelight$DISCOUNT_KEY);
        candlelight$dinnerHost = tag.hasUUID(candlelight$HOST_KEY) ? tag.getUUID(candlelight$HOST_KEY) : null;
    }
}
