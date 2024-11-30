package me.supcheg.advancedgui.api.layout.template;

import me.supcheg.advancedgui.api.builder.Buildable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public non-sealed interface CraftingLayoutTemplate extends LayoutTemplate<CraftingLayoutTemplate, CraftingLayoutTemplate.Builder> {

    @NotNull
    @Contract(value = "-> new", pure = true)
    static  Builder craftingLayoutTemplate() {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    static  CraftingLayoutTemplate craftingLayoutTemplate(@NotNull Consumer<Builder> consumer) {
        return Buildable.configureAndBuild(craftingLayoutTemplate(), consumer);
    }

    interface Builder extends LayoutTemplate.Builder<CraftingLayoutTemplate, CraftingLayoutTemplate.Builder> {
    }
}
