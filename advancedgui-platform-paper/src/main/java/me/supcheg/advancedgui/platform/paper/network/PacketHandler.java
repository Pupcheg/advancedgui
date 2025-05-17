package me.supcheg.advancedgui.platform.paper.network;

import me.supcheg.advancedgui.platform.paper.view.GuiView;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerPlayer;

public interface PacketHandler<T extends Packet<?>> {
    void handlePacket(ServerPlayer subject, GuiView view, T packet);
}
