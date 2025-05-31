package me.supcheg.advancedgui.api.lifecycle;

import me.supcheg.advancedgui.api.lifecycle.pointcut.Pointcut;
import me.supcheg.advancedgui.api.util.Queues;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toCollection;

record LifecycleListenerRegistryImpl<S>(
        @NotNull Map<Pointcut, Queue<LifecycleListener<S>>> listeners
) implements LifecycleListenerRegistry<S> {

    static final LifecycleListenerRegistry<Object> EMPTY = new LifecycleListenerRegistryImpl<>(Map.of());

    @NotNull
    @Override
    public Set<Pointcut> pointcuts() {
        return listeners.keySet();
    }

    @NotNull
    @Override
    public Queue<LifecycleListener<S>> listeners(@NotNull Pointcut pointcut) {
        return listeners.getOrDefault(pointcut, Queues.emptyQueue());
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
                    .<LifecycleListener<S>>mapMulti(Iterable::forEach)
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
            if (listeners.isEmpty()) {
                return LifecycleListenerRegistry.emptyLifecycleListenerRegistry();
            }

            return new LifecycleListenerRegistryImpl<>(
                    listeners.stream()
                            .collect(
                                    groupingBy(
                                            LifecycleListener::pointcut,
                                            HashMap::new,
                                            collectingAndThen(
                                                    toCollection(PriorityQueue::new),
                                                    Queues::unmodifiableQueue
                                            )
                                    )
                            )
            );
        }
    }
}
