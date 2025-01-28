package me.supcheg.advancedgui.api.lifecycle;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public interface Lifecycled<S> {
    @NotNull
    LifecycleListenerRegistry<S> lifecycleListenerRegistry();

    interface Builder<L, B> {

        @Nullable
        LifecycleListenerRegistry<L> lifecycleListenerRegistry();

        @NotNull
        @Contract("_ -> this")
        B lifecycleListenerRegistry(@NotNull LifecycleListenerRegistry<L> lifecycleListenerRegistry);

        @NotNull
        @Contract("_ -> this")
        default B lifecycleListenerRegistry(@NotNull Consumer<LifecycleListenerRegistry.Builder<L>> consumer) {
            return lifecycleListenerRegistry(LifecycleListenerRegistry.lifecycleListenerRegistry(consumer));
        }
    }
}
