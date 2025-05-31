package me.supcheg.advancedgui.platform.paper.network;

import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public interface DelegatingNetworkInjection extends NetworkInjection {

    @NotNull
    NetworkInjection delegate();

    @Override
    default void inject(@NotNull ServerPlayer player) {
        delegate().inject(player);
    }

    @Override
    default void uninject(@NotNull ServerPlayer player) {
        delegate().uninject(player);
    }

    @Override
    default void close() throws IOException {
        delegate().close();
    }
}
