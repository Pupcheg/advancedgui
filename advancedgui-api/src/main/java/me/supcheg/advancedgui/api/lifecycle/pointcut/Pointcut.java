package me.supcheg.advancedgui.api.lifecycle.pointcut;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public interface Pointcut extends Keyed {

    @NotNull
    @Contract("_ -> new")
    static Pointcut pointcut(@NotNull Key key) {
        Objects.requireNonNull(key, "key");
        return new PointcutImpl(key);
    }
}
