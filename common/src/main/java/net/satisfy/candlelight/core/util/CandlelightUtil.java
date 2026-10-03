package net.satisfy.candlelight.core.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.satisfy.candlelight.client.gui.NotePaperGui;
import net.satisfy.candlelight.client.gui.SignedPaperGui;
import net.satisfy.candlelight.client.gui.TypeWriterGui;
import net.satisfy.candlelight.core.block.entity.TypewriterEntity;

public class CandlelightUtil {
    public static void setNotePaperScreen(Player user, ItemStack stack, InteractionHand hand) {
        Minecraft.getInstance().setScreen(new NotePaperGui(user, stack, hand));
    }


    public static void setTypeWriterScreen(Player user, TypewriterEntity typeWriterEntity) {
        if (user instanceof LocalPlayer)
            Minecraft.getInstance().setScreen(new TypeWriterGui(user, typeWriterEntity));
    }

    public static void setSignedPaperScreen(ItemStack stack) {
        Minecraft.getInstance().setScreen(new SignedPaperGui(new SignedPaperGui.WrittenPaperContents(stack)));
    }

}
