package me.supcheg.advancedgui.api.layout.template;

import me.supcheg.advancedgui.api.builder.Buildable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public non-sealed interface HopperLayoutTemplate extends LayoutTemplate<HopperLayoutTemplate, HopperLayoutTemplate.Builder> {

    @NotNull
    @Contract(value = "-> new", pure = true)
    static  Builder hopperLayoutTemplate() {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    static  HopperLayoutTemplate hopperLayoutTemplate(@NotNull Consumer<Builder> consumer) {
        return Buildable.configureAndBuild(hopperLayoutTemplate(), consumer);
    }

    interface Builder extends LayoutTemplate.Builder<HopperLayoutTemplate, HopperLayoutTemplate.Builder> {
    }
}
