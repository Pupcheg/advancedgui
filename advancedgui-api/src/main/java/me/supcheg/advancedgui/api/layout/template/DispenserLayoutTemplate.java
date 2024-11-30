package me.supcheg.advancedgui.api.layout.template;

import me.supcheg.advancedgui.api.builder.Buildable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public non-sealed interface DispenserLayoutTemplate extends LayoutTemplate<DispenserLayoutTemplate, DispenserLayoutTemplate.Builder> {

    @NotNull
    @Contract(value = "-> new", pure = true)
    static  Builder dispenserLayoutTemplate() {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    static  DispenserLayoutTemplate dispenserLayoutTemplate(@NotNull Consumer<Builder> consumer) {
        return Buildable.configureAndBuild(dispenserLayoutTemplate(), consumer);
    }

    interface Builder extends LayoutTemplate.Builder<DispenserLayoutTemplate, DispenserLayoutTemplate.Builder> {
    }
}
