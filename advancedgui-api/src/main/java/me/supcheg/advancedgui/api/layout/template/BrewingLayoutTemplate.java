package me.supcheg.advancedgui.api.layout.template;

import me.supcheg.advancedgui.api.builder.Buildable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public non-sealed interface BrewingLayoutTemplate extends LayoutTemplate<BrewingLayoutTemplate, BrewingLayoutTemplate.Builder> {

    @NotNull
    @Contract(value = "-> new", pure = true)
    static  Builder brewingLayoutTemplate() {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    static  BrewingLayoutTemplate brewingLayoutTemplate(@NotNull Consumer<Builder> consumer) {
        return Buildable.configureAndBuild(brewingLayoutTemplate(), consumer);
    }

    interface Builder extends LayoutTemplate.Builder<BrewingLayoutTemplate, BrewingLayoutTemplate.Builder> {
    }
}
