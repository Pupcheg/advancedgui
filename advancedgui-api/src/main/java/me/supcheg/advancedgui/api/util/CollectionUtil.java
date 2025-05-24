package me.supcheg.advancedgui.api.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.AbstractList;
import java.util.AbstractQueue;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CollectionUtil {

    @NotNull
    @Unmodifiable
    public static <E> Queue<E> copyOf(@NotNull Queue<? extends E> original) {
        return original.isEmpty() ?
                emptyQueue() :
                unmodifiableQueue(new PriorityQueue<>(original));
    }

    @NotNull
    @UnmodifiableView
    public static <E> Queue<E> unmodifiableQueue(@NotNull Queue<? extends E> original) {
        return (Queue<E>) (original instanceof UnmodifiableQueue<? extends E> ? original : new UnmodifiableQueue<>(original));
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    private static class UnmodifiableQueue<E> extends AbstractQueue<E> {
        private final Queue<E> delegate;

        @NotNull
        @Override
        public Iterator<E> iterator() {
            return delegate.iterator();
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
        public E peek() {
            return delegate.peek();
        }
    }

    @NotNull
    @Unmodifiable
    public static <E> Queue<E> emptyQueue() {
        return (Queue<E>) EmptyQueue.INSTANCE;
    }

    private static class EmptyQueue extends AbstractQueue<Object> {
        private static final EmptyQueue INSTANCE = new EmptyQueue();

        @NotNull
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
            return null;
        }

        @Override
        public Object peek() {
            return null;
        }
    }

    @SafeVarargs
    @Unmodifiable
    @NotNull
    @Contract("_, _, _ -> new")
    public static <T> List<T> makeNoNullsList(@NotNull T first, @NotNull T second, @NotNull T @NotNull ... other) {
        return new NoNullsFirstSecondOtherList<>(first, second, other);
    }

    private static final class NoNullsFirstSecondOtherList<T> extends AbstractList<T> {
        private final T first;
        private final T second;
        private final T[] other;

        public NoNullsFirstSecondOtherList(
                @NotNull T first,
                @NotNull T second,
                @NotNull T @NotNull [] other
        ) {
            this.first = Objects.requireNonNull(first);
            this.second = Objects.requireNonNull(second);

            for (T o : other) {
                Objects.requireNonNull(o);
            }
            this.other = other;
        }

        @Override
        public T get(int index) {
            return switch (index) {
                case 0 -> first;
                case 1 -> second;
                default -> other[index - 2];
            };
        }

        @Override
        public int size() {
            return 2 + other.length;
        }
    }

    @SafeVarargs
    @Unmodifiable
    @NotNull
    @Contract("_, _, _ -> new")
    public static <T> Set<T> makeNoNullsSet(@NotNull T first, @NotNull T second, @NotNull T @NotNull ... other) {
        return Set.copyOf(makeNoNullsList(first, second, other));
    }

}
