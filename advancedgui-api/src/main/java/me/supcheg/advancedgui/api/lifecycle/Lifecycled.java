package me.supcheg.advancedgui.api.lifecycle;

import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.function.Consumer;

public interface Lifecycled<S> {
    LifecycleListenerRegistry<S> lifecycleListenerRegistry();

    interface Builder<L, B> {

        @Nullable
        LifecycleListenerRegistry<L> lifecycleListenerRegistry();

        B lifecycleListenerRegistry(LifecycleListenerRegistry<L> lifecycleListenerRegistry);

        default B lifecycleListenerRegistry(Consumer<LifecycleListenerRegistry.Builder<L>> consumer) {
            return lifecycleListenerRegistry(LifecycleListenerRegistry.lifecycleListenerRegistry(consumer));
        }
    }
}
