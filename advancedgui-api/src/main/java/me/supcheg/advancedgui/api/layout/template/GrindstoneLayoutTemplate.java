package me.supcheg.advancedgui.api.layout.template;

import me.supcheg.advancedgui.api.builder.Buildable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public non-sealed interface GrindstoneLayoutTemplate extends LayoutTemplate<GrindstoneLayoutTemplate, GrindstoneLayoutTemplate.Builder> {

    @NotNull
    @Contract(value = "-> new", pure = true)
    static  Builder grindstoneLayoutTemplate() {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    static  GrindstoneLayoutTemplate grindstoneLayoutTemplate(@NotNull Consumer<Builder> consumer) {
        return Buildable.configureAndBuild(grindstoneLayoutTemplate(), consumer);
    }

    interface Builder extends LayoutTemplate.Builder<GrindstoneLayoutTemplate, GrindstoneLayoutTemplate.Builder> {
    }
}
