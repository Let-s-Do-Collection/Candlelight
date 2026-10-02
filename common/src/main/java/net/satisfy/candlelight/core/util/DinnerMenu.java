package net.satisfy.candlelight.core.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.satisfy.candlelight.Candlelight;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class DinnerMenu {
    public static final TagKey<Item> STARTERS = TagKey.create(Registries.ITEM, Candlelight.identifier("starters"));
    public static final TagKey<Item> MAIN_COURSES = TagKey.create(Registries.ITEM, Candlelight.identifier("main_courses"));
    public static final TagKey<Item> DESSERTS = TagKey.create(Registries.ITEM, Candlelight.identifier("desserts"));
    public static final int COURSES = 3;
    private static final long MAX_TICKS_BETWEEN_COURSES = 6000L;

    private static final Map<UUID, Progress> PROGRESS = new HashMap<>();

    private DinnerMenu() {
    }

    public enum Course {
        STARTER, MAIN, DESSERT
    }

    public record Result(Course course, int step, boolean complete) {
    }

    private record Progress(int step, long gameTime) {
    }

    public static Course courseOf(ItemStack stack) {
        if (stack.is(STARTERS)) return Course.STARTER;
        if (stack.is(DESSERTS)) return Course.DESSERT;
        if (stack.is(MAIN_COURSES)) return Course.MAIN;
        return null;
    }

    public static Result eat(Player player, ItemStack stack) {
        Course course = courseOf(stack);
        if (course == null) {
            return null;
        }
        long now = player.level().getGameTime();
        Progress progress = PROGRESS.get(player.getUUID());
        int previous = progress != null && now - progress.gameTime() <= MAX_TICKS_BETWEEN_COURSES ? progress.step() : 0;
        int step = course.ordinal() == previous ? previous + 1 : course == Course.STARTER ? 1 : 0;
        if (step >= COURSES) {
            PROGRESS.remove(player.getUUID());
            return new Result(course, COURSES, true);
        }
        if (step == 0) {
            PROGRESS.remove(player.getUUID());
        } else {
            PROGRESS.put(player.getUUID(), new Progress(step, now));
        }
        return new Result(course, step, false);
    }
}
