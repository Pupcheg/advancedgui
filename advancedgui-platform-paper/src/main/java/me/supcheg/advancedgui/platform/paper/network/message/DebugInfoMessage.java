package me.supcheg.advancedgui.platform.paper.network.message;

import me.supcheg.advancedgui.api.Advancedgui;
import me.supcheg.advancedgui.api.gui.template.GuiTemplate;
import net.kyori.adventure.key.Key;
import net.minecraft.network.FriendlyByteBuf;

import static me.supcheg.advancedgui.api.loader.base64.Base64GuiLoader.jsonDownstreamBase64GuiLoader;

public record DebugInfoMessage(
        GuiTemplate template
) implements Message {
    public static final Key KEY = Key.key(Advancedgui.NAMESPACE, "debug");

    @Override
    public Key key() {
        return KEY;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeUtf(jsonDownstreamBase64GuiLoader().writeString(template));
    }
}
