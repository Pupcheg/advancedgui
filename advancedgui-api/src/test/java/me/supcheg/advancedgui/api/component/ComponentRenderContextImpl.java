package me.supcheg.advancedgui.api.component;

import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;

record ComponentRenderContextImpl(
        @NotNull Audience audience
) implements ComponentRenderContext {
    ComponentRenderContextImpl() {
        this(Audience.empty());
    }
}
