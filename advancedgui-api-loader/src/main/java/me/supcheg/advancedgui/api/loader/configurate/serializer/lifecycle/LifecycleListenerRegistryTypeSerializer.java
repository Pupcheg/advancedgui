package me.supcheg.advancedgui.api.loader.configurate.serializer.lifecycle;

import io.leangen.geantyref.TypeFactory;
import me.supcheg.advancedgui.api.lifecycle.LifecycleAction;
import me.supcheg.advancedgui.api.lifecycle.LifecycleListener;
import me.supcheg.advancedgui.api.lifecycle.LifecycleListenerRegistry;
import me.supcheg.advancedgui.api.lifecycle.pointcut.Pointcut;
import me.supcheg.advancedgui.api.sequence.Priority;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.ConfigurationOptions;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

import static me.supcheg.advancedgui.api.lifecycle.LifecycleListener.lifecycleListener;
import static me.supcheg.advancedgui.api.loader.configurate.ConfigurateUtil.findTypeSerializer;
import static org.spongepowered.configurate.BasicConfigurationNode.root;

public final class LifecycleListenerRegistryTypeSerializer implements TypeSerializer<LifecycleListenerRegistry<?>> {
    private final Type rawLifecycleListenerListType = TypeFactory.parameterizedClass(List.class, RawLifecycleListener.class);

    @Override
    public LifecycleListenerRegistry<?> deserialize(Type type, ConfigurationNode node) throws SerializationException {
        ConfigurationOptions options = node.options();

        TypeSerializer<Pointcut> pointcutTypeSerializer = findTypeSerializer(options, Pointcut.class);
        TypeSerializer<List<RawLifecycleListener>> rawLifecycleListenerListSerializer = findTypeSerializer(options, rawLifecycleListenerListType);

        LifecycleListenerRegistry.Builder<Object> builder = LifecycleListenerRegistry.lifecycleListenerRegistry();
        for (var entry : node.childrenMap().entrySet()) {
            ConfigurationNode keyNode = root(options).set(entry.getKey());
            ConfigurationNode valueNode = entry.getValue();

            Pointcut pointcut = pointcutTypeSerializer.deserialize(Pointcut.class, keyNode);
            var rawList = rawLifecycleListenerListSerializer.deserialize(rawLifecycleListenerListType, valueNode);

            for (var raw : rawList) {
                builder.add(rawLifecycleListenerToLifecycleListener(pointcut, raw));
            }
        }

        return builder.build();
    }

    @Override
    public LifecycleListenerRegistry<?> emptyValue(Type specificType, ConfigurationOptions options) {
        return LifecycleListenerRegistry.lifecycleListenerRegistry().build();
    }

    private static LifecycleListener<Object> rawLifecycleListenerToLifecycleListener(Pointcut pointcut,
                                                                                     RawLifecycleListener raw) {
        return lifecycleListener(lifecycleListener -> lifecycleListener
                .pointcut(pointcut)
                .priority(raw.priority())
                .action(raw.action())
        );
    }

    private static RawLifecycleListener lifecycleListenerToRawLifecycleListener(LifecycleListener<Object> lifecycleListener) {
        return new RawLifecycleListener(
                lifecycleListener.priority(),
                lifecycleListener.action()
        );
    }

    @Override
    public void serialize(Type type, @Nullable LifecycleListenerRegistry<?> obj, ConfigurationNode node) throws SerializationException {
        if (obj == null || obj.listeners().isEmpty()) {
            node.set(null);
            return;
        }

        @SuppressWarnings("unchecked") // safe cast
        LifecycleListenerRegistry<Object> objRegistry = (LifecycleListenerRegistry<Object>) obj;

        for (var entry : objRegistry.listeners().entrySet()) {
            Pointcut pointcut = entry.getKey();
            var listeners = entry.getValue();

            ConfigurationNode child = node.node(serializeAsString(node.options(), pointcut));

            for (LifecycleListener<Object> listener : listeners) {
                child.appendListNode()
                        .set(lifecycleListenerToRawLifecycleListener(listener));
            }
        }
    }

    private static String serializeAsString(ConfigurationOptions options,
                                            Pointcut pointcut) throws SerializationException {
        return (String) Objects.requireNonNull(
                root(options).set(pointcut).rawScalar(),
                () -> "Serialized %s to null".formatted(pointcut)
        );
    }

    @ConfigSerializable
    record RawLifecycleListener(
            Priority priority,
            LifecycleAction<Object> action
    ) {
    }
}
