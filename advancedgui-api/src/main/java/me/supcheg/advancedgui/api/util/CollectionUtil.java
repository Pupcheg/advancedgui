package me.supcheg.advancedgui.api.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.AbstractList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CollectionUtil {

    @NotNull
    public static <E> SortedSet<E> copyOf(@NotNull SortedSet<? extends E> original) {
        return original.isEmpty() ?
                Collections.emptySortedSet() :
                Collections.unmodifiableSortedSet(new TreeSet<E>(original));
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

}
