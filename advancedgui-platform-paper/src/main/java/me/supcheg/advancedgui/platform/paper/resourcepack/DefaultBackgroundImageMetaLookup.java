package me.supcheg.advancedgui.platform.paper.resourcepack;

import me.supcheg.advancedgui.api.Advancedgui;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

public class DefaultBackgroundImageMetaLookup implements BackgroundImageMetaLookup {
    private final Key font = Key.key(Advancedgui.NAMESPACE, "interface_font");

    @NotNull
    @Override
    public BackgroundImageMeta findBackgroundImageMeta(@NotNull Key image) {
        return new BackgroundImageMeta(font, "\u0008");
    }
}
