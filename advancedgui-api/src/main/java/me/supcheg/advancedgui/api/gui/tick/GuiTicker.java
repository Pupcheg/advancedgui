package me.supcheg.advancedgui.api.gui.tick;

import me.supcheg.advancedgui.api.builder.AbstractBuilder;
import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.api.sequence.At;
import me.supcheg.advancedgui.api.sequence.Positioned;
import me.supcheg.advancedgui.api.sequence.Priority;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public interface GuiTicker extends Positioned<GuiTicker>, Buildable<GuiTicker, GuiTicker.Builder> {

    @NotNull
    @Contract(value = " -> new", pure = true)
    static Builder guiTicker() {
        return new GuiTickerImpl.BuilderImpl();
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    static  GuiTicker guiTicker(@NotNull Consumer<GuiTicker.Builder> consumer) {
        return Buildable.configureAndBuild(guiTicker(), consumer);
    }

    @NotNull
    @Override
    At at();

    @NotNull
    @Override
    Priority priority();

    @NotNull
    GuiTickAction action();

    interface Builder extends AbstractBuilder<GuiTicker> {
        @NotNull
        @Contract("_ -> this")
        Builder at(@NotNull At at);

        @Nullable
        At at();

        @NotNull
        @Contract("_ -> this")
        Builder priority(@NotNull Priority priority);

        @Nullable
        Priority priority();

        @NotNull
        @Contract("_ -> this")
        Builder action(@NotNull GuiTickAction action);

        @Nullable
        GuiTickAction action();
    }
}
