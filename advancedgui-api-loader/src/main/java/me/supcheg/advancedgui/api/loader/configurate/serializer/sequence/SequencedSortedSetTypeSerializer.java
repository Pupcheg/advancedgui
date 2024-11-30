package me.supcheg.advancedgui.api.loader.configurate.serializer.sequence;

import me.supcheg.advancedgui.api.sequence.Sequenced;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.serialize.AbstractListChildSerializer;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.util.CheckedConsumer;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.SortedSet;
import java.util.TreeSet;

import static io.leangen.geantyref.GenericTypeReflector.erase;

public final class SequencedSortedSetTypeSerializer extends AbstractListChildSerializer<SortedSet<? extends Sequenced<?>>> {

    public static boolean isSequencedSortedSet(Type type) {
        if (!(type instanceof ParameterizedType parameterizedType)) {
            return false;
        }

        Type[] arguments = parameterizedType.getActualTypeArguments();
        return
                erase(type) == SortedSet.class
                && Sequenced.class.isAssignableFrom(erase(arguments[0]));
    }

    @Override
    protected Type elementType(Type containerType) throws SerializationException {
        if (containerType instanceof ParameterizedType parameterizedType) {
            return parameterizedType.getActualTypeArguments()[0];
        }

        throw new SerializationException(containerType, "Raw types are not supported for collections");
    }

    @Override
    protected SortedSet<? extends Sequenced<?>> createNew(int length, Type elementType) {
        return new TreeSet<>();
    }

    @Override
    protected void forEachElement(SortedSet<? extends Sequenced<?>> collection, CheckedConsumer<Object, SerializationException> action) throws SerializationException {
        for (Sequenced<?> sequenced : collection) {
            action.accept(sequenced);
        }
    }

    @Override
    protected void deserializeSingle(int index, SortedSet<? extends Sequenced<?>> collection, @Nullable Object deserialized) {
        if (deserialized == null) {
            return;
        }

        Sequenced<?> sequenced = (Sequenced<?>) deserialized;
        @SuppressWarnings("unchecked")
        SortedSet<Sequenced<?>> sequencedSet = ((SortedSet<Sequenced<?>>) collection);
        sequencedSet.add(sequenced);
    }
}
