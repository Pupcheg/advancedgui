package me.supcheg.advancedgui.api.texture.source;

import me.supcheg.advancedgui.api.builder.AbstractBuilder;
import me.supcheg.advancedgui.api.texture.GuiTexture;
import me.supcheg.advancedgui.api.texture.ItemTexture;
import me.supcheg.advancedgui.api.builder.Buildable;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public interface TextureSource<T extends TextureSource<T, B>, B extends TextureSource.Builder<T, B>> extends Buildable<T, B> {
    @NotNull
    Stream<ItemTexture> paperItemTextures();

    @NotNull
    Stream<GuiTexture> componentGuiTextures();

    interface Builder<T extends TextureSource<T, B>, B extends Builder<T, B>> extends AbstractBuilder<T> {
    }
}
