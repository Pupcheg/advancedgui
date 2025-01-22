package me.supcheg.advancedgui.api.loader.configurate;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurationOptions;

import java.lang.reflect.Type;
import java.util.Objects;

import static me.supcheg.advancedgui.api.loader.configurate.serializer.unchecked.Unchecked.uncheckedCast;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConfigurateUtil {
    @NotNull
    public static <T> T findTypeSerializer(@NotNull ConfigurationOptions options, @NotNull Type type) {
        return Objects.requireNonNull(
                uncheckedCast(options.serializers().get(type)),
                () -> "No type serializer found for " + type
        );
    }
}
