package me.supcheg.advancedgui.api.component;

import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;

public interface ComponentRenderContext {
    @NotNull
    Audience audience();
}
