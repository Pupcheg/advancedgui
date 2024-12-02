package me.supcheg.advancedgui.api.loader.configurate.serializer.sequence;

import me.supcheg.advancedgui.api.sequence.NamedPriority;
import me.supcheg.advancedgui.api.sequence.Priority;
import org.spongepowered.configurate.serialize.ScalarSerializer;
import org.spongepowered.configurate.serialize.SerializationException;

import java.lang.reflect.Type;
import java.util.function.Predicate;

public final class PriorityTypeSerializer extends ScalarSerializer<Priority> {
    public PriorityTypeSerializer() {
        super(Priority.class);
    }

    @Override
    public Priority deserialize(Type type, Object obj) throws SerializationException {
        if (obj instanceof String name) {
            return NamedPriority.namedPriority(name);
        }

        if (obj instanceof Integer value) {
            return Priority.priority(value);
        }

        throw new SerializationException(obj + "could not be deserialized");
    }

    @Override
    protected Object serialize(Priority item, Predicate<Class<?>> typeSupported) {
        throw new UnsupportedOperationException("PriorityTypeSerializer does not support serialization");
    }
}
