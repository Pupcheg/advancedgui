package me.supcheg.advancedgui.platform.paper.network;

import net.minecraft.network.protocol.Packet;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class PacketHandlerRegistry implements PacketHandlerLookup {
    private final Map<Class<?>, PacketHandler<?>> handlers = new HashMap<>();

    <T extends Packet<?>> void registerPacketHandler(Class<T> type, PacketHandler<T> handler) {
        handlers.put(type, handler);
    }

    @Override
    public <T extends Packet<?>> Optional<PacketHandler<T>> findPacketHandler(Class<T> type) {
        return Optional.ofNullable((PacketHandler<T>) handlers.get(type));
    }
}
