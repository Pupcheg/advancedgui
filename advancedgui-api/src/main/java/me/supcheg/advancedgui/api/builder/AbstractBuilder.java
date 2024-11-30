package me.supcheg.advancedgui.api.builder;

import net.kyori.adventure.util.Buildable;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Objects;

@SuppressWarnings("deprecation") // Buildable.Builder for compatibility
public interface AbstractBuilder<R> extends net.kyori.adventure.builder.AbstractBuilder<R>, Buildable.Builder<R> {

    static <C extends Collection<T>, T> void replaceCollectionContents(@NotNull C dst, @NotNull C src) {
        Objects.requireNonNull(src, "src");
        if (dst == src) {
            throw new IllegalArgumentException("Got same object");
        }

        dst.clear();
        dst.addAll(src);
    }

}
