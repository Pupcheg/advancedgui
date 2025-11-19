package me.supcheg.advancedgui.platform.paper.network.message;

import io.netty.buffer.ByteBufAllocator;
import lombok.RequiredArgsConstructor;
import me.supcheg.advancedgui.api.messaging.DebugSentPacket;
import me.supcheg.advancedgui.api.messaging.DebugViewGuiTemplate;
import me.supcheg.advancedgui.api.messaging.Message;
import net.kyori.adventure.key.Key;
import net.minecraft.network.FriendlyByteBuf;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import static me.supcheg.advancedgui.api.loader.base64.Base64GuiLoader.jsonDownstreamBase64GuiLoader;

@RequiredArgsConstructor
public class AdvancedguiPluginChannel implements Closeable {
    private final Map<Key, BiConsumer<? extends Message, FriendlyByteBuf>> registeredMessages = new HashMap<>();

    private final Plugin plugin;

    public void register() {
        register(DebugViewGuiTemplate.KEY, this::writeViewGuiTemplate);
        register(DebugSentPacket.KEY, this::writeSentPacket);
    }

    private <T extends Message> void register(Key key, BiConsumer<T, FriendlyByteBuf> writer) {
        Bukkit.getMessenger().registerOutgoingPluginChannel(plugin, key.asString());
        registeredMessages.put(key, writer);
    }

    public void sendMessage(Player player, Message message) {
        FriendlyByteBuf buf = new FriendlyByteBuf(ByteBufAllocator.DEFAULT.buffer());
        registeredMessages.get(message.key())
                .accept(uncheckedMessageCast(message), buf);

        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);

        player.sendPluginMessage(plugin, message.key().asString(), bytes);
    }

    private static <T extends Message> T uncheckedMessageCast(Message message) {
        return (T) message;
    }

    private void writeViewGuiTemplate(DebugViewGuiTemplate message, FriendlyByteBuf buffer) {
        buffer.writeUtf(jsonDownstreamBase64GuiLoader().writeString(message.template()));
    }

    private void writeSentPacket(DebugSentPacket packet, FriendlyByteBuf buffer) {
        buffer.writeUtf(packet.representation());
    }

    @Override
    public void close() throws IOException {
        for (Key key : registeredMessages.keySet()) {
            Bukkit.getMessenger().unregisterOutgoingPluginChannel(plugin, key.asString());
        }
    }
}
