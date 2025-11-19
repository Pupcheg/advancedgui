package me.supcheg.advancedgui.platform.paper.network;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketType;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class PacketHandlerRegistry implements PacketHandlerLookup {
    private final Map<PacketType<?>, PacketHandler<?>> handlers = new HashMap<>();

    <T extends Packet<?>> void registerPacketHandler(PacketType<T> type, PacketHandler<T> handler) {
        handlers.put(type, handler);
    }

    @Override
    public <T extends Packet<?>> Optional<PacketHandler<T>> findPacketHandler(PacketType<?> type) {
        return Optional.ofNullable(uncheckedCast(handlers.get(type)));
    }


    @SuppressWarnings("unchecked")
    @Nullable
    private static <T extends Packet<?>> PacketHandler<T> uncheckedCast(@Nullable PacketHandler<?> handler) {
        return (PacketHandler<T>) handler;
    }
}
