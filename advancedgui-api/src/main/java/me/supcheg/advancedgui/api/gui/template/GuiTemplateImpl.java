package me.supcheg.advancedgui.api.gui.template;

import lombok.NoArgsConstructor;
import me.supcheg.advancedgui.api.gui.Gui;
import me.supcheg.advancedgui.api.gui.background.Background;
import me.supcheg.advancedgui.api.layout.template.LayoutTemplate;
import me.supcheg.advancedgui.api.lifecycle.LifecycleListenerRegistry;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

record GuiTemplateImpl(
        @NotNull Key key,
        @NotNull LifecycleListenerRegistry<Gui> lifecycleListenerRegistry,
        @NotNull LayoutTemplate<?, ?, ?> layout,
        @NotNull Background background
) implements GuiTemplate {
    @NotNull
    @Override
    public Builder toBuilder() {
        return new BuilderImpl(this);
    }

    @NoArgsConstructor
    static final class BuilderImpl implements GuiTemplate.Builder {
        private Key key;
        private LifecycleListenerRegistry<Gui> lifecycleListenerRegistry;
        private LayoutTemplate<?, ?, ?> layout;
        private Background background;

        BuilderImpl(@NotNull GuiTemplateImpl impl) {
            this.key = impl.key;
            this.lifecycleListenerRegistry = impl.lifecycleListenerRegistry;
            this.layout = impl.layout;
            this.background = impl.background;
        }

        @NotNull
        @Override
        public Builder key(@NotNull Key key) {
            Objects.requireNonNull(key, "key");
            this.key = key;
            return this;
        }

        @Nullable
        @Override
        public Key key() {
            return key;
        }

        @NotNull
        @Override
        public Builder layout(@NotNull LayoutTemplate<?, ?, ?> layout) {
            Objects.requireNonNull(layout, "layout");
            this.layout = layout;
            return this;
        }

        @Nullable
        @Override
        public LayoutTemplate<?, ?, ?> layout() {
            return layout;
        }

        @Nullable
        @Override
        public LifecycleListenerRegistry<Gui> lifecycleListenerRegistry() {
            return lifecycleListenerRegistry;
        }

        @NotNull
        @Override
        public Builder lifecycleListenerRegistry(@NotNull LifecycleListenerRegistry<Gui> lifecycleListenerRegistry) {
            Objects.requireNonNull(lifecycleListenerRegistry, "lifecycleListenerRegistry");
            this.lifecycleListenerRegistry = lifecycleListenerRegistry;
            return this;
        }

        @NotNull
        @Override
        public Builder background(@NotNull Background background) {
            Objects.requireNonNull(background, "background");
            this.background = background;
            return this;
        }

        @Nullable
        @Override
        public Background background() {
            return background;
        }

        @NotNull
        @Override
        public GuiTemplate build() {
            return new GuiTemplateImpl(
                    key,
                    lifecycleListenerRegistry,
                    layout,
                    background
            );
        }
    }
}
