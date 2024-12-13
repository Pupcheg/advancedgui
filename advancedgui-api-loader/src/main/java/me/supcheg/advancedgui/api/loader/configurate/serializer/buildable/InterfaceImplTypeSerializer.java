package me.supcheg.advancedgui.api.loader.configurate.serializer.buildable;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;

@RequiredArgsConstructor
public final class InterfaceImplTypeSerializer implements TypeSerializer<Object> {
    private final TypeSerializer<Object> delegate;
    private final InterfaceImplLookup implLookup;

    @Override
    public Object deserialize(Type type, ConfigurationNode node) throws SerializationException {
        return delegate.deserialize(implLookup.findImpl(type), node);
    }

    @Override
    public void serialize(Type type, @Nullable Object obj, ConfigurationNode node) throws SerializationException {
        delegate.serialize(type, obj, node);
    }
}
