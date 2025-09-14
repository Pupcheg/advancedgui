package me.supcheg.advancedgui.platform.paper.network;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelPipeline;
import me.supcheg.advancedgui.api.button.Button;
import me.supcheg.advancedgui.api.button.interaction.ButtonInteraction;
import me.supcheg.advancedgui.platform.paper.button.PaperPlatformButtonInteractionContextAdapter;
import me.supcheg.advancedgui.platform.paper.gui.ButtonImpl;
import me.supcheg.advancedgui.platform.paper.view.GuiView;
import me.supcheg.advancedgui.platform.paper.view.GuiViewLookup;
import net.minecraft.network.HandlerNames;
import net.minecraft.network.protocol.game.GamePacketTypes;
import net.minecraft.network.protocol.game.ServerboundContainerClickPacket;
import net.minecraft.network.protocol.game.ServerboundContainerClosePacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ExecutorService;

public class NmsNetworkInjection implements NetworkInjection {
    private final PacketHandlerRegistry handlers;
    private final GuiViewLookup lookup;
    private final ExecutorService packetHandlerExecutor;

    public NmsNetworkInjection(GuiViewLookup lookup, ExecutorService packetHandlerExecutor) {
        this.handlers = new PacketHandlerRegistry();
        this.packetHandlerExecutor = packetHandlerExecutor;

        handlers.registerPacketHandler(GamePacketTypes.SERVERBOUND_CONTAINER_CLICK, this::handleContainerClick);
        handlers.registerPacketHandler(GamePacketTypes.SERVERBOUND_CONTAINER_CLOSE, this::handleContainerClose);

        this.lookup = lookup;
    }

    private void handleContainerClose(ServerPlayer subject, GuiView view, ServerboundContainerClosePacket packet) {
        view.handleClose();
    }

    private void handleContainerClick(ServerPlayer subject, GuiView view, ServerboundContainerClickPacket packet) {
        int index = packet.slotNum();

        if (index < 0) {
            return;
        }

        Optional<? extends Button> optionalButton = view.gui().layout().buttonAt(index);

        if (optionalButton.isEmpty()) {
            return;
        }

        ButtonImpl button = (ButtonImpl) optionalButton.get();

        view.sendButton(button);
        view.sendEmptyCursor();

        var ctx = new PaperPlatformButtonInteractionContextAdapter(
                view,
                button,
                packet.clickType(),
                packet.buttonNum()
        );

        for (ButtonInteraction interaction : button.interactions()) {
            interaction.action().handleButtonInteraction(ctx);
        }
    }

    @Override
    public void inject(ServerPlayer player) {
        ChannelPipeline pipeline = pipeline(player);

        if (pipeline.get(AdvancedGuiChannelInboundHandler.NAME) != null) {
            return;
        }

        pipeline.addBefore(
                HandlerNames.PACKET_HANDLER,
                AdvancedGuiChannelInboundHandler.NAME,
                new AdvancedGuiChannelInboundHandler(player, handlers, lookup, packetHandlerExecutor)
        );
    }

    @Override
    public void uninject(ServerPlayer player) {
        ChannelPipeline pipeline = pipeline(player);
        ChannelHandler channelHandler = pipeline.get(AdvancedGuiChannelInboundHandler.NAME);
        if (channelHandler != null) {
            pipeline.remove(channelHandler);
        }
    }

    private static ChannelPipeline pipeline(ServerPlayer serverPlayer) {
        return serverPlayer.connection.connection.channel.pipeline();
    }

    @Override
    public void close() throws IOException {
        MinecraftServer.getServer().getPlayerList().getPlayers().forEach(this::uninject);
    }
}