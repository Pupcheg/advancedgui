package me.supcheg.advancedgui.api.loader.configurate.serializer.buildable;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import me.supcheg.advancedgui.api.builder.AbstractBuilder;
import me.supcheg.advancedgui.api.builder.Buildable;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.ConfigurationOptions;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;
import java.util.NoSuchElementException;

import static io.leangen.geantyref.GenericTypeReflector.erase;

@SuppressWarnings("rawtypes")
@RequiredArgsConstructor
public final class BuildableInterfaceTypeSerializer implements TypeSerializer<Buildable> {
    private final BuildableMethodDataLookup methodDataLookup;

    @SneakyThrows
    @Override
    public Buildable deserialize(Type type, ConfigurationNode node) throws SerializationException {
        MethodDataReport report = methodDataLookup.buildReport(erase(type));

        AbstractBuilder builder = (AbstractBuilder) report.builderFactory().invoke();

        for (ValueMethodData valueMethodData : report.values()) {
            Object value;
            try {
                value = node.node(valueMethodData.name())
                        .require(valueMethodData.type());
            } catch (NoSuchElementException ex) {
                throw new SerializationException(
                        node,
                        valueMethodData.type(),
                        "Property %s not found".formatted(valueMethodData.name()), ex
                );
            }
            valueMethodData.setter()
                    .invokeExact((Object) builder, (Object) value);
        }

        return (Buildable) builder.build();
    }

    @SneakyThrows
    @Override
    public Buildable emptyValue(Type specificType, ConfigurationOptions options) {
        MethodDataReport report = methodDataLookup.buildReport(erase(specificType));

        AbstractBuilder builder = (AbstractBuilder) report.builderFactory().invoke();
        return (Buildable) builder.build();
    }

    @SneakyThrows
    @Override
    public void serialize(Type type, @Nullable Buildable obj, ConfigurationNode node) throws SerializationException {
        if (obj == null) {
            node.set(null);
            return;
        }
        var objBuilder = obj.toBuilder();

        MethodDataReport report = methodDataLookup.buildReport(erase(type));

        for (ValueMethodData value : report.values()) {
            node.node(value.name())
                    .set(value.type(), (Object) value.getter().invokeExact((Object) objBuilder));
        }
    }
}
