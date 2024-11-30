package me.supcheg.advancedgui.api.texture;

import lombok.NoArgsConstructor;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

record ItemTextureImpl(
        @NotNull Key location,
        @NotNull Key item,
        int customModelData,
        int height,
        int width
) implements ItemTexture {
    @NotNull
    @Override
    public Builder toBuilder() {
        return new BuilderImpl(this);
    }

    @NoArgsConstructor
    static final class BuilderImpl implements ItemTexture.Builder {
        private Key location;
        private Key item;
        private Integer customModelData;
        private Integer height;
        private Integer width;

        BuilderImpl(@NotNull ItemTextureImpl impl) {
            this.location = impl.location;
            this.customModelData = impl.customModelData;
            this.height = impl.height;
            this.width = impl.width;
        }

        @NotNull
        @Override
        public Builder location(@Nullable Key location) {
            this.location = location;
            return this;
        }

        @Nullable
        @Override
        public Key location() {
            return location;
        }

        @NotNull
        @Override
        public Builder item(@Nullable Key item) {
            this.item = item;
            return this;
        }

        @Nullable
        @Override
        public Key item() {
            return item;
        }

        @NotNull
        @Override
        public Builder customModelData(@Nullable Integer customModelData) {
            this.customModelData = customModelData;
            return this;
        }

        @Nullable
        @Override
        public Integer customModelData() {
            return customModelData;
        }

        @NotNull
        @Override
        public Builder height(@Nullable Integer height) {
            this.height = height;
            return this;
        }

        @Override
        public Integer height() {
            return height;
        }

        @NotNull
        @Override
        public Builder width(@Nullable Integer width) {
            this.width = width;
            return this;
        }

        @Nullable
        @Override
        public Integer width() {
            return width;
        }

        @NotNull
        @Override
        public ItemTexture build() {
            Objects.requireNonNull(location, "path");
            Objects.requireNonNull(item, "item");
            Objects.requireNonNull(customModelData, "customModelData");
            Objects.requireNonNull(height, "height");
            Objects.requireNonNull(width, "width");
            return new ItemTextureImpl(location, item, customModelData, height, width);
        }
    }
}
