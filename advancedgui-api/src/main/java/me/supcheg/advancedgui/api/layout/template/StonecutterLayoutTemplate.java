package me.supcheg.advancedgui.api.layout.template;

import me.supcheg.advancedgui.api.builder.Buildable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public non-sealed interface StonecutterLayoutTemplate extends LayoutTemplate<StonecutterLayoutTemplate, StonecutterLayoutTemplate.Builder> {

    @NotNull
    @Contract("-> new")
    static  Builder stonecutterLayoutTemplate() {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Contract("_ -> new")
    static  StonecutterLayoutTemplate stonecutterLayoutTemplate(@NotNull Consumer<Builder> consumer) {
        return Buildable.configureAndBuild(stonecutterLayoutTemplate(), consumer);
    }

    interface Builder extends LayoutTemplate.Builder<StonecutterLayoutTemplate, Builder> {
    }
}
