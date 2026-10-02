package net.satisfy.candlelight.core.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.satisfy.candlelight.client.CandlelightClient;
import net.satisfy.candlelight.core.registry.DataComponentRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TableSignItem extends BlockItem {
    public static final int MAX_LINES = 8;
    public static final int MAX_LINE_LENGTH = 40;

    public TableSignItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (level.isClientSide()) {
            CandlelightClient.openTableSignScreen(hand, getText(stack));
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    public static String getText(ItemStack stack) {
        return stack.getOrDefault(DataComponentRegistry.TABLE_SIGN_TEXT.get(), "");
    }

    public static List<String> getLines(String text) {
        return text.isBlank() ? List.of() : List.of(text.split("\n"));
    }

    public static String sanitize(String text) {
        return String.join("\n", text.lines()
                .limit(MAX_LINES)
                .map(line -> line.length() > MAX_LINE_LENGTH ? line.substring(0, MAX_LINE_LENGTH) : line)
                .toList()).strip();
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        List<String> lines = getLines(getText(stack));
        if (lines.isEmpty()) {
            tooltip.add(Component.translatable("tooltip.candlelight.table_sign.write").withStyle(ChatFormatting.GRAY));
            return;
        }
        for (String line : lines) {
            tooltip.add(Component.literal(line).withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC));
        }
    }
}
