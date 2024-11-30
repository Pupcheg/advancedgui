package me.supcheg.advancedgui.api.texture;

import me.supcheg.advancedgui.api.texture.source.TextureSource;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.toUnmodifiableMap;

record TextureWrapperImpl(
        @NotNull Map<Key, ItemTexture> paperTextures,
        @NotNull Map<Key, GuiTexture> componentTextures
) implements TextureWrapper {

    @NotNull
    @Override
    public GuiTexture guiTexture(@NotNull Key location) {
        return Objects.requireNonNull(
                componentTextures.get(location),
                () -> "ComponentGuiTexture with location='%s' not found".formatted(location)
        );
    }

    @NotNull
    @Override
    public ItemTexture itemTexture(@NotNull Key location) {
        return Objects.requireNonNull(
                paperTextures.get(location),
                () -> "PaperItemTexture with location='%s' not found".formatted(location)
        );
    }

    @NotNull
    @Override
    public Stream<ItemTexture> paperItemTextures() {
        return paperTextures.values().stream();
    }

    @NotNull
    @Override
    public Stream<GuiTexture> componentGuiTextures() {
        return componentTextures.values().stream();
    }

    @NotNull
    @Override
    public TextureWrapper.Builder toBuilder() {
        return new BuilderImpl(this);
    }

    static final class BuilderImpl implements TextureWrapper.Builder {
        private final List<ItemTexture> paperTextures;
        private final List<GuiTexture> componentTextures;
        private final List<TextureSource<?, ?>> sources;

        BuilderImpl() {
            this.paperTextures = new ArrayList<>();
            this.componentTextures = new ArrayList<>();
            this.sources = new ArrayList<>();
        }

        BuilderImpl(@NotNull TextureWrapperImpl impl) {
            this.paperTextures = new ArrayList<>(impl.paperTextures.values());
            this.componentTextures = new ArrayList<>(impl.componentTextures.values());
            this.sources = new ArrayList<>();
        }

        @NotNull
        @Override
        public Builder addItemTexture(@NotNull ItemTexture texture) {
            Objects.requireNonNull(texture, "texture");
            paperTextures.add(texture);
            return this;
        }

        @NotNull
        @Override
        public Builder addGuiTexture(@NotNull GuiTexture texture) {
            Objects.requireNonNull(texture, "texture");
            componentTextures.add(texture);
            return this;
        }

        @NotNull
        @Override
        public Builder addSource(@NotNull TextureSource<?, ?> source) {
            Objects.requireNonNull(source, "source");
            sources.add(source);
            return this;
        }

        @NotNull
        @Override
        public TextureWrapper build() {
            Map<Key, ItemTexture> paperTextures = Stream.concat(
                    this.paperTextures.stream(),
                    this.sources.stream().flatMap(TextureSource::paperItemTextures)
            ).collect(toUnmodifiableMap(ItemTexture::location, identity()));

            Map<Key, GuiTexture> componentTextures = Stream.concat(
                    this.componentTextures.stream(),
                    this.sources.stream().flatMap(TextureSource::componentGuiTextures)
            ).collect(toUnmodifiableMap(GuiTexture::location, identity()));

            return new TextureWrapperImpl(paperTextures, componentTextures);
        }
    }
}
