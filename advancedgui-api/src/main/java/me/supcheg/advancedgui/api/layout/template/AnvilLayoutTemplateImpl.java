package me.supcheg.advancedgui.api.layout.template;

import me.supcheg.advancedgui.api.button.template.ButtonTemplate;
import me.supcheg.advancedgui.api.layout.AnvilLayout;
import me.supcheg.advancedgui.api.layout.template.anvil.InputUpdateListener;
import me.supcheg.advancedgui.api.lifecycle.LifecycleListenerRegistry;
import me.supcheg.advancedgui.api.sequence.collection.SequencedSortedSet;
import me.supcheg.advancedgui.api.sequence.collection.SequencedSortedSets;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

record AnvilLayoutTemplateImpl(
        SequencedSortedSet<InputUpdateListener> inputUpdateListeners,
        Set<ButtonTemplate> buttons,
        LifecycleListenerRegistry<AnvilLayout> lifecycleListenerRegistry
) implements AnvilLayoutTemplate {
    @Override
    public Builder toBuilder() {
        return new BuilderImpl(this);
    }

    static class BuilderImpl implements Builder {
        private final SequencedSortedSet<InputUpdateListener> inputUpdateListeners;
        private final Set<ButtonTemplate> buttons;
        private @Nullable LifecycleListenerRegistry<AnvilLayout> lifecycleListenerRegistry;

        BuilderImpl(AnvilLayoutTemplateImpl impl) {
            this.inputUpdateListeners = SequencedSortedSets.createCopy(impl.inputUpdateListeners);
            this.buttons = new HashSet<>(impl.buttons);
            this.lifecycleListenerRegistry = impl.lifecycleListenerRegistry;
        }

        BuilderImpl() {
            this.inputUpdateListeners = SequencedSortedSets.create();
            this.buttons = new HashSet<>();
        }

        @Override
        public Builder addInputUpdateListener(InputUpdateListener inputUpdateListener) {
            Objects.requireNonNull(inputUpdateListener, "inputUpdate");
            this.inputUpdateListeners.add(inputUpdateListener);
            return this;
        }

        @Override
        public Builder inputUpdateListeners(SequencedSortedSet<InputUpdateListener> inputUpdateListeners) {
            Objects.requireNonNull(inputUpdateListeners, "inputUpdateListeners");
            this.inputUpdateListeners.clear();
            this.inputUpdateListeners.addAll(inputUpdateListeners);
            return this;
        }

        @Override
        public SequencedSortedSet<InputUpdateListener> inputUpdateListeners() {
            return inputUpdateListeners;
        }

        @Override
        public Builder addButton(ButtonTemplate button) {
            Objects.requireNonNull(button, "button");
            this.buttons.add(button);
            return this;
        }

        @Override
        public Builder buttons(Set<ButtonTemplate> buttons) {
            Objects.requireNonNull(buttons, "buttons");
            this.buttons.clear();
            this.buttons.addAll(buttons);
            return this;
        }

        @Override
        public Set<ButtonTemplate> buttons() {
            return buttons;
        }

        @Override
        @Nullable
        public LifecycleListenerRegistry<AnvilLayout> lifecycleListenerRegistry() {
            return lifecycleListenerRegistry;
        }

        @Override
        public Builder lifecycleListenerRegistry(LifecycleListenerRegistry<AnvilLayout> lifecycleListenerRegistry) {
            Objects.requireNonNull(lifecycleListenerRegistry, "lifecycleListenerRegistry");
            this.lifecycleListenerRegistry = lifecycleListenerRegistry;
            return this;
        }

        @Override
        public AnvilLayoutTemplate build() {
            return new AnvilLayoutTemplateImpl(
                    SequencedSortedSets.copyOf(inputUpdateListeners),
                    Set.copyOf(buttons),
                    Objects.requireNonNull(lifecycleListenerRegistry, "lifecycleListenerRegistry")
            );
        }
    }
}
