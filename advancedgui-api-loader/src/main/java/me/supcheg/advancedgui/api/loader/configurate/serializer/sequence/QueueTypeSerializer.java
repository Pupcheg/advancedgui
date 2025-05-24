package me.supcheg.advancedgui.api.loader.configurate.serializer.sequence;

import me.supcheg.advancedgui.api.sequence.Sequenced;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.serialize.AbstractListChildSerializer;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.util.CheckedConsumer;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.PriorityQueue;
import java.util.Queue;

import static io.leangen.geantyref.GenericTypeReflector.erase;

public final class QueueTypeSerializer extends AbstractListChildSerializer<Queue<? extends Sequenced<?>>> {

    public static boolean isQueue(Type type) {
        if (!(type instanceof ParameterizedType parameterizedType)) {
            return false;
        }

        Type[] arguments = parameterizedType.getActualTypeArguments();
        return
                erase(type) == Queue.class
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
    protected Queue<? extends Sequenced<?>> createNew(int length, Type elementType) {
        return new PriorityQueue<>();
    }

    @Override
    protected void forEachElement(Queue<? extends Sequenced<?>> collection, CheckedConsumer<Object, SerializationException> action) throws SerializationException {
        for (Sequenced<?> sequenced : collection) {
            action.accept(sequenced);
        }
    }

    @Override
    protected void deserializeSingle(int index, Queue<? extends Sequenced<?>> collection, @Nullable Object deserialized) {
        if (deserialized == null) {
            return;
        }

        Sequenced<?> sequenced = (Sequenced<?>) deserialized;
        @SuppressWarnings("unchecked")
        Queue<Sequenced<?>> sequencedSet = ((Queue<Sequenced<?>>) collection);
        sequencedSet.add(sequenced);
    }
}
