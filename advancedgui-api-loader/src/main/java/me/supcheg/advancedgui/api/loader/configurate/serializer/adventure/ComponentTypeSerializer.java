package me.supcheg.advancedgui.api.loader.configurate.serializer.adventure;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.ComponentSerializer;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.serialize.ScalarSerializer;

import java.lang.reflect.Type;
import java.util.Objects;
import java.util.function.Predicate;

public final class ComponentTypeSerializer extends ScalarSerializer<Component> {
    private final ComponentSerializer<Component, ?, String> stringComponentSerializer;

    public ComponentTypeSerializer(@NotNull ComponentSerializer<Component, ?, String> stringComponentSerializer) {
        super(Component.class);
        this.stringComponentSerializer = Objects.requireNonNull(stringComponentSerializer);
    }

    @Override
    public Component deserialize(Type type, Object obj) {
        return stringComponentSerializer.deserialize(String.valueOf(obj));
    }

    @Override
    protected Object serialize(Component item, Predicate<Class<?>> typeSupported) {
        return stringComponentSerializer.serialize(item);
    }
}
