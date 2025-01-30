package me.supcheg.advancedgui.api.button.interaction;

import lombok.NoArgsConstructor;
import me.supcheg.advancedgui.api.sequence.Priority;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

record ButtonInteractionImpl(
        @NotNull Priority priority,
        @NotNull ButtonInteractionAction action
) implements ButtonInteraction {
    @NotNull
    @Override
    public Builder toBuilder() {
        return new BuilderImpl(this);
    }

    @NoArgsConstructor
    static final class BuilderImpl implements Builder {
        private Priority priority;
        private ButtonInteractionAction action;

        BuilderImpl(@NotNull ButtonInteractionImpl impl) {
            this.priority = impl.priority;
            this.action = impl.action;
        }

        @NotNull
        @Contract("_ -> this")
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
        public Builder action(@NotNull ButtonInteractionAction action) {
            Objects.requireNonNull(action, "action");
            this.action = action;
            return this;
        }

        @Nullable
        @Override
        public ButtonInteractionAction action() {
            return action;
        }

        @NotNull
        @Contract("-> new")
        public ButtonInteraction build() {
            return new ButtonInteractionImpl(
                    Objects.requireNonNull(priority, "priority"),
                    Objects.requireNonNull(action, "action")
            );
        }
    }
}
