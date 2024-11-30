package me.supcheg.advancedgui.api.util;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
public final class CollectionUtil {
    @SafeVarargs
    @Unmodifiable
    @NotNull
    @Contract("_, _, _ -> new")
    public static <T> List<T> makeNoNullsList(@NotNull T t1, @NotNull T t2, @NotNull T @NotNull ... other) {
        if (other.length == 0) {
            return List.of(t1, t2);
        }
        List<T> list = new ArrayList<>(2 + other.length);
        list.add(Objects.requireNonNull(t1));
        list.add(Objects.requireNonNull(t2));
        list.addAll(Arrays.asList(other));

        return List.copyOf(list);
    }
}
