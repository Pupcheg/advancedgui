package me.supcheg.advancedgui.api.sequence.collection;

import com.google.common.collect.Multimaps;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.supcheg.advancedgui.api.sequence.Priority;
import me.supcheg.advancedgui.api.sequence.Sequenced;
import org.jetbrains.annotations.Unmodifiable;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.TreeMap;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SequencedSortedSets {

    public static <E extends Sequenced<E>> SequencedSortedSet<E> create() {
        return new GuavaBackedSequencedSortedSet<>(Multimaps.newSetMultimap(new TreeMap<Priority, Collection<E>>(), HashSet::new));
    }

    public static <E extends Sequenced<E>> SequencedSortedSet<E> create(E element) {
        SequencedSortedSet<E> set = create();
        set.add(element);
        return set;
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
    @SuppressWarnings("unchecked")
    public static <E extends Sequenced<E>> SequencedSortedSet<E> of() {
        return (SequencedSortedSet<E>) EmptySequencedSortedSet.INSTANCE;
    }

    @Unmodifiable
    public static <E extends Sequenced<E>> SequencedSortedSet<E> of(E element) {
        return unmodifiableSequencedSortedSet(create(element));
    }

    @Unmodifiable
    @SafeVarargs
    public static <E extends Sequenced<E>> SequencedSortedSet<E> of(E first, E... rest) {
        return unmodifiableSequencedSortedSet(create(first, rest));
    }

    @Unmodifiable
    public static <E extends Sequenced<E>> SequencedSortedSet<E> copyOf(Collection<E> collection) {
        if (collection instanceof UnmodifiableSequencedSortedSet<E> unmodifiable) {
            return unmodifiable;
        }

        if (collection.isEmpty()) {
            return of();
        }

        return unmodifiableSequencedSortedSet(createCopy(collection));
    }

    @UnmodifiableView
    public static <E extends Sequenced<E>> SequencedSortedSet<E> unmodifiableSequencedSortedSet(SequencedSortedSet<E> set) {
        if (set instanceof UnmodifiableSequencedSortedSet<E>) {
            return set;
        }

        return new UnmodifiableSequencedSortedSet<>(set);
    }
}
