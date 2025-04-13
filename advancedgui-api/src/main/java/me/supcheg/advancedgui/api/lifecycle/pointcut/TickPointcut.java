package me.supcheg.advancedgui.api.lifecycle.pointcut;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.supcheg.advancedgui.api.Advancedgui;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static me.supcheg.advancedgui.api.lifecycle.pointcut.Pointcut.pointcut;
import static net.kyori.adventure.key.Key.key;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TickPointcut {
    private static final Pointcut BEFORE_TICK = pointcut(key(Advancedgui.NAMESPACE, "before_tick"));
    private static final Pointcut AFTER_TICK = pointcut(key(Advancedgui.NAMESPACE, "after_tick"));

    @Contract(pure = true)
    @NotNull
    public static Pointcut beforeTickPointcut() {
        return BEFORE_TICK;
    }

    @Contract(pure = true)
    @NotNull
    public static Pointcut afterTickPointcut() {
        return AFTER_TICK;
    }
}
