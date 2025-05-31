package me.supcheg.advancedgui.api.builder;

import java.util.Objects;
import java.util.function.Consumer;

public interface Buildable<R, B extends AbstractBuilder<R>> extends net.kyori.adventure.util.Buildable<R, B> {

    static <R, B extends AbstractBuilder<R>> R configureAndBuild(B builder, Consumer<? super B> consumer) {
        Objects.requireNonNull(builder, "builder");
        consumer.accept(builder);
        return builder.build();
    }

}
