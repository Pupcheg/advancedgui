package me.supcheg.advancedgui.api.lifecycle.tick;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.supcheg.advancedgui.api.lifecycle.Pointcut;
import org.jetbrains.annotations.NotNull;

import static me.supcheg.advancedgui.api.key.AdvancedGuiKeys.advancedguiKey;
import static me.supcheg.advancedgui.api.lifecycle.Pointcut.newPointcut;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TickPointcut {
    private static final Pointcut BEFORE = newPointcut(advancedguiKey("before_tick"));
    private static final Pointcut AFTER = newPointcut(advancedguiKey("after_tick"));

    @NotNull
    public static Pointcut beforeTickPointcut() {
        return BEFORE;
    }

    @NotNull
    public static Pointcut afterTickPointcut() {
        return AFTER;
    }
}
