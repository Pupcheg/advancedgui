package me.supcheg.advancedgui.api.lifecycle;

import me.supcheg.advancedgui.api.lifecycle.pointcut.Pointcut;
import me.supcheg.advancedgui.api.sequence.collection.SequencedSortedSet;
import me.supcheg.advancedgui.api.sequence.collection.SequencedSortedSets;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toCollection;

record LifecycleListenerRegistryImpl<S>(
        Map<Pointcut, SequencedSortedSet<LifecycleListener<S>>> listeners
) implements LifecycleListenerRegistry<S> {

    static final LifecycleListenerRegistry<Object> EMPTY = new LifecycleListenerRegistryImpl<>(Map.of());

    @Override
    public SequencedSortedSet<LifecycleListener<S>> listeners(Pointcut pointcut) {
        return listeners.getOrDefault(pointcut, SequencedSortedSets.of());
    }

    @Override
    public Builder<S> toBuilder() {
        return new BuilderImpl<>(this);
    }

    static final class BuilderImpl<S> implements Builder<S> {
        private final Set<LifecycleListener<S>> listeners;

        BuilderImpl(LifecycleListenerRegistryImpl<S> impl) {
            this.listeners = impl.listeners.values()
                    .stream()
                    .<LifecycleListener<S>>mapMulti(Iterable::forEach)
                    .collect(Collectors.toSet());
        }

        BuilderImpl() {
            this.listeners = new HashSet<>();
        }

        @Override
        public Builder<S> add(LifecycleListener<S> listener) {
            Objects.requireNonNull(listener, "listener");
            listeners.add(listener);
            return this;
        }

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
                                                    toCollection(SequencedSortedSets::create),
                                                    SequencedSortedSets::copyOf
                                            )
                                    )
                            )
            );
        }
    }
}
