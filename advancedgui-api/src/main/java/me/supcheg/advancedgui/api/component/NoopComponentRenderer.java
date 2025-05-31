package me.supcheg.advancedgui.api.component;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.renderer.ComponentRenderer;

public enum NoopComponentRenderer implements ComponentRenderer<ComponentRenderContext> {
    INSTANCE;

    @Override
    public Component render(Component component, ComponentRenderContext ctx) {
        return component;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
