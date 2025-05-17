package me.supcheg.advancedgui.api.loader.interpret;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

public interface ActionInterpretContextParser<C extends ActionInterpretContext> {

    String TYPE_KEY = "type";

    @Nullable
    static String parseType(@NotNull final ConfigurationNode node) {
        String scalar = node.getString();

        return scalar != null ? scalar
                : node.node(TYPE_KEY).getString();
    }

    boolean isAcceptable(@NotNull ConfigurationNode node);

    @NotNull
    C deserialize(@NotNull ConfigurationNode node) throws SerializationException;

    void serialize(@NotNull C ctx, @NotNull ConfigurationNode node) throws SerializationException;
}
