package me.supcheg.advancedgui.api.lifecycle;

import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.api.lifecycle.pointcut.Pointcut;
import me.supcheg.advancedgui.api.sequence.collection.SequencedSortedSet;
import me.supcheg.advancedgui.api.sequence.collection.SequencedSortedSets;
import me.supcheg.advancedgui.code.RecordInterface;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Map;
import java.util.function.Consumer;

@RecordInterface
public interface LifecycleListenerRegistry<S> extends Buildable<LifecycleListenerRegistry<S>, LifecycleListenerRegistryBuilder<S>> {

    static <S> LifecycleListenerRegistryBuilder<S> lifecycleListenerRegistry() {
        return new LifecycleListenerRegistryBuilderImpl<>();
    }

    static <S> LifecycleListenerRegistry<S> lifecycleListenerRegistry(Consumer<LifecycleListenerRegistryBuilder<S>> consumer) {
        return Buildable.configureAndBuild(lifecycleListenerRegistry(), consumer);
    }

    @Unmodifiable
    default SequencedSortedSet<LifecycleListener<S>> listeners(Pointcut pointcut) {
        return listeners().getOrDefault(pointcut, SequencedSortedSets.of());
    }

    @Unmodifiable
    Map<Pointcut, SequencedSortedSet<LifecycleListener<S>>> listeners();
}
