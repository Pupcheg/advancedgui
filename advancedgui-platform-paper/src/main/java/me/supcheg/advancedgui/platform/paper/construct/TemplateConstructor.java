package me.supcheg.advancedgui.platform.paper.construct;

import org.jetbrains.annotations.NotNull;

public interface TemplateConstructor<T, O> {
    @NotNull
    O construct(@NotNull T template);
}
