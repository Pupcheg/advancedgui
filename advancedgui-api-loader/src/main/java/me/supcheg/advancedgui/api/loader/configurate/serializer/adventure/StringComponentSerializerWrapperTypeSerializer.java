package me.supcheg.advancedgui.api.loader.configurate.serializer.adventure;

import net.kyori.adventure.key.InvalidKeyException;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.ComponentSerializer;
import org.spongepowered.configurate.serialize.CoercionFailedException;
import org.spongepowered.configurate.serialize.ScalarSerializer;
import org.spongepowered.configurate.serialize.SerializationException;

import java.lang.reflect.Type;
import java.util.function.Predicate;

public class StringComponentSerializerWrapperTypeSerializer extends ScalarSerializer<Component> {
    private final ComponentSerializer<Component, ?, String> componentSerializer;

    public StringComponentSerializerWrapperTypeSerializer(ComponentSerializer<Component, ?, String> componentSerializer) {
        super(Component.class);
        this.componentSerializer = componentSerializer;
    }

    @Override
    public Component deserialize(Type type, Object obj) throws SerializationException {
        if (!(obj instanceof CharSequence)) {
            throw new CoercionFailedException(obj, "string");
        }

        try {
            return componentSerializer.deserialize(String.valueOf(obj));
        } catch (InvalidKeyException ex) {
            throw new SerializationException(ex);
        }
    }

    @Override
    protected Object serialize(Component item, Predicate<Class<?>> typeSupported) {
        return componentSerializer.serialize(item);
    }
}
