package me.supcheg.advancedgui.api.lifecycle;

import me.supcheg.advancedgui.api.builder.AbstractBuilder;
import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.api.lifecycle.pointcut.Pointcut;
import net.kyori.examination.Examinable;
import net.kyori.examination.ExaminableProperty;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;

public interface LifecycleListenerRegistry<S> extends Examinable, Buildable<LifecycleListenerRegistry<S>, LifecycleListenerRegistry.Builder<S>> {

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
    Queue<LifecycleListener<S>> listeners(@NotNull Pointcut pointcut);

    @NotNull
    @Unmodifiable
    Map<Pointcut, Queue<LifecycleListener<S>>> listeners();

    @Override
    default @NotNull Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(
                ExaminableProperty.of("listeners", listeners().values().stream().flatMap(Collection::stream))
        );
    }

    interface Builder<S> extends AbstractBuilder<LifecycleListenerRegistry<S>> {
        @NotNull
        @Contract("_ -> this")
        Builder<S> add(@NotNull LifecycleListener<S> listener);

        @NotNull
        @Contract("_ -> this")
        default Builder<S> add(@NotNull Consumer<LifecycleListener.Builder<S>> consumer) {
            return add(LifecycleListener.lifecycleListener(consumer));
        }
    }
}
