package me.supcheg.advancedgui.api.texture;

import lombok.NoArgsConstructor;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

record GuiTextureImpl(
        @NotNull Key location,
        @NotNull Component component,
        int height,
        int width
) implements GuiTexture {
    @NotNull
    @Override
    public GuiTexture.Builder toBuilder() {
        return new BuilderImpl(this);
    }

    @NoArgsConstructor
    static final class BuilderImpl implements GuiTexture.Builder {
        private Key location;
        private Component component;
        private Integer height;
        private Integer width;

        BuilderImpl(@NotNull GuiTextureImpl impl) {
            this.location = impl.location;
            this.component = impl.component;
            this.height = impl.height;
            this.width = impl.width;
        }

        @NotNull
        @Override
        public Builder location(@Nullable Key location) {
            this.location = location;
            return this;
        }

        @NotNull
        @Override
        public Builder component(@Nullable Component component) {
            this.component = component;
            return this;
        }

        @NotNull
        @Override
        public Builder height(@Nullable Integer height) {
            this.height = height;
            return this;
        }

        @NotNull
        @Override
        public Builder width(@Nullable Integer width) {
            this.width = width;
            return this;
        }

        @Nullable
        @Override
        public Key location() {
            return location;
        }

        @Nullable
        @Override
        public Component component() {
            return component;
        }

        @Nullable
        @Override
        public Integer height() {
            return height;
        }

        @Nullable
        @Override
        public Integer width() {
            return width;
        }

        @NotNull
        @Override
        public GuiTexture build() {
            Objects.requireNonNull(location, "path");
            Objects.requireNonNull(component, "component");
            Objects.requireNonNull(height, "height");
            Objects.requireNonNull(width, "width");
            return new GuiTextureImpl(location, component, height, width);
        }
    }
}
