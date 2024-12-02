package me.supcheg.advancedgui.api.loader.interpret;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

import java.lang.reflect.Type;

public interface ActionInterpretContextParser<C> {

    String TYPE_KEY = "type";

    @Nullable
    static String parseType(@NotNull final ConfigurationNode node) {
        return node.node(TYPE_KEY).getString();
    }

    boolean isAcceptable(@NotNull ConfigurationNode node);

    @NotNull
    C deserialize(@NotNull ConfigurationNode node) throws SerializationException;

    default void serialize(@NotNull Type type, @Nullable C obj, @NotNull ConfigurationNode node) throws SerializationException {
        node.set(type, obj);
    }
}
