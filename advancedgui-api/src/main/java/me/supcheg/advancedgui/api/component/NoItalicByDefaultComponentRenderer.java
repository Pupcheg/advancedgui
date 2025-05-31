package me.supcheg.advancedgui.api.component;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.format.TextDecoration.State;
import net.kyori.adventure.text.renderer.ComponentRenderer;

enum NoItalicByDefaultComponentRenderer implements ComponentRenderer<ComponentRenderContext> {
    INSTANCE;

    @Override
    public Component render(Component component, ComponentRenderContext ctx) {
        return component.decorationIfAbsent(TextDecoration.ITALIC, State.FALSE);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
