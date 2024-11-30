package me.supcheg.advancedgui.api.layout.template;

import me.supcheg.advancedgui.api.builder.Buildable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public non-sealed interface SmithingLayoutTemplate extends LayoutTemplate<SmithingLayoutTemplate, SmithingLayoutTemplate.Builder> {

    @NotNull
    @Contract("-> new")
    static  Builder smithingLayoutTemplate() {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Contract("_ -> new")
    static  SmithingLayoutTemplate smithingLayoutTemplate(@NotNull Consumer<Builder> consumer) {
        return Buildable.configureAndBuild(smithingLayoutTemplate(), consumer);
    }

    interface Builder extends LayoutTemplate.Builder<SmithingLayoutTemplate, Builder> {
    }
}
