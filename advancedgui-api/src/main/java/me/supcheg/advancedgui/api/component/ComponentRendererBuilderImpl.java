package me.supcheg.advancedgui.api.component;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.renderer.ComponentRenderer;
import org.jetbrains.annotations.NotNull;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;

final class ComponentRendererBuilderImpl implements ComponentRendererBuilder {
    private final Deque<ComponentRenderer<ComponentRenderContext>> queue = new LinkedList<>();
    private Map<Component, Component> cache;

    @NotNull
    @Override
    public ComponentRendererBuilder enableCache(@NotNull Map<Component, Component> cache) {
        Objects.requireNonNull(cache, "cache");
        this.cache = cache;
        return this;
    }

    @NotNull
    @Override
    public ComponentRendererBuilder disableCache() {
        cache = null;
        return this;
    }

    @NotNull
    @Override
    public ComponentRendererBuilder addHead(@NotNull ComponentRenderer<ComponentRenderContext> head) {
        Objects.requireNonNull(head, "head");
        queue.addFirst(head);
        return this;
    }

    @NotNull
    @Override
    public ComponentRendererBuilder addTail(@NotNull ComponentRenderer<ComponentRenderContext> tail) {
        Objects.requireNonNull(tail, "tail");
        queue.addLast(tail);
        return this;
    }

    @NotNull
    @Override
    public ComponentRenderer<ComponentRenderContext> build() {
        if (queue.isEmpty()) {
            throw new IllegalStateException("No any renderer");
        }

        ComponentRenderer<ComponentRenderContext> renderer;

        renderer = queue.size() == 1 ?
                queue.getFirst() :
                new SequencedComponentRenderer(new LinkedList<>(queue));

        renderer = cache == null ?
                renderer :
                new CachingComponentRenderer(cache, renderer);

        return renderer;
    }

    private record SequencedComponentRenderer(
            @NotNull Iterable<ComponentRenderer<ComponentRenderContext>> renderers
    ) implements ComponentRenderer<ComponentRenderContext> {
        @NotNull
        @Override
        public Component render(@NotNull Component component, @NotNull ComponentRenderContext ctx) {
            for (ComponentRenderer<ComponentRenderContext> renderer : renderers) {
                component = renderer.render(component, ctx);
            }
            return component;
        }
    }

    private record CachingComponentRenderer(
            @NotNull Map<Component, Component> cache,
            @NotNull ComponentRenderer<ComponentRenderContext> delegate
    ) implements ComponentRenderer<ComponentRenderContext> {
        @NotNull
        @Override
        public Component render(@NotNull Component original, @NotNull ComponentRenderContext ctx) {
            return cache.computeIfAbsent(original, component -> delegate.render(component, ctx));
        }
    }
}
