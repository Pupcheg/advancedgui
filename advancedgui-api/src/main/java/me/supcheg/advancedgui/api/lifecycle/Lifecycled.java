package me.supcheg.advancedgui.api.lifecycle;

public interface Lifecycled<S> {
    LifecycleListenerRegistry<S> lifecycleListenerRegistry();
}
