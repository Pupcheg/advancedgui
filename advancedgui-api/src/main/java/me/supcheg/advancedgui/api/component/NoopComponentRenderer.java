package me.supcheg.advancedgui.api.component;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.renderer.ComponentRenderer;
import org.jetbrains.annotations.NotNull;

public enum NoopComponentRenderer implements ComponentRenderer<ComponentRenderContext> {
    INSTANCE;

    @NotNull
    @Override
    public Component render(@NotNull Component component, @NotNull ComponentRenderContext ctx) {
        return component;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
