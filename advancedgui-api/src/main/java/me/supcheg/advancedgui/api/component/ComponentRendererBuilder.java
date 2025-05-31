package me.supcheg.advancedgui.api.component;

import me.supcheg.advancedgui.api.builder.AbstractBuilder;
import me.supcheg.advancedgui.api.builder.Buildable;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.renderer.ComponentRenderer;

import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public interface ComponentRendererBuilder extends AbstractBuilder<ComponentRenderer<ComponentRenderContext>> {

    static ComponentRendererBuilder componentRenderer() {
        return new ComponentRendererBuilderImpl();
    }

    static ComponentRenderer<ComponentRenderContext> componentRenderer(Consumer<ComponentRendererBuilder> consumer) {
        return Buildable.configureAndBuild(componentRenderer(), consumer);
    }

    default ComponentRendererBuilder noItalicByDefault() {
        return addTail(ComponentRenderers.noItalicComponentRenderer());
    }

    ComponentRendererBuilder enableCache(ComponentRendererCacheProvider cacheProvider);

    ComponentRendererBuilder disableCache();

    ComponentRendererBuilder addHead(ComponentRenderer<ComponentRenderContext> head);

    default ComponentRendererBuilder addHead(UnaryOperator<Component> tail) {
        return addHead((component, ctx) -> tail.apply(component));
    }

    ComponentRendererBuilder addTail(ComponentRenderer<ComponentRenderContext> tail);

    default ComponentRendererBuilder addTail(UnaryOperator<Component> tail) {
        return addTail((component, ctx) -> tail.apply(component));
    }
}
