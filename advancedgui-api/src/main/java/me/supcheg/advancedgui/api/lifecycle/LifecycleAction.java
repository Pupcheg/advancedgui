package me.supcheg.advancedgui.api.lifecycle;

import me.supcheg.advancedgui.api.action.Action;

public interface LifecycleAction<S> extends Action {
    void handle(LifecycleContext<S> ctx);
}
