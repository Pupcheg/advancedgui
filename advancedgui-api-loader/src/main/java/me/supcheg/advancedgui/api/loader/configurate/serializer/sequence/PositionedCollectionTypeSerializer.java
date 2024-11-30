package me.supcheg.advancedgui.api.loader.configurate.serializer.sequence;

import me.supcheg.advancedgui.api.sequence.collection.PositionedCollection;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import static io.leangen.geantyref.GenericTypeReflector.erase;
import static io.leangen.geantyref.TypeFactory.parameterizedClass;

public final class PositionedCollectionTypeSerializer implements TypeSerializer<PositionedCollection<?>> {

    public static boolean isPositionedCollection(Type type) {
        return PositionedCollection.class.isAssignableFrom(erase(type));
    }

    private static Type elementType(Type type) {
        if (type instanceof ParameterizedType parameterizedType) {
            return parameterizedType.getActualTypeArguments()[0];
        }
        return type;
    }

    private static Type positionedCollectionTypeToListType(Type type) {
        return parameterizedClass(List.class, elementType(type));
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public PositionedCollection<?> deserialize(Type type, ConfigurationNode node) throws SerializationException {
        Type positionedListType = positionedCollectionTypeToListType(type);

        Collection positionedList = (Collection) node.require(positionedListType);
        if (positionedList == null) {
            return null;
        }

        return PositionedCollection.copyOf(positionedList);
    }

    @Override
    public void serialize(Type type, @Nullable PositionedCollection<?> obj, ConfigurationNode node) throws SerializationException {
        node.set(
                positionedCollectionTypeToListType(type), obj == null ? null : obj.allElements()
        );
    }
}
