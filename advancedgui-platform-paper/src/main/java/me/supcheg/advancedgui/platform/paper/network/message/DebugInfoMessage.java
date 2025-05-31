package me.supcheg.advancedgui.platform.paper.network.message;

import me.supcheg.advancedgui.api.Advancedgui;
import me.supcheg.advancedgui.api.gui.template.GuiTemplate;
import net.kyori.adventure.key.Key;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;

import static me.supcheg.advancedgui.api.loader.base64.Base64GuiLoader.jsonDownstreamBase64GuiLoader;

public record DebugInfoMessage(
        @NotNull GuiTemplate template
) implements Message {
    public static final Key KEY = Key.key(Advancedgui.NAMESPACE, "debug");

    @NotNull
    @Override
    public Key key() {
        return KEY;
    }

    @Override
    public void write(@NotNull FriendlyByteBuf buf) {
        buf.writeUtf(jsonDownstreamBase64GuiLoader().writeString(template));
    }
}
