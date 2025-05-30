package me.supcheg.advancedgui.api.loader.configurate.serializer.layout;

import me.supcheg.advancedgui.api.layout.template.AnvilLayoutTemplate;
import me.supcheg.advancedgui.api.layout.template.LayoutTemplate;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import static io.leangen.geantyref.GenericTypeReflector.erase;

public final class LayoutTemplateTypeSerializer implements TypeSerializer<LayoutTemplate<?, ?, ?>> {
    private static final String TYPE = "type";

    private final Map<String, Type> key2type = new HashMap<>();

    public static boolean isExactLayoutTemplate(Type type) {
        return erase(type) == LayoutTemplate.class;
    }

    public LayoutTemplateTypeSerializer() {
        key2type.put("anvil", AnvilLayoutTemplate.class);
    }

    @Override
    public LayoutTemplate<?, ?, ?> deserialize(Type type, ConfigurationNode node) throws SerializationException {
       @Nullable String nodeType = node.node(TYPE).getString();
        if (nodeType == null) {
            throw new SerializationException("Node type is required");
        }

        Type exactType = key2type.get(nodeType);
        if (exactType == null) {
            throw new SerializationException("Node type '" + nodeType + "' does not exist");
        }

        return (LayoutTemplate<?, ?, ?>) node.require(exactType);
    }

    @Override
    public void serialize(Type type, @Nullable LayoutTemplate<?, ?, ?> obj, ConfigurationNode node) throws SerializationException {
        if (obj == null) {
            node.set(null);
            return;
        }

        var objType = obj.getClass();

        var nodeType = findTypeEntry(objType);
        node.node(TYPE).set(nodeType.getKey());
        node.set(nodeType.getValue(), obj);
    }

    private Map.Entry<String, Type> findTypeEntry(Type requestedType) throws SerializationException {
        for (Map.Entry<String, Type> entry : key2type.entrySet()) {
            Type type = entry.getValue();

            if (erase(type).isAssignableFrom(erase(requestedType))) {
                return entry;
            }
        }
        throw new SerializationException("Unsupported node type: " + requestedType);
    }
}
