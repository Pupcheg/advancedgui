package me.supcheg.advancedgui.platform.paper.render;

import org.jetbrains.annotations.NotNull;

public interface Renderer<I, O> {
    @NotNull
    O render(@NotNull I input);
}
