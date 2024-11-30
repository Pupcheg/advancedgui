package me.supcheg.advancedgui.api.texture;

import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.api.texture.source.TextureSource;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface TextureWrapper extends TextureSource<TextureWrapper, TextureWrapper.Builder> {

    @NotNull
    @Contract("-> new")
    static Builder textureWrapper() {
        return new TextureWrapperImpl.BuilderImpl();
    }

    @NotNull
    @Contract("_ -> new")
    static TextureWrapper textureWrapper(@NotNull Consumer<Builder> consumer) {
        return Buildable.configureAndBuild(textureWrapper(), consumer);
    }

    @NotNull
    ItemTexture itemTexture(@NotNull Key location);

    @NotNull
    GuiTexture guiTexture(@NotNull Key location);

    interface Builder extends TextureSource.Builder<TextureWrapper, TextureWrapper.Builder> {

        @NotNull
        @Contract("_ -> this")
        default Builder addSource(@NotNull TextureSource.Builder<?, ?> source) {
            return addSource(source.build());
        }

        @NotNull
        @Contract("_ -> this")
        Builder addSource(@NotNull TextureSource<?, ?> source);

        @NotNull
        @Contract("_ -> this")
        default Builder addPaperItemTextures(@NotNull Iterable<ItemTexture> textures) {
            textures.forEach(this::addItemTexture);
            return this;
        }

        @NotNull
        @Contract("_ -> this")
        default Builder addItemTexture(@NotNull Consumer<ItemTexture.Builder> consumer) {
            ItemTexture.Builder builder = ItemTexture.itemTexture();
            consumer.accept(builder);
            return addItemTexture(builder);
        }

        @NotNull
        @Contract("_ -> this")
        default Builder addItemTexture(@NotNull ItemTexture.Builder texture) {
            return addItemTexture(texture.build());
        }

        @NotNull
        @Contract("_ -> this")
        Builder addItemTexture(@NotNull ItemTexture texture);

        @NotNull
        @Contract("_ -> this")
        default Builder addGuiTextures(@NotNull Iterable<GuiTexture> textures) {
            textures.forEach(this::addGuiTexture);
            return this;
        }

        @NotNull
        @Contract("_ -> this")
        default Builder addGuiTexture(@NotNull Consumer<GuiTexture.Builder> consumer) {
            GuiTexture.Builder builder = GuiTexture.guiTexture();
            consumer.accept(builder);
            return addGuiTexture(builder);
        }

        @NotNull
        @Contract("_ -> this")
        default Builder addGuiTexture(@NotNull GuiTexture.Builder texture) {
            return addGuiTexture(texture.build());
        }

        @NotNull
        @Contract("_ -> this")
        Builder addGuiTexture(@NotNull GuiTexture texture);
    }
}
