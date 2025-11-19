package me.supcheg.advancedgui.api.sequence.collection;

import com.google.common.collect.SetMultimap;
import me.supcheg.advancedgui.api.sequence.Priority;
import me.supcheg.advancedgui.api.sequence.Sequenced;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;

final class GuavaBackedSequencedSortedSet<E extends Sequenced<E>>
        extends AbstractSet<E>
        implements SequencedSortedSet<E> {
    private final SetMultimap<Priority, E> map;
    private final Collection<E> values;

    GuavaBackedSequencedSortedSet(SetMultimap<Priority, E> map) {
        this.map = map;
        this.values = map.values();
    }

    @Override
    public Iterator<E> iterator() {
        return values.iterator();
    }

    @Override
    public Object[] toArray() {
        return values.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return values.toArray(a);
    }

    @Override
    public boolean add(E e) {
        return map.get(e.priority()).add(e);
    }

    @Override
    public boolean remove(Object o) {
        if (!(o instanceof Sequenced<?> sequenced)) {
            throw new ClassCastException(o + " is not a Sequenced");
        }

        return map.get(sequenced.priority()).remove(o);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public int size() {
        return values.size();
    }

    @Override
    public boolean isEmpty() {
        return values.isEmpty();
    }
}
