package me.supcheg.advancedgui.api.loader.configurate.serializer.adventure;

import net.kyori.adventure.key.InvalidKeyException;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.serialize.CoercionFailedException;
import org.spongepowered.configurate.serialize.ScalarSerializer;
import org.spongepowered.configurate.serialize.SerializationException;

import java.lang.reflect.Type;
import java.util.Objects;
import java.util.function.Predicate;

public final class CustomNamespaceKeyTypeSerializer extends ScalarSerializer<Key> {
    private final String namespace;

    public CustomNamespaceKeyTypeSerializer(@NotNull String namespace) {
        super(Key.class);
        Objects.requireNonNull(namespace);
        this.namespace = namespace;
    }

    @Override
    public Key deserialize(Type type, Object obj) throws SerializationException {
        if (!(obj instanceof CharSequence)) {
            throw new CoercionFailedException(obj, "string");
        }

        try {
            return parseKey(String.valueOf(obj));
        } catch (InvalidKeyException ex) {
            throw new SerializationException(ex);
        }
    }

    @SuppressWarnings("PatternValidation")
    private Key parseKey(String raw) {
        int index = raw.indexOf(Key.DEFAULT_SEPARATOR);
        String namespace = index >= 1 ? raw.substring(0, index) : this.namespace;
        String value = index >= 0 ? raw.substring(index + 1) : raw;
        return Key.key(namespace, value);
    }

    @Override
    protected Object serialize(Key item, Predicate<Class<?>> typeSupported) {
        return minimize(item);
    }

    private String minimize(Key key) {
        return namespace.equals(key.namespace()) ?
                key.value() :
                key.asString();
    }

}
