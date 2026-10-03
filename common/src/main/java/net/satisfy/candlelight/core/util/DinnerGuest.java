package net.satisfy.candlelight.core.util;

import java.util.UUID;
public interface DinnerGuest {
    long candlelight$getDinnerDiscountUntil();

    void candlelight$setDinnerDiscountUntil(long gameTime);

    void candlelight$setDinnerHost(UUID host);
}
