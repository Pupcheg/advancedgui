package me.supcheg.advancedgui.api.sequence;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

record NamedPriorityImpl(
        @NotNull String name,
        int value
) implements NamedPriority {
    @NotNull
    @Contract("_, _ -> new")
    static NamedPriorityImpl namedPriorityImpl(@NotNull String name, int value) {
        Objects.requireNonNull(name, "name");
        NamedPriorityImpl impl = new NamedPriorityImpl(name, value);
        Cache.register(impl);
        return impl;
    }

    @NotNull
    static NamedPriorityImpl byNameOrThrow(@NotNull String name) {
        return Cache.byNameOrThrow(name);
    }

    @Nullable
    static NamedPriorityImpl byValue(int value) {
        return Cache.byValue(value);
    }

    private static class Cache {
        private static final Map<String, NamedPriorityImpl> name2instance = new HashMap<>();
        private static final Map<Integer, NamedPriorityImpl> value2instance = new HashMap<>();

        private static void register(@NotNull NamedPriorityImpl impl) {
            name2instance.put(impl.name(), impl);
            value2instance.put(impl.value(), impl);
        }

        @NotNull
        private static NamedPriorityImpl byNameOrThrow(@NotNull String name) {
            Objects.requireNonNull(name, "name");
            NamedPriorityImpl impl = name2instance.get(name.toLowerCase());
            if (impl == null) {
                throw new IllegalArgumentException("No such name: " + name);
            }
            return impl;
        }

        @Nullable
        private static NamedPriorityImpl byValue(int value) {
            return value2instance.get(value);
        }
    }
}
