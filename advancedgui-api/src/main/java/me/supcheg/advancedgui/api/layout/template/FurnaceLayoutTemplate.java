package me.supcheg.advancedgui.api.layout.template;

import me.supcheg.advancedgui.api.builder.Buildable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public non-sealed interface FurnaceLayoutTemplate extends LayoutTemplate<FurnaceLayoutTemplate, FurnaceLayoutTemplate.Builder> {

    @NotNull
    @Contract("-> new")
    static  Builder furnaceLayoutTemplate() {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Contract("_ -> new")
    static  FurnaceLayoutTemplate furnaceLayoutTemplate(@NotNull Consumer<Builder> consumer) {
        return Buildable.configureAndBuild(furnaceLayoutTemplate(), consumer);
    }

    interface Builder extends LayoutTemplate.Builder<FurnaceLayoutTemplate, Builder> {
    }
}
