package net.satisfy.candlelight.core.networking.packet;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.satisfy.candlelight.Candlelight;
import net.satisfy.candlelight.core.item.TableSignItem;
import net.satisfy.candlelight.core.registry.DataComponentRegistry;
import org.jetbrains.annotations.NotNull;

public record SetTableSignTextPacket(InteractionHand hand, String text) implements CustomPacketPayload {
    public static final Type<SetTableSignTextPacket> TYPE = new Type<>(Candlelight.identifier("set_table_sign_text"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SetTableSignTextPacket> STREAM_CODEC = StreamCodec.of(
            (buf, packet) -> {
                buf.writeEnum(packet.hand);
                buf.writeUtf(packet.text, 512);
            },
            buf -> new SetTableSignTextPacket(buf.readEnum(InteractionHand.class), buf.readUtf(512)));

    public static void handle(SetTableSignTextPacket packet, ServerPlayer player) {
        ItemStack stack = player.getItemInHand(packet.hand);
        if (!(stack.getItem() instanceof TableSignItem)) {
            return;
        }
        String text = TableSignItem.sanitize(packet.text);
        if (text.isEmpty()) {
            stack.remove(DataComponentRegistry.TABLE_SIGN_TEXT.get());
        } else {
            stack.set(DataComponentRegistry.TABLE_SIGN_TEXT.get(), text);
        }
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
