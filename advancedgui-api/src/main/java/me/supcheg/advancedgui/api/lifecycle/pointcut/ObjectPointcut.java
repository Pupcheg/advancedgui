package me.supcheg.advancedgui.api.lifecycle.pointcut;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.supcheg.advancedgui.api.Advancedgui;

import static me.supcheg.advancedgui.api.lifecycle.pointcut.Pointcut.pointcut;
import static net.kyori.adventure.key.Key.key;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ObjectPointcut {
    private static final Pointcut CONSTRUCT = pointcut(key(Advancedgui.NAMESPACE, "construct"));

    public static Pointcut objectConstructPointcut() {
        return CONSTRUCT;
    }
}
