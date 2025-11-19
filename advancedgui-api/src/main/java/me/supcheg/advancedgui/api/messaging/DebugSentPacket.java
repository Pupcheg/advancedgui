package me.supcheg.advancedgui.api.messaging;

import me.supcheg.advancedgui.api.Advancedgui;
import net.kyori.adventure.key.Key;

public record DebugSentPacket(
        String representation
) implements Message {
    public static final Key KEY = Key.key(Advancedgui.NAMESPACE, "debug_sent_packet");

    @Override
    public Key key() {
        return KEY;
    }
}
