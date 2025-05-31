package me.supcheg.advancedgui.api.button.interaction;

import lombok.NoArgsConstructor;
import me.supcheg.advancedgui.api.sequence.Priority;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Objects;

record ButtonInteractionImpl(
        Priority priority,
        ButtonInteractionAction action
) implements ButtonInteraction {
    @Override
    public Builder toBuilder() {
        return new BuilderImpl(this);
    }

    @NoArgsConstructor
    static final class BuilderImpl implements Builder {
        private @Nullable Priority priority;
        private @Nullable ButtonInteractionAction action;

        BuilderImpl(ButtonInteractionImpl impl) {
            this.priority = impl.priority;
            this.action = impl.action;
        }

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
        public Builder action(ButtonInteractionAction action) {
            Objects.requireNonNull(action, "action");
            this.action = action;
            return this;
        }

        @Override
        @Nullable
        public ButtonInteractionAction action() {
            return action;
        }

        public ButtonInteraction build() {
            return new ButtonInteractionImpl(
                    Objects.requireNonNull(priority, "priority"),
                    Objects.requireNonNull(action, "action")
            );
        }
    }
}
