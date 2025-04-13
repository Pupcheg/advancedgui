package me.supcheg.advancedgui.platform.paper.resourcepack;

import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

public record BackgroundImageMeta(
        @NotNull Key font,
        @NotNull String data
) {
}
