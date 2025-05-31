package me.supcheg.advancedgui.api.layout.template.anvil;

import lombok.NoArgsConstructor;
import me.supcheg.advancedgui.api.sequence.Priority;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Objects;

record InputUpdateListenerImpl(
        Priority priority,
        InputUpdateAction action
) implements InputUpdateListener {
    @Override
    public Builder toBuilder() {
        return new BuilderImpl(this);
    }

    @NoArgsConstructor
    static class BuilderImpl implements Builder {
        private @Nullable Priority priority;
        private @Nullable InputUpdateAction action;

        public BuilderImpl(InputUpdateListenerImpl impl) {
            this.priority = impl.priority;
            this.action = impl.action;
        }

        @Override
        public Builder priority(Priority priority) {
            Objects.requireNonNull(priority, "priority");
            this.priority = priority;
            return this;
        }

        @Override
        @Nullable
        public Priority priority() {
            return priority;
        }

        @Override
        public Builder action(InputUpdateAction action) {
            Objects.requireNonNull(action, "action");
            this.action = action;
            return this;
        }

        @Override
        @Nullable
        public InputUpdateAction action() {
            return action;
        }

        @Override
        public InputUpdateListener build() {
            return new InputUpdateListenerImpl(
                    Objects.requireNonNull(priority, "priority"),
                    Objects.requireNonNull(action, "action")
            );
        }
    }
}
