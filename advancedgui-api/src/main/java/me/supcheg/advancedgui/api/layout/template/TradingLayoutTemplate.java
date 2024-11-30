package me.supcheg.advancedgui.api.layout.template;

import me.supcheg.advancedgui.api.builder.Buildable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public non-sealed interface TradingLayoutTemplate extends LayoutTemplate<TradingLayoutTemplate, TradingLayoutTemplate.Builder> {

    @NotNull
    @Contract(value = "-> new", pure = true)
    static  Builder tradingLayoutTemplate() {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    static  TradingLayoutTemplate tradingLayoutTemplate(@NotNull Consumer<Builder> consumer) {
        return Buildable.configureAndBuild(tradingLayoutTemplate(), consumer);
    }

    interface Builder extends LayoutTemplate.Builder<TradingLayoutTemplate, TradingLayoutTemplate.Builder> {
    }
}
