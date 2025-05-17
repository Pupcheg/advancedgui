package me.supcheg.advancedgui.platform.paper.network;

import net.minecraft.network.protocol.Packet;

import java.util.Optional;

public interface PacketHandlerLookup {
    <T extends Packet<?>> Optional<PacketHandler<T>> findPacketHandler(Class<T> type);
}
