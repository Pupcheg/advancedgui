package me.supcheg.advancedgui.api.controller;

import me.supcheg.advancedgui.api.builder.AbstractBuilder;
import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.api.component.ComponentRenderContext;
import me.supcheg.advancedgui.api.component.ComponentRendererBuilder;
import me.supcheg.advancedgui.api.gui.Gui;
import me.supcheg.advancedgui.api.gui.template.GuiTemplate;
import me.supcheg.advancedgui.api.texture.TextureWrapper;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.renderer.ComponentRenderer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Consumer;

public interface GuiController<S, T extends GuiController<S, T, B>, B extends GuiController.Builder<S, T, B>> extends Buildable<T, B>, AutoCloseable {

    @NotNull
    TextureWrapper textureWrapper();

    @NotNull
    ComponentRenderer<ComponentRenderContext> componentRenderer();

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
    @Contract("_, _ -> new")
    Gui register(@NotNull GuiTemplate template);

    void unregister(@NotNull Key key);

    interface Builder<S, T extends GuiController<S, T, B>, B extends Builder<S, T, B>> extends AbstractBuilder<T> {
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

        @NotNull
        @Contract("_ -> this")
        default B textureWrapper(@NotNull Consumer<TextureWrapper.Builder> consumer) {
            TextureWrapper.Builder builder = TextureWrapper.textureWrapper();
            consumer.accept(builder);
            return textureWrapper(builder);
        }

        @NotNull
        @Contract("_ -> this")
        default B textureWrapper(@NotNull TextureWrapper.Builder wrapper) {
            return textureWrapper(wrapper.build());
        }

        @NotNull
        @Contract("_ -> this")
        B textureWrapper(@NotNull TextureWrapper textureWrapper);

        @Nullable
        TextureWrapper textureWrapper();
    }
}
