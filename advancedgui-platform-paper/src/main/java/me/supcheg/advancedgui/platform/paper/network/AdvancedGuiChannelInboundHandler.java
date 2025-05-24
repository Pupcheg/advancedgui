package me.supcheg.advancedgui.platform.paper.network;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.supcheg.advancedgui.api.Advancedgui;
import me.supcheg.advancedgui.platform.paper.view.GuiViewLookup;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketType;
import net.minecraft.server.level.ServerPlayer;

import java.util.concurrent.Executor;

@Slf4j
@RequiredArgsConstructor
public class AdvancedGuiChannelInboundHandler extends SimpleChannelInboundHandler<Packet<?>> {
    public static final String NAME = Advancedgui.NAMESPACE + "_packet_handler";

    private final ServerPlayer targetPlayer;
    private final PacketHandlerLookup packetHandlerLookup;
    private final GuiViewLookup guiViewLookup;
    private final Executor executor;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet<?> msg) {
        handlePacket(ctx, msg);
    }

    private <T extends Packet<?>> void handlePacket(ChannelHandlerContext ctx, T packet) {
        packetHandlerLookup.findPacketHandler((PacketType<T>) packet.type())
                .ifPresentOrElse(
                        handler ->
                                guiViewLookup.lookupGuiView(targetPlayer)
                                        .ifPresent(view -> executor.execute(() -> handler.handlePacket(targetPlayer, view, packet))),
                        () -> ctx.fireChannelRead(packet)
                );
    }
}
