package me.supcheg.advancedgui.api.loader.configurate.serializer.sequence;

import me.supcheg.advancedgui.api.sequence.Sequenced;
import me.supcheg.advancedgui.api.sequence.collection.SequencedSortedSet;
import me.supcheg.advancedgui.api.sequence.collection.SequencedSortedSets;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.serialize.AbstractListChildSerializer;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.util.CheckedConsumer;

import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import static io.leangen.geantyref.GenericTypeReflector.erase;

public final class SequencedSetTypeSerializer extends AbstractListChildSerializer<SequencedSortedSet<?>> {

    public static boolean isSequencedSet(Type type) {
        if (!(type instanceof ParameterizedType parameterizedType)) {
            return false;
        }

        Type[] arguments = parameterizedType.getActualTypeArguments();
        return
                erase(type) == SequencedSortedSet.class
                && Sequenced.class.isAssignableFrom(erase(arguments[0]));
    }

    @Override
    protected AnnotatedType elementType(AnnotatedType containerType) throws SerializationException {
        if (containerType instanceof AnnotatedParameterizedType parameterizedType) {
            return parameterizedType.getAnnotatedActualTypeArguments()[0];
        }

        throw new SerializationException(containerType, "Raw types are not supported for collections");
    }

    @Override
    protected SequencedSortedSet<?> createNew(int length, AnnotatedType elementType) {
        return SequencedSortedSets.create();
    }

    @Override
    protected void forEachElement(SequencedSortedSet<?> collection, CheckedConsumer<Object, SerializationException> action) throws SerializationException {
        for (Sequenced<?> sequenced : collection) {
            action.accept(sequenced);
        }
    }

    @Override
    protected void deserializeSingle(int index, SequencedSortedSet collection, @Nullable Object deserialized) {
        if (deserialized != null) {
            collection.add(deserialized);
        }
    }
}
