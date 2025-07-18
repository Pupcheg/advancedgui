package me.supcheg.advancedgui.api.sequence.collection;

import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.SetMultimap;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.supcheg.advancedgui.api.sequence.Priority;
import me.supcheg.advancedgui.api.sequence.Sequenced;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.HashSet;
import java.util.TreeMap;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SequencedSortedSets {
    public static <E extends Sequenced<E>> SequencedSortedSet<E> create() {
        return new GuavaBackedSequencedSortedSet<E>(Multimaps.newSetMultimap(new TreeMap<>(), HashSet::new));
    }

    public static <E extends Sequenced<E>> SequencedSortedSet<E> create(SequencedSortedSet<E> sequencedSortedSet) {
        var original = (GuavaBackedSequencedSortedSet<E>) sequencedSortedSet;

        SetMultimap<Priority, E> multimap = Multimaps.newSetMultimap(new TreeMap<>(), HashSet::new);
        multimap.putAll(original.asMultimap());
        return new GuavaBackedSequencedSortedSet<>(multimap);
    }

    public static <E extends Sequenced<E>> SequencedSortedSet<E> of() {
        return new GuavaBackedSequencedSortedSet<E>(ImmutableSetMultimap.of());
    }

    @UnmodifiableView
    public static <E extends Sequenced<E>> SequencedSortedSet<E> copyOf(SequencedSortedSet<E> sequencedSortedSet) {
        var original = (GuavaBackedSequencedSortedSet<E>) sequencedSortedSet;

        return new GuavaBackedSequencedSortedSet<>(
                ImmutableSetMultimap.copyOf(original.asMultimap())
        );
    }
}
