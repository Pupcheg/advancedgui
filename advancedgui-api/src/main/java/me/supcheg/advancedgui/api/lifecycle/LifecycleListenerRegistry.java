package me.supcheg.advancedgui.api.lifecycle;

import me.supcheg.advancedgui.api.builder.AbstractBuilder;
import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.api.lifecycle.pointcut.Pointcut;
import me.supcheg.advancedgui.api.sequence.collection.SequencedSortedSet;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Map;
import java.util.function.Consumer;

public interface LifecycleListenerRegistry<S> extends Buildable<LifecycleListenerRegistry<S>, LifecycleListenerRegistry.Builder<S>> {

    @SuppressWarnings("unchecked")
    static <S> LifecycleListenerRegistry<S> emptyLifecycleListenerRegistry() {
        return (LifecycleListenerRegistry<S>) LifecycleListenerRegistryImpl.EMPTY;
    }

    static <S> Builder<S> lifecycleListenerRegistry() {
        return new LifecycleListenerRegistryImpl.BuilderImpl<>();
    }

    static <S> LifecycleListenerRegistry<S> lifecycleListenerRegistry(Consumer<Builder<S>> consumer) {
        return Buildable.configureAndBuild(lifecycleListenerRegistry(), consumer);
    }

    @Unmodifiable
    SequencedSortedSet<LifecycleListener<S>> listeners(Pointcut pointcut);

    @Unmodifiable
    Map<Pointcut, SequencedSortedSet<LifecycleListener<S>>> listeners();

    interface Builder<S> extends AbstractBuilder<LifecycleListenerRegistry<S>> {

        Builder<S> add(LifecycleListener<S> listener);

        default Builder<S> add(Consumer<LifecycleListener.Builder<S>> consumer) {
            return add(LifecycleListener.lifecycleListener(consumer));
        }
    }
}
