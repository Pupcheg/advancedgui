package me.supcheg.advancedgui.platform.paper.network.message;

import io.netty.buffer.ByteBufAllocator;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.key.Key;
import net.minecraft.network.FriendlyByteBuf;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class AdvancedguiPluginChannel implements Closeable {
    private final List<Key> REGISTERED_MESSAGES = new ArrayList<>();

    private final Plugin plugin;

    public void register() {
        register(DebugInfoMessage.KEY);
    }

    private void register(@NotNull Key key) {
        Bukkit.getMessenger().registerOutgoingPluginChannel(plugin, key.asString());
        REGISTERED_MESSAGES.add(key);
    }

    public void sendMessage(@NotNull Player player, @NotNull Message message) {
        FriendlyByteBuf buf = new FriendlyByteBuf(ByteBufAllocator.DEFAULT.buffer());
        message.write(buf);

        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);

        player.sendPluginMessage(plugin, message.key().asString(), bytes);
    }

    @Override
    public void close() throws IOException {
        for (Key key : REGISTERED_MESSAGES) {
            Bukkit.getMessenger().unregisterOutgoingPluginChannel(plugin, key.asString());
        }
    }
}
