package me.supcheg.advancedgui.api.lifecycle;

import org.jetbrains.annotations.NotNull;

public interface LifecycleContext<S> {
    @NotNull
    S subject();
}
