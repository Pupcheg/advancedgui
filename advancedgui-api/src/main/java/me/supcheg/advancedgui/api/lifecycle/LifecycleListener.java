package me.supcheg.advancedgui.api.lifecycle;

import me.supcheg.advancedgui.api.builder.AbstractBuilder;
import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.api.lifecycle.pointcut.Pointcut;
import me.supcheg.advancedgui.api.sequence.Priority;
import me.supcheg.advancedgui.api.sequence.Sequenced;
import net.kyori.examination.Examinable;
import net.kyori.examination.ExaminableProperty;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.stream.Stream;

public interface LifecycleListener<S> extends Examinable, Sequenced<LifecycleListener<S>>, Buildable<LifecycleListener<S>, LifecycleListener.Builder<S>> {

    @NotNull
    @Contract("-> new")
    static <S> LifecycleListener.Builder<S> lifecycleListener() {
        return new LifecycleListenerImpl.BuilderImpl<>();
    }

    @NotNull
    @Contract("_ -> new")
    static <S> LifecycleListener<S> lifecycleListener(@NotNull Consumer<Builder<S>> consumer) {
        return Buildable.configureAndBuild(lifecycleListener(), consumer);
    }

    @NotNull
    LifecycleAction<S> action();

    @NotNull
    Pointcut pointcut();

    @NotNull
    @Override
    default Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(
                ExaminableProperty.of("action", action()),
                ExaminableProperty.of("pointcut", pointcut()),
                ExaminableProperty.of("priority", priority())
        );
    }

    interface Builder<S> extends AbstractBuilder<LifecycleListener<S>> {

        @Nullable
        Pointcut pointcut();

        @NotNull
        @Contract("_ -> this")
        Builder<S> pointcut(@NotNull Pointcut pointcut);

        @Nullable
        LifecycleAction<S> action();

        @NotNull
        @Contract("_ -> this")
        Builder<S> action(@NotNull LifecycleAction<S> action);

        @Nullable
        Priority priority();

        @NotNull
        @Contract("_ -> this")
        Builder<S> priority(@NotNull Priority priority);
    }
}
