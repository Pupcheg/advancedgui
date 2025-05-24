package me.supcheg.advancedgui.api.layout.template;

import me.supcheg.advancedgui.api.button.template.ButtonTemplate;
import me.supcheg.advancedgui.api.layout.AnvilLayout;
import me.supcheg.advancedgui.api.layout.template.anvil.InputUpdateListener;
import me.supcheg.advancedgui.api.lifecycle.LifecycleListenerRegistry;
import me.supcheg.advancedgui.api.util.CollectionUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

record AnvilLayoutTemplateImpl(
        @NotNull Queue<InputUpdateListener> inputUpdateListeners,
        @NotNull Set<ButtonTemplate> buttons,
        @NotNull LifecycleListenerRegistry<AnvilLayout> lifecycleListenerRegistry
) implements AnvilLayoutTemplate {
    @NotNull
    @Override
    public Builder toBuilder() {
        return new BuilderImpl(this);
    }

    static class BuilderImpl implements Builder {
        private final Queue<InputUpdateListener> inputUpdateListeners;
        private final Set<ButtonTemplate> buttons;
        private LifecycleListenerRegistry<AnvilLayout> lifecycleListenerRegistry;

        BuilderImpl(@NotNull AnvilLayoutTemplateImpl impl) {
            this.inputUpdateListeners = new PriorityQueue<>(impl.inputUpdateListeners);
            this.buttons = new HashSet<>(impl.buttons);
            this.lifecycleListenerRegistry = impl.lifecycleListenerRegistry;
        }

        BuilderImpl() {
            this.inputUpdateListeners = new PriorityQueue<>();
            this.buttons = new HashSet<>();
        }

        @NotNull
        @Override
        public Builder addInputUpdateListener(@NotNull InputUpdateListener inputUpdateListener) {
            Objects.requireNonNull(inputUpdateListener, "inputUpdate");
            this.inputUpdateListeners.add(inputUpdateListener);
            return this;
        }

        @NotNull
        @Override
        public Builder inputUpdateListeners(@NotNull Queue<InputUpdateListener> inputUpdateListeners) {
            Objects.requireNonNull(inputUpdateListeners, "inputUpdateListeners");
            this.inputUpdateListeners.clear();
            this.inputUpdateListeners.addAll(inputUpdateListeners);
            return this;
        }

        @NotNull
        @Override
        public Queue<InputUpdateListener> inputUpdateListeners() {
            return inputUpdateListeners;
        }

        @NotNull
        @Override
        public Builder addButton(@NotNull ButtonTemplate button) {
            Objects.requireNonNull(button, "button");
            this.buttons.add(button);
            return this;
        }

        @NotNull
        @Override
        public Builder buttons(@NotNull Set<ButtonTemplate> buttons) {
            Objects.requireNonNull(buttons, "buttons");
            this.buttons.clear();
            this.buttons.addAll(buttons);
            return this;
        }

        @NotNull
        @Override
        public Set<ButtonTemplate> buttons() {
            return buttons;
        }

        @Nullable
        @Override
        public LifecycleListenerRegistry<AnvilLayout> lifecycleListenerRegistry() {
            return lifecycleListenerRegistry;
        }

        @NotNull
        @Override
        public Builder lifecycleListenerRegistry(@NotNull LifecycleListenerRegistry<AnvilLayout> lifecycleListenerRegistry) {
            Objects.requireNonNull(lifecycleListenerRegistry, "lifecycleListenerRegistry");
            this.lifecycleListenerRegistry = lifecycleListenerRegistry;
            return this;
        }

        @NotNull
        @Override
        public AnvilLayoutTemplate build() {
            return new AnvilLayoutTemplateImpl(
                    CollectionUtil.copyOf(inputUpdateListeners),
                    Set.copyOf(buttons),
                    Objects.requireNonNull(lifecycleListenerRegistry, "lifecycleListenerRegistry")
            );
        }
    }
}
