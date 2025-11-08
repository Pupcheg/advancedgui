package me.supcheg.advancedgui.api.layout.template;

import me.supcheg.advancedgui.api.button.template.ButtonTemplate;
import me.supcheg.advancedgui.api.layout.ChestLayout;
import me.supcheg.advancedgui.api.lifecycle.LifecycleListenerRegistry;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

record ChestLayoutTemplateImpl(
        int rows,
        Set<ButtonTemplate> buttons,
        LifecycleListenerRegistry<ChestLayout> lifecycleListenerRegistry
) implements ChestLayoutTemplate {
    @Override
    public Builder toBuilder() {
        return new BuilderImpl(this);
    }

    static class BuilderImpl implements Builder {
        private @Nullable Integer rows;
        private final Set<ButtonTemplate> buttons;
        private @Nullable LifecycleListenerRegistry<ChestLayout> lifecycleListenerRegistry;

        BuilderImpl(ChestLayoutTemplateImpl impl) {
            this.rows = impl.rows;
            this.buttons = new HashSet<>(impl.buttons);
            this.lifecycleListenerRegistry = impl.lifecycleListenerRegistry;
        }

        BuilderImpl() {
            this.buttons = new HashSet<>();
        }

        @Override
        public Builder rows(int rows) {
            this.rows = rows;
            return this;
        }

        @Nullable
        @Override
        public Integer rows() {
            return rows;
        }

        @Override
        public Builder addButton(ButtonTemplate button) {
            Objects.requireNonNull(button, "button");
            buttons.add(button);
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

        @Nullable
        @Override
        public LifecycleListenerRegistry<ChestLayout> lifecycleListenerRegistry() {
            return lifecycleListenerRegistry;
        }

        @Override
        public Builder lifecycleListenerRegistry(LifecycleListenerRegistry<ChestLayout> lifecycleListenerRegistry) {
            Objects.requireNonNull(lifecycleListenerRegistry, "lifecycleListenerRegistry");
            this.lifecycleListenerRegistry = lifecycleListenerRegistry;
            return this;
        }

        @Override
        public ChestLayoutTemplate build() {
            return new ChestLayoutTemplateImpl(
                    Objects.requireNonNull(rows, "rows"),
                    Set.copyOf(buttons),
                    Objects.requireNonNull(lifecycleListenerRegistry, "lifecycleListenerRegistry")
            );
        }
    }
}
