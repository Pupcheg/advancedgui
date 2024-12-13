package me.supcheg.advancedgui.api.lifecycle;

import me.supcheg.advancedgui.api.action.Action;
import org.jetbrains.annotations.NotNull;

public interface LifecycleAction<S> extends Action {
    void handle(@NotNull LifecycleContext<S> ctx);
}
