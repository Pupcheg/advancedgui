package me.supcheg.advancedgui.api.loader.configurate.serializer.sequence;

import com.google.common.collect.SortedMultiset;
import com.google.common.collect.TreeMultiset;
import me.supcheg.advancedgui.api.sequence.Sequenced;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.serialize.AbstractListChildSerializer;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.util.CheckedConsumer;

import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import static io.leangen.geantyref.GenericTypeReflector.erase;

public final class SortedMultisetTypeSerializer extends AbstractListChildSerializer<SortedMultiset<? extends Sequenced<?>>> {

    public static boolean isSortedMultiset(Type type) {
        if (!(type instanceof ParameterizedType parameterizedType)) {
            return false;
        }

        Type[] arguments = parameterizedType.getActualTypeArguments();
        return
                erase(type) == SortedMultiset.class
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
    protected SortedMultiset<? extends Sequenced<?>> createNew(int length, AnnotatedType elementType) {
        return TreeMultiset.create();
    }

    @Override
    protected void forEachElement(SortedMultiset<? extends Sequenced<?>> collection, CheckedConsumer<Object, SerializationException> action) throws SerializationException {
        for (Sequenced<?> sequenced : collection) {
            action.accept(sequenced);
        }
    }

    @Override
    protected void deserializeSingle(int index, SortedMultiset<? extends Sequenced<?>> collection, @Nullable Object deserialized) {
        if (deserialized == null) {
            return;
        }

        Sequenced<?> sequenced = (Sequenced<?>) deserialized;
        @SuppressWarnings("unchecked")
        SortedMultiset<Sequenced<?>> sequencedSet = ((SortedMultiset<Sequenced<?>>) collection);
        sequencedSet.add(sequenced);
    }
}
