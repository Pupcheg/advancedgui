package me.supcheg.advancedgui.api.lifecycle;

import me.supcheg.advancedgui.api.lifecycle.pointcut.Pointcut;
import me.supcheg.advancedgui.api.sequence.Priority;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

record LifecycleListenerImpl<S>(
        @NotNull Pointcut pointcut,
        @NotNull Priority priority,
        @NotNull LifecycleAction<S> action
) implements LifecycleListener<S> {
    @NotNull
    @Override
    public Builder<S> toBuilder() {
        return new BuilderImpl<>(this);
    }

    static final class BuilderImpl<S> implements LifecycleListener.Builder<S> {
        private Pointcut pointcut;
        private Priority priority;
        private LifecycleAction<S> action;

        BuilderImpl() {
        }

        BuilderImpl(@NotNull LifecycleListenerImpl<S> impl) {
            this.pointcut = impl.pointcut;
            this.priority = impl.priority;
            this.action = impl.action;
        }
        @NotNull
        @Override
        public Builder<S> pointcut(@NotNull Pointcut pointcut) {
            Objects.requireNonNull(pointcut, "pointcut");
            this.pointcut = pointcut;
            return this;
        }

        @Nullable
        @Override
        public Pointcut pointcut() {
            return pointcut;
        }

        @Nullable
        @Override
        public LifecycleAction<S> action() {
            return action;
        }

        @NotNull
        @Override
        public Builder<S> action(@NotNull LifecycleAction<S> action) {
            Objects.requireNonNull(action, "action");
            this.action = action;
            return this;
        }

        @Nullable
        @Override
        public Priority priority() {
            return priority;
        }

        @NotNull
        @Override
        public Builder<S> priority(@NotNull Priority priority) {
            Objects.requireNonNull(priority, "priority");
            this.priority = priority;
            return this;
        }

        @NotNull
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
