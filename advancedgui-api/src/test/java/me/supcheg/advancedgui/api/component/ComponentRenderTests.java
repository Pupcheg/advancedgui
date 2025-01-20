package me.supcheg.advancedgui.api.component;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.format.TextDecoration.State;
import net.kyori.adventure.text.renderer.ComponentRenderer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static me.supcheg.advancedgui.api.component.ComponentRendererBuilder.componentRenderer;
import static net.kyori.adventure.text.Component.empty;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ComponentRenderTests {

    Component component;
    ComponentRenderContext ctx;

    @BeforeEach
    void setup() {
        component = empty();
        ctx = new ComponentRenderContextImpl();
    }

    @Test
    void noopBuilderTest() {
        var builder = componentRenderer();
        assertThrows(IllegalStateException.class, builder::build);
    }

    @Test
    void singleRendererTest() {
        ComponentRenderer<ComponentRenderContext> mock = componentRendererMock();

        var renderer = componentRenderer(componentRenderer -> componentRenderer
                .addTail(mock)
        );

        assertSame(renderer, mock);
    }

    @Test
    void cacheTest() {
        ComponentRenderer<ComponentRenderContext> mock = componentRendererMock();

        when(mock.render(any(), any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        var renderer = componentRenderer(componentRenderer -> componentRenderer
                .enableCache(HashMap::new)
                .addTail(mock)
        );

        renderer.render(component, ctx); // long path
        renderer.render(component, ctx); // fast path

        verify(mock, times(1))
                .render(component, ctx);
    }

    @Test
    void renderersTest() {
        var renderer = componentRenderer(componentRenderer -> componentRenderer
                .addTail(component -> component.colorIfAbsent(NamedTextColor.RED))
                .addTail(component -> component.decorate(TextDecoration.BOLD))
                .addHead(component -> component.color(NamedTextColor.BLUE))
                .noItalicByDefault()
        );

        assertThat(renderer.render(empty(), ctx))
                .isEqualTo(empty()
                        .color(NamedTextColor.BLUE)
                        .decorate(TextDecoration.BOLD)
                        .decoration(TextDecoration.ITALIC, State.FALSE)
                );
    }

    @SuppressWarnings("unchecked")
    private static ComponentRenderer<ComponentRenderContext> componentRendererMock() {
        return mock(ComponentRenderer.class);
    }
}
