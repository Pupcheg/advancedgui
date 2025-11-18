package me.supcheg.advancedgui.api.lifecycle;

import me.supcheg.advancedgui.api.builder.AbstractBuilder;
import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.api.lifecycle.pointcut.Pointcut;
import me.supcheg.advancedgui.api.sequence.Priority;
import me.supcheg.advancedgui.api.sequence.Sequenced;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.function.Consumer;

public interface LifecycleListener<S> extends Sequenced<LifecycleListener<S>>, Buildable<LifecycleListener<S>, LifecycleListener.Builder<S>> {

    static <S> LifecycleListener.Builder<S> lifecycleListener() {
        throw new UnsupportedOperationException();
    }

    static <S> LifecycleListener<S> lifecycleListener(Consumer<Builder<S>> consumer) {
        return Buildable.configureAndBuild(lifecycleListener(), consumer);
    }

    LifecycleAction<S> action();

    Pointcut pointcut();

    interface Builder<S> extends AbstractBuilder<LifecycleListener<S>> {

        @Nullable
        Pointcut pointcut();

        Builder<S> pointcut(Pointcut pointcut);

        @Nullable
        LifecycleAction<S> action();


        Builder<S> action(LifecycleAction<S> action);

        @Nullable
        Priority priority();

        Builder<S> priority(Priority priority);
    }
}
