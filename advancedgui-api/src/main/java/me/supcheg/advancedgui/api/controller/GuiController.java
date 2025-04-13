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
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Consumer;

public interface GuiController<T extends GuiController<T, B>, B extends GuiController.Builder<T, B>> extends Buildable<T, B>, AutoCloseable {

    @NotNull
    ComponentRenderer<ComponentRenderContext> componentRenderer();

    @NotNull
    PointcutSupport pointcutSupport();

    @UnmodifiableView
    @NotNull
    Collection<Gui> guis();

    @NotNull
    default Gui guiOrThrow(@NotNull Key key) {
        return Objects.requireNonNull(gui(key), "Not found gui with key=" + key);
    }

    @Nullable
    Gui gui(@NotNull Key key);

    @NotNull
    @Contract("_ -> new")
    Gui register(@NotNull GuiTemplate template);

    void unregister(@NotNull Key key);

    default void unregister(@NotNull Gui gui) {
        unregister(gui.key());
    }

    interface Builder<T extends GuiController<T, B>, B extends Builder<T, B>> extends AbstractBuilder<T> {
        @NotNull
        @Contract("_ -> this")
        default B componentRenderer(@NotNull Consumer<ComponentRendererBuilder> consumer) {
            ComponentRendererBuilder builder = ComponentRendererBuilder.componentRenderer();
            consumer.accept(builder);
            return componentRenderer(builder);
        }

        @NotNull
        @Contract("_ -> this")
        default B componentRenderer(@NotNull ComponentRendererBuilder renderer) {
            return componentRenderer(renderer.build());
        }

        @NotNull
        @Contract("_ -> this")
        B componentRenderer(@NotNull ComponentRenderer<ComponentRenderContext> componentRenderer);

        @Nullable
        ComponentRenderer<ComponentRenderContext> componentRenderer();
    }
}
