package me.supcheg.advancedgui.api.texture;

import me.supcheg.advancedgui.api.builder.AbstractBuilder;
import net.kyori.adventure.key.Key;
import me.supcheg.advancedgui.api.builder.Buildable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ItemTexture extends Buildable<ItemTexture, ItemTexture.Builder> {

    @NotNull
    @Contract("-> new")
    static Builder itemTexture() {
        return new ItemTextureImpl.BuilderImpl();
    }

    @NotNull
    Key location();

    @NotNull
    Key item();

    int customModelData();

    int height();

    int width();

    interface Builder extends AbstractBuilder<ItemTexture> {
        @NotNull
        @Contract("_ -> this")
        Builder location(@Nullable Key location);

        @Nullable
        Key location();

        @NotNull
        Builder item(@Nullable Key item);

        @Nullable
        Key item();

        @NotNull
        @Contract("_ -> this")
        Builder customModelData(@Nullable Integer customModelData);

        @Nullable
        Integer customModelData();

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
