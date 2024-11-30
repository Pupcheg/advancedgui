package me.supcheg.advancedgui.api.sequence.collection;

import me.supcheg.advancedgui.api.sequence.Positioned;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public interface MutablePositionedCollection<P extends Positioned<P>> extends PositionedCollection<P> {

    @NotNull
    static <P extends Positioned<P>> MutablePositionedCollection<P> mutableCopyOf(Collection<P> collection) {
        return new MutableEnumMapPositionedCollection<>(collection);
    }

    @NotNull
    static <P extends Positioned<P>> MutablePositionedCollection<P> mutableCopyOf(PositionedCollection<P> collection) {
        return new MutableEnumMapPositionedCollection<>(collection.allElements());
    }

    @NotNull
    static <P extends Positioned<P>> MutablePositionedCollection<P> mutableEmpty() {
        return new MutableEnumMapPositionedCollection<>();
    }

    void add(@NotNull P positioned);

    void remove(@NotNull P positioned);
}
