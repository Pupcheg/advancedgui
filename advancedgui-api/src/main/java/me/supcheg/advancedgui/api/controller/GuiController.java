package me.supcheg.advancedgui.api.controller;

import me.supcheg.advancedgui.api.builder.AbstractBuilder;
import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.api.component.ComponentRenderContext;
import me.supcheg.advancedgui.api.component.ComponentRendererBuilder;
import me.supcheg.advancedgui.api.gui.Gui;
import me.supcheg.advancedgui.api.gui.template.GuiTemplate;
import me.supcheg.advancedgui.api.lifecycle.pointcut.support.PointcutSupport;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.renderer.ComponentRenderer;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Consumer;

public interface GuiController<T extends GuiController<T, B>, B extends GuiController.Builder<T, B>> extends Buildable<T, B>, AutoCloseable {

    ComponentRenderer<ComponentRenderContext> componentRenderer();

    PointcutSupport pointcutSupport();

    @UnmodifiableView
    Collection<Gui> guis();

    default Gui guiOrThrow(Key key) {
        return Objects.requireNonNull(gui(key), "Not found gui with key=" + key);
    }

    @Nullable
    Gui gui(Key key);

    Gui register(GuiTemplate template);

    void unregister(Key key);

    default void unregister(Gui gui) {
        unregister(gui.key());
    }

    interface Builder<T extends GuiController<T, B>, B extends Builder<T, B>> extends AbstractBuilder<T> {

        default B componentRenderer(Consumer<ComponentRendererBuilder> consumer) {
            ComponentRendererBuilder builder = ComponentRendererBuilder.componentRenderer();
            consumer.accept(builder);
            return componentRenderer(builder);
        }

        default B componentRenderer(ComponentRendererBuilder renderer) {
            return componentRenderer(renderer.build());
        }


        B componentRenderer(ComponentRenderer<ComponentRenderContext> componentRenderer);

        @Nullable
        ComponentRenderer<ComponentRenderContext> componentRenderer();
    }
}
