package me.supcheg.advancedgui.platform.paper.network;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelPipeline;
import lombok.extern.slf4j.Slf4j;
import me.supcheg.advancedgui.api.button.Button;
import me.supcheg.advancedgui.api.button.interaction.ButtonInteraction;
import me.supcheg.advancedgui.api.button.interaction.ButtonInteractionContext;
import me.supcheg.advancedgui.api.button.interaction.ButtonInteractionType;
import me.supcheg.advancedgui.api.gui.Gui;
import me.supcheg.advancedgui.platform.paper.view.GuiView;
import me.supcheg.advancedgui.platform.paper.view.GuiViewLookup;
import net.kyori.adventure.audience.Audience;
import net.minecraft.network.protocol.game.ServerboundContainerClickPacket;
import net.minecraft.network.protocol.game.ServerboundContainerClosePacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import java.io.Closeable;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class NmsNetworkInjection implements NetworkInjection {
    private static final String VANILLA_CHANNEL_NAME = "packet_handler";

    private final PacketHandlerRegistry handlers;
    private final GuiViewLookup lookup;
    private final ExecutorService packetHandlerExecutor;

    public NmsNetworkInjection(GuiViewLookup lookup) {
        this.handlers = new PacketHandlerRegistry();
        this.packetHandlerExecutor = Executors.newSingleThreadExecutor();

        handlers.registerPacketHandler(ServerboundContainerClickPacket.class, this::handleContainerClick);
        handlers.registerPacketHandler(ServerboundContainerClosePacket.class, this::handleContainerClose);

        this.lookup = lookup;
    }

    private void handleContainerClose(ServerPlayer subject, GuiView view, ServerboundContainerClosePacket packet) {
        view.handleRemove();
    }

    private void handleContainerClick(ServerPlayer subject, GuiView view, ServerboundContainerClickPacket packet) {
        int index = packet.getSlotNum();

        if (index == -999) {
            return;
        }

        Optional<? extends Button> optionalButton = view.gui().layout().buttonAt(index);

        if (optionalButton.isEmpty()) {
            return;
        }

        Button button = optionalButton.get();

        ButtonInteractionContext ctx = new ButtonInteractionContext() {
            @Override
            public @NotNull Gui gui() {
                return view.gui();
            }

            @Override
            public @NotNull Button button() {
                return button;
            }

            @Override
            public @NotNull ButtonInteractionType interactionType() {
                return ButtonInteractionType.LEFT_CLICK;
            }

            @Override
            public @NotNull Audience audience() {
                return subject.getBukkitEntity();
            }
        };

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
                VANILLA_CHANNEL_NAME,
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
        Closeable uninjectCloseable =
                () -> MinecraftServer.getServer().getPlayerList().getPlayers().forEach(this::uninject);

        try (
                uninjectCloseable;
                packetHandlerExecutor
        ) {
        }
    }
}