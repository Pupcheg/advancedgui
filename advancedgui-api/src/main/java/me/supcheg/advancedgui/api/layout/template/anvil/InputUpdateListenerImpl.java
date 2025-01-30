package me.supcheg.advancedgui.api.layout.template.anvil;

import lombok.NoArgsConstructor;
import me.supcheg.advancedgui.api.sequence.Priority;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

record InputUpdateListenerImpl(
        @NotNull Priority priority,
        @NotNull InputUpdateAction action
) implements InputUpdateListener {
    @NotNull
    @Override
    public Builder toBuilder() {
        return new BuilderImpl(this);
    }

    @NoArgsConstructor
    static class BuilderImpl implements Builder {
        private Priority priority;
        private InputUpdateAction action;

        public BuilderImpl(@NotNull InputUpdateListenerImpl impl) {
            this.priority = impl.priority;
            this.action = impl.action;
        }

        @NotNull
        @Override
        public Builder priority(@NotNull Priority priority) {
            Objects.requireNonNull(priority, "priority");
            this.priority = priority;
            return this;
        }

        @Nullable
        @Override
        public Priority priority() {
            return priority;
        }

        @NotNull
        @Override
        public Builder action(@NotNull InputUpdateAction action) {
            Objects.requireNonNull(action, "action");
            this.action = action;
            return this;
        }

        @Nullable
        @Override
        public InputUpdateAction action() {
            return action;
        }

        @NotNull
        @Override
        public InputUpdateListener build() {
            return new InputUpdateListenerImpl(
                    Objects.requireNonNull(priority, "priority"),
                    Objects.requireNonNull(action, "action")
            );
        }
    }
}
