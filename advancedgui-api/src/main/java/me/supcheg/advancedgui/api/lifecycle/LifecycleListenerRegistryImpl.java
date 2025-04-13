package me.supcheg.advancedgui.api.lifecycle;

import me.supcheg.advancedgui.api.lifecycle.pointcut.Pointcut;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toCollection;

public record LifecycleListenerRegistryImpl<S>(
        @NotNull Map<Pointcut, SortedSet<LifecycleListener<S>>> listeners
) implements LifecycleListenerRegistry<S> {
    @NotNull
    @Override
    public Set<Pointcut> pointcuts() {
        return listeners.keySet();
    }

    @NotNull
    @Override
    public SortedSet<LifecycleListener<S>> listeners(@NotNull Pointcut pointcut) {
        return listeners.getOrDefault(pointcut, Collections.emptySortedSet());
    }

    @NotNull
    @Override
    public Builder<S> toBuilder() {
        return new BuilderImpl<>(this);
    }

    static final class BuilderImpl<S> implements Builder<S> {
        private final Set<LifecycleListener<S>> listeners;

        BuilderImpl(@NotNull LifecycleListenerRegistryImpl<S> impl) {
            this.listeners = impl.listeners.values()
                    .stream()
                    .flatMap(Collection::stream)
                    .collect(Collectors.toSet());
        }

        BuilderImpl() {
            this.listeners = new HashSet<>();
        }

        @NotNull
        @Override
        public Builder<S> add(@NotNull LifecycleListener<S> listener) {
            Objects.requireNonNull(listener, "listener");
            listeners.add(listener);
            return this;
        }

        @NotNull
        @Override
        public LifecycleListenerRegistry<S> build() {
            return new LifecycleListenerRegistryImpl<>(
                    listeners.stream()
                            .collect(
                                    groupingBy(
                                            LifecycleListener::pointcut,
                                            HashMap::new,
                                            collectingAndThen(
                                                    toCollection(TreeSet::new),
                                                    Collections::unmodifiableSortedSet
                                            )
                                    )
                            )
            );
        }
    }
}
