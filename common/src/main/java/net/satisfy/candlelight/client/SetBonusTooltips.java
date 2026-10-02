package net.satisfy.candlelight.client;

import dev.architectury.event.events.client.ClientTooltipEvent;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.satisfy.candlelight.core.registry.ObjectRegistry;
import net.satisfy.candlelight.core.util.Wearables;

import java.util.List;
import java.util.function.Predicate;

public final class SetBonusTooltips {
    private SetBonusTooltips() {
    }

    private record Piece(List<Item> items) {
        boolean worn(Player player) {
            return player != null && items.stream().anyMatch(item -> Wearables.isWearing(player, item));
        }

        Component name() {
            MutableComponent name = Component.empty();
            for (int i = 0; i < items.size(); i++) {
                if (i > 0) name.append(" / ");
                name.append(items.get(i).getDescription());
            }
            return name;
        }
    }

    private record ArmorSet(String key, List<Piece> pieces, String bonusKey, Predicate<Player> complete) {
        boolean contains(Item item) {
            return pieces.stream().anyMatch(piece -> piece.items().contains(item));
        }
    }

    private static List<ArmorSet> sets;

    public static void init() {
        ClientTooltipEvent.ITEM.register(SetBonusTooltips::append);
    }

    private static List<ArmorSet> sets() {
        if (sets == null) {
            sets = List.of(
                    new ArmorSet("chef", List.of(
                            piece(ObjectRegistry.COOKING_HAT.get()),
                            piece(ObjectRegistry.CHEFS_JACKET.get()),
                            piece(ObjectRegistry.CHEFS_PANTS.get()),
                            piece(ObjectRegistry.CHEFS_BOOTS.get())),
                            "chef", Wearables::hasChefSet),
                    new ArmorSet("suit", List.of(
                            piece(ObjectRegistry.SHIRT.get(), ObjectRegistry.FORMAL_SHIRT.get()),
                            piece(ObjectRegistry.TROUSERS_AND_VEST.get()),
                            piece(ObjectRegistry.NECKTIE.get())),
                            "elegant", Wearables::isElegantlyDressed),
                    new ArmorSet("dress", List.of(
                            piece(ObjectRegistry.DRESS.get()),
                            piece(ObjectRegistry.FLOWER_CROWN.get())),
                            "elegant", Wearables::isElegantlyDressed));
        }
        return sets;
    }

    private static Piece piece(Item... items) {
        return new Piece(List.of(items));
    }

    private static void append(ItemStack stack, List<Component> tooltip, Item.TooltipContext context, TooltipFlag flag) {
        for (ArmorSet set : sets()) {
            if (set.contains(stack.getItem())) {
                appendSet(set, tooltip);
                return;
            }
        }
    }

    private static void appendSet(ArmorSet set, List<Component> tooltip) {
        Player player = Minecraft.getInstance().player;
        long worn = set.pieces().stream().filter(piece -> piece.worn(player)).count();
        boolean complete = player != null && set.complete().test(player);

        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("tooltip.candlelight.set." + set.key())
                .append(" (" + worn + "/" + set.pieces().size() + ")")
                .withStyle(ChatFormatting.AQUA));
        for (Piece piece : set.pieces()) {
            tooltip.add(Component.literal("- [").append(piece.name()).append("]")
                    .withStyle(piece.worn(player) ? ChatFormatting.GREEN : ChatFormatting.GRAY));
        }
        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("tooltip.candlelight.set.bonus").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.candlelight.set." + set.bonusKey() + ".bonus")
                .withStyle(complete ? ChatFormatting.DARK_GREEN : ChatFormatting.GRAY));
    }
}
