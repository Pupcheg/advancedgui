package me.supcheg.advancedgui.api.lifecycle.pointcut;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;

import java.util.Objects;

public interface Pointcut extends Keyed {

    static Pointcut pointcut(Key key) {
        Objects.requireNonNull(key, "key");
        return new PointcutImpl(key);
    }
}
