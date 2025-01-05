package me.supcheg.advancedgui.api.component;

import me.supcheg.advancedgui.api.builder.AbstractBuilder;
import me.supcheg.advancedgui.api.builder.Buildable;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.renderer.ComponentRenderer;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

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
        return addTail(NoItalicByDefaultComponentRenderer.INSTANCE);
    }

    @NotNull
    default ComponentRendererBuilder enableSynchronizedWeakCache() {
        return enableCache(Collections.synchronizedMap(new WeakHashMap<>()));
    }

    @NotNull
    default ComponentRendererBuilder enableConcurrentCache() {
        return enableCache(new ConcurrentHashMap<>());
    }

    @NotNull
    ComponentRendererBuilder enableCache(@NotNull Map<Component, Component> cache);

    @NotNull
    ComponentRendererBuilder disableCache();

    @NotNull
    ComponentRendererBuilder addHead(@NotNull ComponentRenderer<ComponentRenderContext> head);

    @NotNull
    ComponentRendererBuilder addTail(@NotNull ComponentRenderer<ComponentRenderContext> tail);
}
