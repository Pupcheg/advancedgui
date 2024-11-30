package me.supcheg.advancedgui.api.layout.template;

import me.supcheg.advancedgui.api.builder.Buildable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public non-sealed interface HorseLayoutTemplate extends LayoutTemplate<HorseLayoutTemplate, HorseLayoutTemplate.Builder> {

    @NotNull
    @Contract(value = "-> new", pure = true)
    static  Builder horseLayoutTemplate() {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    static  HorseLayoutTemplate hopperLayoutTemplate(@NotNull Consumer<Builder> consumer) {
        return Buildable.configureAndBuild(horseLayoutTemplate(), consumer);
    }

    interface Builder extends LayoutTemplate.Builder<HorseLayoutTemplate, HorseLayoutTemplate.Builder> {
    }
}
