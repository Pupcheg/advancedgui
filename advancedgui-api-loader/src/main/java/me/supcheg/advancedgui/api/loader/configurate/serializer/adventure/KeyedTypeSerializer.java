package me.supcheg.advancedgui.api.loader.configurate.serializer.adventure;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;

public interface KeyedTypeSerializer<T extends Keyed> extends TypeSerializer<T> {

    @Override
    default T deserialize(Type type, ConfigurationNode node) throws SerializationException {
        return constructFromKey(node.require(Key.class));
    }

    @NotNull
    T constructFromKey(@NotNull Key key);

    @Override
    default void serialize(Type type, @Nullable T obj, ConfigurationNode node) throws SerializationException {
        node.set(obj == null ? null : obj.key());
    }
}
