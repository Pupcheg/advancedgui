package me.supcheg.advancedgui.api.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Unmodifiable;

import java.util.AbstractList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CollectionUtil {

    @SafeVarargs
    @Unmodifiable
    public static <T> List<T> makeNoNullsList(T first, T second, T ... other) {
        return new NoNullsFirstSecondOtherList<>(first, second, other);
    }

    private static final class NoNullsFirstSecondOtherList<T> extends AbstractList<T> {
        private final T first;
        private final T second;
        private final T[] other;

        private NoNullsFirstSecondOtherList(T first, T second, T [] other) {
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
    public static <T> Set<T> makeNoNullsSet(T first, T second, T ... other) {
        return Set.copyOf(makeNoNullsList(first, second, other));
    }

}
