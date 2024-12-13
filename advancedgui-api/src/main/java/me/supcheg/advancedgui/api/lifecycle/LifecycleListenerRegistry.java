package me.supcheg.advancedgui.api.lifecycle;

import me.supcheg.advancedgui.api.builder.AbstractBuilder;
import me.supcheg.advancedgui.api.builder.Buildable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Set;
import java.util.SortedSet;
import java.util.function.Consumer;

public interface LifecycleListenerRegistry<S> extends Buildable<LifecycleListenerRegistry<S>, LifecycleListenerRegistry.Builder<S>> {

    @NotNull
    @Contract("-> new")
    static <S> Builder<S> lifecycleListenerRegistry() {
        return new LifecycleListenerRegistryImpl.BuilderImpl<>();
    }

    @NotNull
    @Contract("_ -> new")
    static <S> LifecycleListenerRegistry<S> lifecycleListenerRegistry(@NotNull Consumer<Builder<S>> consumer) {
        return Buildable.configureAndBuild(lifecycleListenerRegistry(), consumer);
    }

    @NotNull
    @Unmodifiable
    Set<Pointcut> pointcuts();

    @NotNull
    @Unmodifiable
    SortedSet<LifecycleListener<S>> listeners(@NotNull Pointcut pointcut);

    interface Builder<S> extends AbstractBuilder<LifecycleListenerRegistry<S>> {
        @NotNull
        @Contract("_ -> this")
        Builder<S> add(@NotNull LifecycleListener<S> listener);

        @NotNull
        @Contract("_, _ -> this")
        default Builder<S> add(@NotNull Pointcut pointcut, @NotNull Consumer<LifecycleListener.Builder<S>> consumer) {
            return add(pointcut.lifecycleListener(consumer));
        }
    }
}
