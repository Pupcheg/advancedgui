package me.supcheg.advancedgui.api.lifecycle;

import me.supcheg.advancedgui.api.lifecycle.pointcut.Pointcut;
import me.supcheg.advancedgui.api.sequence.Priority;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Objects;

record LifecycleListenerImpl<S>(
        Pointcut pointcut,
        Priority priority,
        LifecycleAction<S> action
) implements LifecycleListener<S> {
    @Override
    public Builder<S> toBuilder() {
        return new BuilderImpl<>(this);
    }

    static final class BuilderImpl<S> implements LifecycleListener.Builder<S> {
        private @Nullable Pointcut pointcut;
        private @Nullable Priority priority;
        private @Nullable LifecycleAction<S> action;

        BuilderImpl() {
        }

        BuilderImpl(LifecycleListenerImpl<S> impl) {
            this.pointcut = impl.pointcut;
            this.priority = impl.priority;
            this.action = impl.action;
        }

        @Override
        public Builder<S> pointcut(Pointcut pointcut) {
            Objects.requireNonNull(pointcut, "pointcut");
            this.pointcut = pointcut;
            return this;
        }

        @Override
        @Nullable
        public Pointcut pointcut() {
            return pointcut;
        }

        @Override
        @Nullable
        public LifecycleAction<S> action() {
            return action;
        }

        @Override
        public Builder<S> action(LifecycleAction<S> action) {
            Objects.requireNonNull(action, "action");
            this.action = action;
            return this;
        }

        @Override
        @Nullable
        public Priority priority() {
            return priority;
        }

        @Override
        public Builder<S> priority(Priority priority) {
            Objects.requireNonNull(priority, "priority");
            this.priority = priority;
            return this;
        }

        @Override
        public LifecycleListener<S> build() {
            return new LifecycleListenerImpl<>(
                    Objects.requireNonNull(pointcut, "pointcut"),
                    Objects.requireNonNull(priority, "priority"),
                    Objects.requireNonNull(action, "action")
            );
        }
    }
}
