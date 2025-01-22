package me.supcheg.advancedgui.api.lifecycle;

import me.supcheg.advancedgui.api.builder.Buildable;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Consumer;

public interface Pointcut extends Keyed {

    @NotNull
    @Contract("_ -> new")
    static Pointcut pointcut(@NotNull Key key) {
        Objects.requireNonNull(key, "key");
        return new PointcutImpl(key);
    }

    @NotNull
    @Contract("-> new")
    <S> LifecycleListener.Builder<S> lifecycleListener();

    @NotNull
    @Contract("_ -> new")
    default <S> LifecycleListener<S> lifecycleListener(@NotNull Consumer<LifecycleListener.Builder<S>> consumer) {
        return Buildable.configureAndBuild(lifecycleListener(), consumer);
    }
}
