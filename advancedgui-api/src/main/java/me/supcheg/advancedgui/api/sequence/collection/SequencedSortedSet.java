package me.supcheg.advancedgui.api.sequence.collection;

import me.supcheg.advancedgui.api.sequence.Priority;
import me.supcheg.advancedgui.api.sequence.Sequenced;

import java.util.Set;

public sealed interface SequencedSortedSet<E extends Sequenced<E>> extends Set<E>
        permits GuavaBackedSequencedSortedSet {
    Set<E> subsetWithPriority(Priority priority);
}
