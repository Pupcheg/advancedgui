package me.supcheg.advancedgui.api.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.AbstractQueue;
import java.util.Collections;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Queues {

    @Unmodifiable
    public static <E> Queue<E> copyOf(Queue<E> original) {
        return original.isEmpty() ?
                emptyQueue() :
                unmodifiableQueue(new PriorityQueue<>(original));
    }

    @UnmodifiableView
    public static <E> Queue<E> unmodifiableQueue(Queue<E> original) {
        return original instanceof UnmodifiableQueue<?> ?
                original :
                new UnmodifiableQueue<>(original);
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    private static class UnmodifiableQueue<E> extends AbstractQueue<E> {
        private final Queue<E> delegate;

        @Override
        public Iterator<E> iterator() {
            var it = delegate.iterator();

            return new Iterator<>() {
                @Override
                public boolean hasNext() {
                    return it.hasNext();
                }

                @Override
                public E next() {
                    return it.next();
                }
            };
        }

        @Override
        public int size() {
            return delegate.size();
        }

        @Override
        public boolean offer(E t) {
            throw new UnsupportedOperationException();
        }

        @Override
        public E poll() {
            throw new UnsupportedOperationException();
        }

        @Override
        @Nullable
        public E peek() {
            return delegate.peek();
        }
    }

    @Unmodifiable
    public static <E> Queue<E> emptyQueue() {
        // noinspection unchecked
        return (Queue<E>) EmptyQueue.INSTANCE;
    }

    private static class EmptyQueue extends AbstractQueue<Object> {
        private static final EmptyQueue INSTANCE = new EmptyQueue();

        @Override
        public Iterator<Object> iterator() {
            return Collections.emptyIterator();
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean offer(Object o) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object poll() {
            throw new UnsupportedOperationException();
        }

        @Override
        @Nullable
        public Object peek() {
            return null;
        }
    }
}
