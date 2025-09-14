package me.supcheg.advancedgui.platform.paper.network;

import net.minecraft.server.level.ServerPlayer;

import java.io.IOException;

@FunctionalInterface
public interface DelegatingNetworkInjection extends NetworkInjection {

    NetworkInjection delegate();

    @Override
    default void inject(ServerPlayer player) {
        delegate().inject(player);
    }

    @Override
    default void uninject(ServerPlayer player) {
        delegate().uninject(player);
    }

    @Override
    default void close() throws IOException {
        delegate().close();
    }
}
