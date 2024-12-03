package me.supcheg.advancedgui.api.sequence.collection;

import me.supcheg.advancedgui.api.sequence.Positioned;
import me.supcheg.advancedgui.api.sequence.pointcut.PointCut;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;
import java.util.SortedSet;

public interface PositionedCollection<P extends Positioned<P>> {
    @NotNull
    static <P extends Positioned<P>> PositionedCollection<P> copyOf(Collection<P> collection) {
        if (collection.isEmpty()) {
            return empty();
        }

        return new EnumMapPositionedCollection<>(collection);
    }

    @NotNull
    static <P extends Positioned<P>> PositionedCollection<P> copyOf(PositionedCollection<P> collection) {
        if (collection.getClass() == EnumMapPositionedCollection.class) {
            return collection;
        }

        return copyOf(collection.allElements());
    }

    @NotNull
    @SuppressWarnings("unchecked")
    static <P extends Positioned<P>> PositionedCollection<P> empty() {
        return (PositionedCollection<P>) EnumMapPositionedCollection.EMPTY;
    }

    @NotNull
    @Unmodifiable
    SortedSet<P> allElementsWith(@NotNull PointCut at);

    @NotNull
    @Unmodifiable
    Collection<P> allElements();
}
