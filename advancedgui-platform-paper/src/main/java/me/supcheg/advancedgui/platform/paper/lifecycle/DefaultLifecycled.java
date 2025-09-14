package me.supcheg.advancedgui.platform.paper.lifecycle;

import me.supcheg.advancedgui.api.lifecycle.LifecycleContext;
import me.supcheg.advancedgui.api.lifecycle.LifecycleListener;
import me.supcheg.advancedgui.api.lifecycle.Lifecycled;
import me.supcheg.advancedgui.api.lifecycle.pointcut.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface DefaultLifecycled<S> extends Lifecycled<S> {
    Logger log = LoggerFactory.getLogger(DefaultLifecycled.class);

    default S lifecycleSubject() {
        // noinspection unchecked
        return (S) this;
    }

    default void handleEachLifecycleAction(Pointcut pointcut) {
        LifecycleContext<S> ctx = this::lifecycleSubject;

        for (LifecycleListener<S> listener : lifecycleListenerRegistry().listeners(pointcut)) {
            try {
                listener.action().handle(ctx);
            } catch (Exception e) {
                log.error("An error occurred while handling lifecycle action {} for {} at {}", listener, this, pointcut, e);
            }
        }
    }
}
