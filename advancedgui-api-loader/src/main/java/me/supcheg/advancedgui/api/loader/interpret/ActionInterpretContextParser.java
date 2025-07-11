package me.supcheg.advancedgui.api.loader.interpret;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

public interface ActionInterpretContextParser<C extends ActionInterpretContext> {

    String TYPE_KEY = "type";

    static @Nullable String parseType(ConfigurationNode node) {
        @Nullable String scalar = node.getString();

        return scalar != null ? scalar
                : node.node(TYPE_KEY).getString();
    }

    boolean isAcceptable(ConfigurationNode node);

    C deserialize(ConfigurationNode node) throws SerializationException;

    void serialize(C ctx, ConfigurationNode node) throws SerializationException;
}
