package me.supcheg.advancedgui.api.button.tick;

import me.supcheg.advancedgui.api.builder.AbstractBuilder;
import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.api.sequence.Positioned;
import me.supcheg.advancedgui.api.sequence.Priority;
import me.supcheg.advancedgui.api.sequence.pointcut.PointCut;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public interface ButtonTicker extends Positioned<ButtonTicker>, Buildable<ButtonTicker, ButtonTicker.Builder> {

    @NotNull
    @Contract("-> new")
    static Builder buttonTicker() {
        return new ButtonTickerImpl.BuilderImpl();
    }

    @NotNull
    @Contract("_ -> new")
    static ButtonTicker buttonTicker(@NotNull Consumer<Builder> consumer) {
        return Buildable.configureAndBuild(buttonTicker(), consumer);
    }

    @NotNull
    ButtonTickAction action();

    interface Builder extends AbstractBuilder<ButtonTicker> {
        @NotNull
        @Contract("_ -> this")
        Builder at(@NotNull PointCut at);

        @Nullable
        PointCut at();

        @NotNull
        @Contract("_ -> this")
        Builder priority(@NotNull Priority priority);

        @Nullable
        Priority priority();

        @NotNull
        @Contract("_ -> this")
        Builder action(@NotNull ButtonTickAction action);

        @Nullable
        ButtonTickAction action();
    }
}
