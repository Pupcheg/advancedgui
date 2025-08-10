package me.supcheg.advancedgui.api.sequence.collection;

import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

final class EmptySequencedSortedSet implements SequencedSortedSet {
    static final EmptySequencedSortedSet INSTANCE = new EmptySequencedSortedSet();
    static final Iterator ITERATOR = new Iterator() {
        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public Object next() {
            throw new NoSuchElementException();
        }
    };

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator iterator() {
        return ITERATOR;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public @Nullable Object[] toArray(@Nullable Object[] a) {
        if (a.length > 0)
            a[0] = null;
        return a;
    }

    @Override
    public boolean add(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection c) {
        return c.isEmpty();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection c) {
        return c.isEmpty();
    }

    @Override
    public boolean retainAll(Collection c) {
        return c.isEmpty();
    }

    @Override
    public boolean containsAll(Collection c) {
        return c.isEmpty();
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
