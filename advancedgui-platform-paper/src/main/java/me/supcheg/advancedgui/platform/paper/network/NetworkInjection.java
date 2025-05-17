package me.supcheg.advancedgui.platform.paper.network;

import net.minecraft.server.level.ServerPlayer;

import java.io.Closeable;

public interface NetworkInjection extends Closeable {
    void inject(ServerPlayer player);

    void uninject(ServerPlayer player);
}
