package me.supcheg.advancedgui.api.component;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.format.TextDecoration.State;
import net.kyori.adventure.text.renderer.ComponentRenderer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

enum NoItalicComponentRenderer implements ComponentRenderer<ComponentRenderContext> {
    INSTANCE;

    @NotNull
    @Override
    public Component render(@NotNull Component component, @NotNull ComponentRenderContext ctx) {
        return removeItalic(component);
    }

    @NotNull
    private Component removeItalic(@NotNull Component component) {
        if (!hasStrictNoItalic(component)) {
            component = component.decoration(TextDecoration.ITALIC, State.FALSE);
        }

        if (!component.children().isEmpty()) {
            component = component.children(copyAndRemoveItalic(component.children()));
        }

        return component;
    }

    private boolean hasStrictNoItalic(@NotNull Component component) {
        return component.decorations().get(TextDecoration.ITALIC) == State.FALSE;
    }

    @NotNull
    private List<Component> copyAndRemoveItalic(@NotNull List<Component> components) {
        components = new ArrayList<>(components);
        components.replaceAll(this::removeItalic);
        return components;
    }
}
