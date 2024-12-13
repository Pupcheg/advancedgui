package me.supcheg.advancedgui.api.loader.configurate.serializer.lifecycle;

import io.leangen.geantyref.TypeFactory;
import me.supcheg.advancedgui.api.lifecycle.LifecycleAction;
import me.supcheg.advancedgui.api.lifecycle.LifecycleListener;
import me.supcheg.advancedgui.api.lifecycle.LifecycleListenerRegistry;
import me.supcheg.advancedgui.api.lifecycle.Pointcut;
import me.supcheg.advancedgui.api.sequence.Priority;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.ConfigurationOptions;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.serialize.ScalarSerializer;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

public class LifecycleListenerRegistryTypeSerializer implements TypeSerializer<LifecycleListenerRegistry<?>> {
    private final Type rawLifecycleListenerListType = TypeFactory.parameterizedClass(List.class, RawLifecycleListener.class);

    @Override
    public LifecycleListenerRegistry<?> deserialize(Type type, ConfigurationNode node) throws SerializationException {
        ScalarSerializer<Pointcut> pointcutTypeSerializer = getPointcutScalarSerializer(node.options());
        var rawLifecycleListenerListSerializer = getRawLifecycleListenerListSerializer(node.options());

        LifecycleListenerRegistry.Builder<Object> builder = LifecycleListenerRegistry.lifecycleListenerRegistry();
        for (var entry : node.childrenMap().entrySet()) {
            Object rawPointcut = entry.getKey();
            ConfigurationNode valueNode = entry.getValue();

            Pointcut pointcut = pointcutTypeSerializer.deserialize(Pointcut.class, rawPointcut);
            var rawList = rawLifecycleListenerListSerializer.deserialize(rawLifecycleListenerListType, valueNode);

            for (var raw : rawList) {
                builder.add(rawLifecycleListenerToLifecycleListener(pointcut, raw));
            }
        }

        return builder.build();
    }

    @NotNull
    private LifecycleListener<Object> rawLifecycleListenerToLifecycleListener(@NotNull Pointcut pointcut,
                                                                              @NotNull RawLifecycleListener raw) {
        return pointcut.lifecycleListener(lifecycleListener -> lifecycleListener
                .priority(raw.priority())
                .action(raw.action())
        );
    }

    @NotNull
    private ScalarSerializer<Pointcut> getPointcutScalarSerializer(@NotNull ConfigurationOptions options) {
        return (ScalarSerializer<Pointcut>)
                Objects.requireNonNull(
                        options.serializers().get(Pointcut.class),
                        "Not found type serializer for Pointcut"
                );
    }

    @SuppressWarnings("unchecked")
    @NotNull
    private TypeSerializer<List<RawLifecycleListener>> getRawLifecycleListenerListSerializer(@NotNull ConfigurationOptions options) {
        return (TypeSerializer<List<RawLifecycleListener>>) Objects.requireNonNull(
                options.serializers().get(rawLifecycleListenerListType),
                "Not found type serializer for Pointcut"
        );
    }

    @Override
    public void serialize(Type type, @Nullable LifecycleListenerRegistry<?> obj, ConfigurationNode node) throws SerializationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @ConfigSerializable
    record RawLifecycleListener(
            @NotNull Priority priority,
            @NotNull LifecycleAction<Object> action
    ) {
    }
}
