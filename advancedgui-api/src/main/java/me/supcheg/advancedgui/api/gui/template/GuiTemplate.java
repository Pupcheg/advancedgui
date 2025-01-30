package me.supcheg.advancedgui.api.gui.template;

import me.supcheg.advancedgui.api.builder.AbstractBuilder;
import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.api.gui.Gui;
import me.supcheg.advancedgui.api.gui.background.Background;
import me.supcheg.advancedgui.api.layout.Layout;
import me.supcheg.advancedgui.api.layout.template.LayoutTemplate;
import me.supcheg.advancedgui.api.lifecycle.Lifecycled;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public interface GuiTemplate extends Keyed, Buildable<GuiTemplate, GuiTemplate.Builder>, Lifecycled<Gui> {

    @NotNull
    @Contract("-> new")
    static Builder gui() {
        return new GuiTemplateImpl.BuilderImpl();
    }

    @NotNull
    @Contract("_ -> new")
    static GuiTemplate gui(@NotNull Consumer<GuiTemplate.Builder> consumer) {
        return Buildable.configureAndBuild(gui(), consumer);
    }

    @NotNull
    Background background();

    @NotNull
    LayoutTemplate<?, ?, ?> layout();

    interface Builder extends AbstractBuilder<GuiTemplate>, Lifecycled.Builder<Gui, Builder> {

        @NotNull
        @Contract("_ -> this")
        Builder key(@NotNull Key key);

        @Nullable
        Key key();

        @NotNull
        @Contract("_ -> this")
        Builder layout(@NotNull LayoutTemplate<?, ?, ?> layout);

        @NotNull
        @Contract("_, _ -> this")
        default <L extends Layout<L>, T extends LayoutTemplate<L, T, B>, B extends LayoutTemplate.Builder<L, T, B>>  Builder layout(@NotNull B builder, @NotNull Consumer<B> consumer) {
            return layout(Buildable.configureAndBuild(builder, consumer));
        }

        @NotNull
        @Contract("_ -> this")
        default <L extends Layout<L>, T extends LayoutTemplate<L, T, B>, B extends LayoutTemplate.Builder<L, T, B>> Builder layout(@NotNull B layout) {
            return layout(layout.build());
        }

        @Nullable
        LayoutTemplate<?, ?, ?> layout();

        @NotNull
        @Contract("_ -> this")
        default Builder background(@NotNull Consumer<Background.Builder> consumer) {
            return background(Background.background(consumer));
        }

        @NotNull
        @Contract("_ -> this")
        Builder background(@NotNull Background background);

        @Nullable
        Background background();
    }
}
