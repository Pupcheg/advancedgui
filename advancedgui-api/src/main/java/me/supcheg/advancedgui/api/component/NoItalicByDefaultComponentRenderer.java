package me.supcheg.advancedgui.api.component;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.format.TextDecoration.State;
import net.kyori.adventure.text.renderer.ComponentRenderer;
import org.jetbrains.annotations.NotNull;

enum NoItalicByDefaultComponentRenderer implements ComponentRenderer<ComponentRenderContext> {
    INSTANCE;

    @NotNull
    @Override
    public Component render(@NotNull Component component, @NotNull ComponentRenderContext ctx) {
        return component.decorationIfAbsent(TextDecoration.ITALIC, State.FALSE);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
