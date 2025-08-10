package me.supcheg.advancedgui.api.sequence.collection;

import com.google.common.collect.Multimaps;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.supcheg.advancedgui.api.sequence.Sequenced;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.TreeMap;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SequencedSortedSets {

    public static <E extends Sequenced<E>> SequencedSortedSet<E> create() {
        return new GuavaBackedSequencedSortedSet<E>(Multimaps.newSetMultimap(new TreeMap<>(), HashSet::new));
    }

    @SafeVarargs
    public static <E extends Sequenced<E>> SequencedSortedSet<E> create(E first, E... rest) {
        SequencedSortedSet<E> set = create();
        set.add(first);
        Collections.addAll(set, rest);
        return set;
    }

    public static <E extends Sequenced<E>> SequencedSortedSet<E> createCopy(Collection<E> collection) {
        SequencedSortedSet<E> set = create();
        set.addAll(collection);
        return set;
    }

    @Unmodifiable
    public static <E extends Sequenced<E>> SequencedSortedSet<E> of() {
        return EmptySequencedSortedSet.INSTANCE;
    }

    @Unmodifiable
    @SafeVarargs
    public static <E extends Sequenced<E>> SequencedSortedSet<E> of(E first, E... rest) {
        return SequencedSortedSets.unmodifiableSequencedSortedSet(create(first, rest));
    }

    @Unmodifiable
    public static <E extends Sequenced<E>> SequencedSortedSet<E> copyOf(Collection<E> collection) {
        return unmodifiableSequencedSortedSet(createCopy(collection));
    }

    public static <E extends Sequenced<E>> SequencedSortedSet<E> unmodifiableSequencedSortedSet(SequencedSortedSet<E> set) {
        if (set instanceof UnmodifiableSequencedSortedSet<E>) {
            return set;
        }

        return new UnmodifiableSequencedSortedSet<>(set);
    }
}
