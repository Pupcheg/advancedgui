package me.supcheg.advancedgui.api.component;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.renderer.ComponentRenderer;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

final class ComponentRendererBuilderImpl implements ComponentRendererBuilder {
    private final Deque<ComponentRenderer<ComponentRenderContext>> queue = new LinkedList<>();
    private @Nullable ComponentRendererCacheProvider cacheProvider;

    @Override
    public ComponentRendererBuilder enableCache(ComponentRendererCacheProvider cacheProvider) {
        Objects.requireNonNull(cacheProvider, "cacheProvider");
        this.cacheProvider = cacheProvider;
        return this;
    }

    @Override
    public ComponentRendererBuilder disableCache() {
        cacheProvider = null;
        return this;
    }

    @Override
    public ComponentRendererBuilder addHead(ComponentRenderer<ComponentRenderContext> head) {
        Objects.requireNonNull(head, "head");
        queue.addFirst(head);
        return this;
    }

    @Override
    public ComponentRendererBuilder addTail(ComponentRenderer<ComponentRenderContext> tail) {
        Objects.requireNonNull(tail, "tail");
        queue.addLast(tail);
        return this;
    }

    @Override
    public ComponentRenderer<ComponentRenderContext> build() {
        if (queue.isEmpty()) {
            return NoopComponentRenderer.INSTANCE;
        }

        ComponentRenderer<ComponentRenderContext> renderer;

        renderer = queue.size() == 1 ?
                queue.getFirst() :
                new SequencedComponentRenderer(List.copyOf(queue));

        renderer = cacheProvider == null ?
                renderer :
                new CachingComponentRenderer(cacheProvider.newCacheMap(), renderer);

        return renderer;
    }

    private record SequencedComponentRenderer(
            Iterable<ComponentRenderer<ComponentRenderContext>> renderers
    ) implements ComponentRenderer<ComponentRenderContext> {
        @Override
        public Component render(Component component, ComponentRenderContext ctx) {
            for (ComponentRenderer<ComponentRenderContext> renderer : renderers) {
                component = renderer.render(component, ctx);
            }
            return component;
        }
    }

    private record CachingComponentRenderer(
            Map<Component, Component> cache,
            ComponentRenderer<ComponentRenderContext> delegate
    ) implements ComponentRenderer<ComponentRenderContext> {
        @Override
        public Component render(Component original, ComponentRenderContext ctx) {
            return cache.computeIfAbsent(original, component -> delegate.render(component, ctx));
        }
    }
}
