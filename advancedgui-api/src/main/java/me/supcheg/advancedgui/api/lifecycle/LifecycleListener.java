package me.supcheg.advancedgui.api.lifecycle;

import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.api.lifecycle.pointcut.Pointcut;
import me.supcheg.advancedgui.api.sequence.Priority;
import me.supcheg.advancedgui.api.sequence.Sequenced;
import me.supcheg.advancedgui.code.RecordInterface;

import java.util.function.Consumer;

@RecordInterface
public interface LifecycleListener<S> extends Sequenced<LifecycleListener<S>>, Buildable<LifecycleListener<S>, LifecycleListenerBuilder<S>> {

    static <S> LifecycleListenerBuilder<S> lifecycleListener() {
        return new LifecycleListenerBuilderImpl<>();
    }

    static <S> LifecycleListener<S> lifecycleListener(Consumer<LifecycleListenerBuilder<S>> consumer) {
        return Buildable.configureAndBuild(lifecycleListener(), consumer);
    }

    @Override
    Priority priority();

    LifecycleAction<S> action();

    Pointcut pointcut();
}
