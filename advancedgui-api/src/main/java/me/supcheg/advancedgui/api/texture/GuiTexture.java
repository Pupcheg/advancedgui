package me.supcheg.advancedgui.api.texture;

import me.supcheg.advancedgui.api.builder.AbstractBuilder;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import me.supcheg.advancedgui.api.builder.Buildable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface GuiTexture extends Buildable<GuiTexture, GuiTexture.Builder> {
    @NotNull
    @Contract(" -> new")
    static Builder guiTexture() {
        return new GuiTextureImpl.BuilderImpl();
    }

    @NotNull
    Key location();

    @NotNull
    Component component();

    int height();

    int width();

    interface Builder extends AbstractBuilder<GuiTexture> {

        @NotNull
        @Contract("_ -> this")
        Builder location(@Nullable Key location);

        @Nullable
        Key location();

        @NotNull
        @Contract("_ -> this")
        Builder component(@Nullable Component component);

        @Nullable
        Component component();

        @NotNull
        @Contract("_ -> this")
        Builder height(@Nullable Integer height);

        @Nullable
        Integer height();

        @NotNull
        @Contract("_ -> this")
        Builder width(@Nullable Integer width);

        @Nullable
        Integer width();
    }
}
