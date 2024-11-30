package me.supcheg.advancedgui.api.layout.template;

import me.supcheg.advancedgui.api.builder.Buildable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public non-sealed interface LoomLayoutTemplate extends LayoutTemplate<LoomLayoutTemplate, LoomLayoutTemplate.Builder> {

    @NotNull
    @Contract("-> new")
    static  Builder loomLayoutTemplate() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @NotNull
    @Contract("_ -> new")
    static  LoomLayoutTemplate loomLayoutTemplate(@NotNull Consumer<Builder> consumer) {
        return Buildable.configureAndBuild(loomLayoutTemplate(), consumer);
    }

    interface Builder extends LayoutTemplate.Builder<LoomLayoutTemplate, Builder> {
    }
}
