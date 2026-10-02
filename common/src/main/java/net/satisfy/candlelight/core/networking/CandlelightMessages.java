package net.satisfy.candlelight.core.networking;

import net.satisfy.candlelight.core.networking.packet.SetTableSignTextPacket;
import dev.architectury.networking.NetworkManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.satisfy.candlelight.Candlelight;
import net.satisfy.candlelight.core.networking.handler.SignNoteC2SPacketHandler;
import net.satisfy.candlelight.core.networking.handler.TypewriterDataC2SPacketHandler;
import net.satisfy.candlelight.core.networking.packet.SignNoteC2SPacket;
import net.satisfy.candlelight.core.networking.packet.SyncTypewriterDataC2SPacket;

public class CandlelightMessages {
    public static final ResourceLocation TYPEWRITER_SYNC = Candlelight.identifier("typewriter_sync");
    public static final ResourceLocation SIGN_NOTE = Candlelight.identifier("sign_note");

    public static void init() {
        NetworkManager.registerReceiver(NetworkManager.c2s(), SetTableSignTextPacket.TYPE, SetTableSignTextPacket.STREAM_CODEC, (packet, context) -> context.queue(() -> SetTableSignTextPacket.handle(packet, (ServerPlayer) context.getPlayer())));
        NetworkManager.registerReceiver(NetworkManager.c2s(), SyncTypewriterDataC2SPacket.TYPE, SyncTypewriterDataC2SPacket.STREAM_CODEC, new TypewriterDataC2SPacketHandler());
        NetworkManager.registerReceiver(NetworkManager.c2s(), SignNoteC2SPacket.TYPE, SignNoteC2SPacket.STREAM_CODEC, new SignNoteC2SPacketHandler());
    }
}