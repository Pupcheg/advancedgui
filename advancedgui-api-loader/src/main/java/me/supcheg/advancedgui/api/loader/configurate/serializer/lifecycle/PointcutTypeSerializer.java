package me.supcheg.advancedgui.api.loader.configurate.serializer.lifecycle;

import me.supcheg.advancedgui.api.lifecycle.Pointcut;
import net.kyori.adventure.key.Key;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;

public final class PointcutTypeSerializer implements TypeSerializer<Pointcut> {
    @Override
    public Pointcut deserialize(Type type, ConfigurationNode node) throws SerializationException {
        return Pointcut.newPointcut(node.require(Key.class));
    }

    @Override
    public void serialize(Type type, @Nullable Pointcut obj, ConfigurationNode node) throws SerializationException {
        if(obj == null) {
            node.set(null);
            return;
        }
        node.set(obj.key());
    }
}
