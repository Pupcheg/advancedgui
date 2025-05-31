package me.supcheg.advancedgui.api.lifecycle.pointcut;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.supcheg.advancedgui.api.Advancedgui;

import static me.supcheg.advancedgui.api.lifecycle.pointcut.Pointcut.pointcut;
import static net.kyori.adventure.key.Key.key;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RegistrationPointcut {
    private static final Pointcut PRE_REGISTER = pointcut(key(Advancedgui.NAMESPACE, "pre_register"));
    private static final Pointcut POST_REGISTER = pointcut(key(Advancedgui.NAMESPACE, "post_register"));
    private static final Pointcut PRE_UNREGISTER = pointcut(key(Advancedgui.NAMESPACE, "pre_unregister"));
    private static final Pointcut POST_UNREGISTER = pointcut(key(Advancedgui.NAMESPACE, "post_unregister"));

    public static Pointcut preRegisterPointcut() {
        return PRE_REGISTER;
    }

    public static Pointcut postRegisterPointcut() {
        return POST_REGISTER;
    }

    public static Pointcut preUnregisterPointcut() {
        return PRE_UNREGISTER;
    }

    public static Pointcut postUnregisterPointcut() {
        return POST_UNREGISTER;
    }
}
