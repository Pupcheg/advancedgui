package me.supcheg.advancedgui.api.lifecycle;

import me.supcheg.advancedgui.api.builder.AbstractBuilder;
import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.api.lifecycle.pointcut.Pointcut;
import me.supcheg.advancedgui.api.sequence.Priority;
import me.supcheg.advancedgui.api.sequence.Sequenced;
import net.kyori.examination.Examinable;
import net.kyori.examination.ExaminableProperty;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.function.Consumer;
import java.util.stream.Stream;

public interface LifecycleListener<S> extends Examinable, Sequenced<LifecycleListener<S>>, Buildable<LifecycleListener<S>, LifecycleListener.Builder<S>> {

    static <S> LifecycleListener.Builder<S> lifecycleListener() {
        return new LifecycleListenerImpl.BuilderImpl<>();
    }

    static <S> LifecycleListener<S> lifecycleListener(Consumer<Builder<S>> consumer) {
        return Buildable.configureAndBuild(lifecycleListener(), consumer);
    }

    LifecycleAction<S> action();

    Pointcut pointcut();

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

        Builder<S> pointcut(Pointcut pointcut);

        @Nullable
        LifecycleAction<S> action();


        Builder<S> action(LifecycleAction<S> action);

        @Nullable
        Priority priority();

        Builder<S> priority(Priority priority);
    }
}
