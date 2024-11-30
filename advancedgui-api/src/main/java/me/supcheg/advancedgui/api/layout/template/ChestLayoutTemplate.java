package me.supcheg.advancedgui.api.layout.template;

import me.supcheg.advancedgui.api.builder.Buildable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public non-sealed interface ChestLayoutTemplate extends LayoutTemplate<ChestLayoutTemplate, ChestLayoutTemplate.Builder> {

    @NotNull
    @Contract(value = "-> new", pure = true)
    static  Builder chestLayoutTemplate() {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    static  ChestLayoutTemplate chestLayoutTemplate(@NotNull Consumer<Builder> consumer) {
        return Buildable.configureAndBuild(chestLayoutTemplate(), consumer);
    }

    int rows();

    interface Builder extends LayoutTemplate.Builder<ChestLayoutTemplate, Builder> {

        @NotNull
        Builder rows(int rows);

        @Nullable
        Integer rows();
    }
}
