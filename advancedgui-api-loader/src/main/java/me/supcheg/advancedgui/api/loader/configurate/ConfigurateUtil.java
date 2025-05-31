package me.supcheg.advancedgui.api.loader.configurate;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.spongepowered.configurate.ConfigurationOptions;

import java.lang.reflect.Type;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConfigurateUtil {
    public static <T> T findTypeSerializer(ConfigurationOptions options, Type type) {
        @SuppressWarnings("unchecked")
        T serializer = (T) options.serializers().get(type);
        return Objects.requireNonNull(serializer, () -> "No type serializer found for " + type);
    }
}
