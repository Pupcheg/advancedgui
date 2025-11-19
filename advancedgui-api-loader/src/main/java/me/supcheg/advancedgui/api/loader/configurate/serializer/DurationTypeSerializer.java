package me.supcheg.advancedgui.api.loader.configurate.serializer;

import net.kyori.adventure.util.Ticks;
import org.spongepowered.configurate.serialize.ScalarSerializer;
import org.spongepowered.configurate.serialize.SerializationException;

import java.lang.reflect.Type;
import java.time.Duration;
import java.util.function.Function;
import java.util.function.Predicate;

public final class DurationTypeSerializer extends ScalarSerializer<Duration> {
    public DurationTypeSerializer() {
        super(Duration.class);
    }

    @Override
    public Duration deserialize(Type type, Object obj) throws SerializationException {
        if (!(obj instanceof String value)) {
            throw new SerializationException(obj + "could not be deserialized");
        }

        value = value.trim();

        if (value.isEmpty()) {
            throw new SerializationException("Got an empty value");
        }

        var factory = factoryFor(value.charAt(value.length() - 1));

        var rawIntValue = value.substring(0, value.length() - 1);
        long longValue;
        try {
            longValue = Long.parseLong(rawIntValue);
        } catch (NumberFormatException ex) {
            throw new SerializationException(long.class, rawIntValue + " is not a number", ex);
        }

        return factory.apply(longValue);
    }

    private Function<Long, Duration> factoryFor(char letter) throws SerializationException {
        return switch (letter) {
            case 't' -> Ticks::duration;
            case 's' -> Duration::ofSeconds;
            case 'm' -> Duration::ofMinutes;
            case 'h' -> Duration::ofHours;
            case 'd' -> Duration::ofDays;
            default -> throw new SerializationException(letter + " is not a valid duration type");
        };
    }

    @Override
    protected String serialize(Duration item, Predicate<Class<?>> typeSupported) {
        return item.getSeconds() + "s";
    }
}
