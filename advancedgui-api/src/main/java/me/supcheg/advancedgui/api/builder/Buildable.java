package me.supcheg.advancedgui.api.builder;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Consumer;

public interface Buildable<R, B extends AbstractBuilder<R>> extends net.kyori.adventure.util.Buildable<R, B> {

    @NotNull
    static <R, B extends AbstractBuilder<R>> R configureAndBuild(@NotNull B builder,
                                                                 @NotNull Consumer<? super B> consumer) {
        Objects.requireNonNull(builder, "builder");
        consumer.accept(builder);
        return builder.build();
    }

}
