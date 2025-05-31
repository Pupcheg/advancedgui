package me.supcheg.advancedgui.api.gui.template;

import lombok.NoArgsConstructor;
import me.supcheg.advancedgui.api.gui.Gui;
import me.supcheg.advancedgui.api.gui.background.Background;
import me.supcheg.advancedgui.api.layout.template.LayoutTemplate;
import me.supcheg.advancedgui.api.lifecycle.LifecycleListenerRegistry;
import net.kyori.adventure.key.Key;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Objects;

record GuiTemplateImpl(
        Key key,
        LifecycleListenerRegistry<Gui> lifecycleListenerRegistry,
        LayoutTemplate<?, ?, ?> layout,
        Background background
) implements GuiTemplate {
    @Override
    public Builder toBuilder() {
        return new BuilderImpl(this);
    }

    @NoArgsConstructor
    static final class BuilderImpl implements GuiTemplate.Builder {
        private @Nullable Key key;
        private @Nullable LifecycleListenerRegistry<Gui> lifecycleListenerRegistry;
        private @Nullable LayoutTemplate<?, ?, ?> layout;
        private @Nullable Background background;

        BuilderImpl(GuiTemplateImpl impl) {
            this.key = impl.key;
            this.lifecycleListenerRegistry = impl.lifecycleListenerRegistry;
            this.layout = impl.layout;
            this.background = impl.background;
        }

        @Override
        public Builder key(Key key) {
            Objects.requireNonNull(key, "key");
            this.key = key;
            return this;
        }

        @Override
        @Nullable
        public Key key() {
            return key;
        }

        @Override
        public Builder layout(LayoutTemplate<?, ?, ?> layout) {
            Objects.requireNonNull(layout, "layout");
            this.layout = layout;
            return this;
        }

        @Override
        @Nullable
        public LayoutTemplate<?, ?, ?> layout() {
            return layout;
        }

        @Override
        @Nullable
        public LifecycleListenerRegistry<Gui> lifecycleListenerRegistry() {
            return lifecycleListenerRegistry;
        }

        @Override
        public Builder lifecycleListenerRegistry(LifecycleListenerRegistry<Gui> lifecycleListenerRegistry) {
            Objects.requireNonNull(lifecycleListenerRegistry, "lifecycleListenerRegistry");
            this.lifecycleListenerRegistry = lifecycleListenerRegistry;
            return this;
        }

        @Override
        public Builder background(Background background) {
            Objects.requireNonNull(background, "background");
            this.background = background;
            return this;
        }

        @Override
        @Nullable
        public Background background() {
            return background;
        }

        @Override
        public GuiTemplate build() {
            return new GuiTemplateImpl(
                    Objects.requireNonNull(key, "key"),
                    Objects.requireNonNull(lifecycleListenerRegistry, "lifecycleListenerRegistry"),
                    Objects.requireNonNull(layout, "layout"),
                    Objects.requireNonNull(background, "background")
            );
        }
    }
}
