package me.supcheg.advancedgui.api.gui.template;

import me.supcheg.advancedgui.api.builder.AbstractBuilder;
import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.api.gui.background.Background;
import me.supcheg.advancedgui.api.gui.tick.GuiTicker;
import me.supcheg.advancedgui.api.layout.template.LayoutTemplate;
import me.supcheg.advancedgui.api.sequence.collection.MutablePositionedCollection;
import me.supcheg.advancedgui.api.sequence.collection.PositionedCollection;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

import static me.supcheg.advancedgui.api.gui.tick.GuiTicker.guiTicker;

public interface GuiTemplate extends Keyed, Buildable<GuiTemplate, GuiTemplate.Builder> {

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
    PositionedCollection<GuiTicker> tickers();

    @NotNull
    LayoutTemplate<?, ?> layout();

    interface Builder extends AbstractBuilder<GuiTemplate> {

        @NotNull
        @Contract("_ -> this")
        Builder key(@NotNull Key key);

        @Nullable
        Key key();

        @NotNull
        @Contract("_ -> this")
        <L extends LayoutTemplate<L, B>, B extends LayoutTemplate.Builder<L, B>> Builder layout(@NotNull L layout);

        @NotNull
        @Contract("_, _ -> this")
        default <L extends LayoutTemplate<L, B>, B extends LayoutTemplate.Builder<L, B>> Builder layout(@NotNull B builder, @NotNull Consumer<B> consumer) {
            return layout(Buildable.configureAndBuild(builder, consumer));
        }

        @NotNull
        @Contract("_ -> this")
        default <L extends LayoutTemplate<L, B>, B extends LayoutTemplate.Builder<L, B>> Builder layout(@NotNull B layout) {
            return layout(layout.build());
        }

        @NotNull
        @Contract("_ -> this")
        default Builder addTicker(@NotNull Consumer<GuiTicker.Builder> consumer) {
            return addTicker(guiTicker(consumer));
        }

        @NotNull
        @Contract("_ -> this")
        default Builder addTicker(@NotNull GuiTicker.Builder ticker) {
            return addTicker(ticker.build());
        }

        @NotNull
        @Contract("_ -> this")
        Builder addTicker(@NotNull GuiTicker ticker);

        @NotNull
        MutablePositionedCollection<GuiTicker> tickers();

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
