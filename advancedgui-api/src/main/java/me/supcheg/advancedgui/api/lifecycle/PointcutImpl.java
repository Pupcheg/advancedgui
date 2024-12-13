package me.supcheg.advancedgui.api.lifecycle;

import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

record PointcutImpl(
        @NotNull Key key
) implements Pointcut {
    @NotNull
    @Override
    public <S> LifecycleListener.Builder<S> lifecycleListener() {
        return new LifecycleListenerImpl.BuilderImpl<>(this);
    }
}
