package me.supcheg.advancedgui.api.component;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.kyori.adventure.text.renderer.ComponentRenderer;
import org.jetbrains.annotations.NotNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ComponentRenderers {
    @NotNull
    public static ComponentRenderer<ComponentRenderContext> noopComponentRenderer() {
        return NoopComponentRenderer.INSTANCE;
    }

    @NotNull
    public static ComponentRenderer<ComponentRenderContext> noItalicComponentRenderer() {
        return NoItalicByDefaultComponentRenderer.INSTANCE;
    }
}
