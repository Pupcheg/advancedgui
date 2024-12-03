package me.supcheg.advancedgui.api.gui.tick;

import lombok.NoArgsConstructor;
import me.supcheg.advancedgui.api.sequence.Priority;
import me.supcheg.advancedgui.api.sequence.pointcut.PointCut;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

record GuiTickerImpl(
        @NotNull PointCut at,
        @NotNull Priority priority,
        @NotNull GuiTickAction action
) implements GuiTicker {
    @NotNull
    @Override
    public GuiTicker.Builder toBuilder() {
        return new BuilderImpl(this);
    }

    @NoArgsConstructor
    static class BuilderImpl implements GuiTicker.Builder {
        private PointCut at;
        private Priority priority;
        private GuiTickAction action;

        BuilderImpl(@NotNull GuiTickerImpl impl) {
            this.at = impl.at;
            this.priority = impl.priority;
            this.action = impl.action;
        }

        @NotNull
        @Override
        public Builder at(@Nullable PointCut at) {
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
        public Builder priority(@Nullable Priority priority) {
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
        public Builder action(@Nullable GuiTickAction action) {
            this.action = action;
            return this;
        }

        @NotNull
        @Override
        public GuiTickAction action() {
            return action;
        }

        @NotNull
        @Override
        public GuiTicker build() {
            Objects.requireNonNull(at, "at");
            Objects.requireNonNull(priority, "priority");
            Objects.requireNonNull(action, "action");
            return new GuiTickerImpl(at, priority, action);
        }
    }
}
