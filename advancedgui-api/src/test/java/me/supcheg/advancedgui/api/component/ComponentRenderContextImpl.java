package me.supcheg.advancedgui.api.component;

import net.kyori.adventure.audience.Audience;

record ComponentRenderContextImpl(
        Audience audience
) implements ComponentRenderContext {
    ComponentRenderContextImpl() {
        this(Audience.empty());
    }
}
