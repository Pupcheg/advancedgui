package me.supcheg.advancedgui.api.component;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.format.TextDecoration.State;
import net.kyori.adventure.text.renderer.ComponentRenderer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static me.supcheg.advancedgui.api.component.ComponentRendererBuilder.componentRenderer;
import static net.kyori.adventure.text.Component.text;
import static org.assertj.core.api.Assertions.assertThat;

class NoItalicByDefaultTests {

    ComponentRenderContext ctx;
    ComponentRenderer<ComponentRenderContext> renderer;

    @BeforeEach
    void setup() {
        ctx = new ComponentRenderContextImpl();
        renderer = componentRenderer(ComponentRendererBuilder::noItalicByDefault);
    }

    @Test
    void originalNoItalicTest() {
        Component original = text("myText", NamedTextColor.GOLD);
        Component originalWithStrictNoItalic = original
                .decoration(TextDecoration.ITALIC, State.FALSE);

        Component render = renderer.render(original, ctx);

        assertThat(render)
                .isEqualTo(originalWithStrictNoItalic);
    }

    @Test
    void originalWithItalicTest() {
        Component original = text("myText", NamedTextColor.GOLD, TextDecoration.ITALIC);

        Component render = renderer.render(original, ctx);

        assertThat(render)
                .isEqualTo(original);
    }

    record ComponentRenderContextImpl() implements ComponentRenderContext {
    }
}
