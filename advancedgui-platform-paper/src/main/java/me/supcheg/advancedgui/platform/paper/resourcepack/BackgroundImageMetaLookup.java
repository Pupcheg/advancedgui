package me.supcheg.advancedgui.platform.paper.resourcepack;

import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

public interface BackgroundImageMetaLookup {
    @NotNull
    BackgroundImageMeta findBackgroundImageMeta(@NotNull Key image);
}
