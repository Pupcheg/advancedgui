package me.supcheg.advancedgui.api.loader.configurate;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationOptions;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConfigurateUtil {
    @SuppressWarnings("unchecked")
    public static <T> TypeSerializer<T> findTypeSerializer(ConfigurationOptions options, Type type) {
        @Nullable
        TypeSerializer<?> serializer = options.serializers().get(type);
        return (TypeSerializer<T>) Objects.requireNonNull(serializer, () -> "No type serializer found for " + type);
    }
}
