package me.supcheg.advancedgui.api.button.tick;

import lombok.NoArgsConstructor;
import me.supcheg.advancedgui.api.sequence.Priority;
import me.supcheg.advancedgui.api.sequence.pointcut.PointCut;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

record ButtonTickerImpl(
        @NotNull PointCut at,
        @NotNull Priority priority,
        @NotNull ButtonTickAction action
) implements ButtonTicker {

    @NotNull
    @Override
    public Builder toBuilder() {
        return new BuilderImpl(this);
    }

    @NoArgsConstructor
    static class BuilderImpl implements ButtonTicker.Builder {
        private PointCut at;
        private Priority priority;
        private ButtonTickAction action;

        BuilderImpl(@NotNull ButtonTickerImpl impl) {
            this.at = impl.at;
            this.priority = impl.priority;
            this.action = impl.action;
        }

        @NotNull
        @Override
        public Builder at(@NotNull PointCut at) {
            Objects.requireNonNull(at, "at");
            this.at = at;
            return this;
        }

        @Nullable
        @Override
        public PointCut at() {
            return at;
        }

        @NotNull
        @Override
        public Builder priority(@NotNull Priority priority) {
            Objects.requireNonNull(priority, "priority");
            this.priority = priority;
            return this;
        }

        @NotNull
        @Override
        public Priority priority() {
            return priority;
        }

        @NotNull
        @Override
        public Builder action(@NotNull ButtonTickAction action) {
            Objects.requireNonNull(action, "action");
            this.action = action;
            return this;
        }

        @NotNull
        @Override
        public ButtonTickAction action() {
            return action;
        }

        @NotNull
        @Override
        public ButtonTicker build() {
            Objects.requireNonNull(at, "at");
            Objects.requireNonNull(priority, "priority");
            Objects.requireNonNull(action, "action");
            return new ButtonTickerImpl(at, priority, action);
        }
    }
}
