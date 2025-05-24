package me.supcheg.advancedgui.platform.paper.network;

import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import java.io.Closeable;

public interface NetworkInjection extends Closeable {
    void inject(@NotNull ServerPlayer player);

    void uninject(@NotNull ServerPlayer player);
}
