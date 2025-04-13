package me.supcheg.advancedgui.api.component;

import me.supcheg.advancedgui.api.builder.AbstractBuilder;
import me.supcheg.advancedgui.api.builder.Buildable;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.renderer.ComponentRenderer;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public interface ComponentRendererBuilder extends AbstractBuilder<ComponentRenderer<ComponentRenderContext>> {

    @NotNull
    static ComponentRendererBuilder componentRenderer() {
        return new ComponentRendererBuilderImpl();
    }

    @NotNull
    static ComponentRenderer<ComponentRenderContext> componentRenderer(Consumer<ComponentRendererBuilder> consumer) {
        return Buildable.configureAndBuild(componentRenderer(), consumer);
    }

    @NotNull
    default ComponentRendererBuilder noItalicByDefault() {
        return addTail(ComponentRenderers.noItalicComponentRenderer());
    }

    @NotNull
    ComponentRendererBuilder enableCache(@NotNull ComponentRendererCacheProvider cacheProvider);

    @NotNull
    ComponentRendererBuilder disableCache();

    @NotNull
    ComponentRendererBuilder addHead(@NotNull ComponentRenderer<ComponentRenderContext> head);

    @NotNull
    default ComponentRendererBuilder addHead(@NotNull UnaryOperator<Component> tail) {
        return addHead((component, ctx) -> tail.apply(component));
    }

    @NotNull
    ComponentRendererBuilder addTail(@NotNull ComponentRenderer<ComponentRenderContext> tail);

    @NotNull
    default ComponentRendererBuilder addTail(@NotNull UnaryOperator<Component> tail) {
        return addTail((component, ctx) -> tail.apply(component));
    }
}
