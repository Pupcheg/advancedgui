package me.supcheg.advancedgui.api.component;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.kyori.adventure.text.renderer.ComponentRenderer;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ComponentRenderers {
    public static ComponentRenderer<ComponentRenderContext> noopComponentRenderer() {
        return NoopComponentRenderer.INSTANCE;
    }

    public static ComponentRenderer<ComponentRenderContext> noItalicComponentRenderer() {
        return NoItalicByDefaultComponentRenderer.INSTANCE;
    }
}
