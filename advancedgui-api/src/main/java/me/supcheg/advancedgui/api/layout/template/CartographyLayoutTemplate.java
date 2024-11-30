package me.supcheg.advancedgui.api.layout.template;

import me.supcheg.advancedgui.api.builder.Buildable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public non-sealed interface CartographyLayoutTemplate extends LayoutTemplate<CartographyLayoutTemplate, CartographyLayoutTemplate.Builder> {

    @NotNull
    @Contract(value = "-> new", pure = true)
    static  Builder cartographyLayoutTemplate() {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    static  CartographyLayoutTemplate hopperLayoutTemplate(@NotNull Consumer<Builder> consumer) {
        return Buildable.configureAndBuild(cartographyLayoutTemplate(), consumer);
    }

    interface Builder extends LayoutTemplate.Builder<CartographyLayoutTemplate, CartographyLayoutTemplate.Builder> {
    }
}
