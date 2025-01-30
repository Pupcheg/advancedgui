package me.supcheg.advancedgui.api.loader.configurate.serializer.coordinate;

import me.supcheg.advancedgui.api.coordinate.Coordinate;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;
import java.util.List;

public final class CoordinateTypeSerializer implements TypeSerializer<Coordinate> {
    @Override
    public Coordinate deserialize(Type type, ConfigurationNode node) throws SerializationException {
        int x;
        int y;

        if (node.isList()) {
            List<? extends ConfigurationNode> children = node.childrenList();
            x = children.get(0).getInt();
            y = children.get(1).getInt();
        } else if (node.isMap()) {
            x = node.node("x").getInt();
            y = node.node("y").getInt();
        } else {
            throw new SerializationException("Invalid type for coordinate");
        }

        return Coordinate.coordinate(x, y);
    }

    @Override
    public void serialize(Type type, @Nullable Coordinate obj, ConfigurationNode node) throws SerializationException {
        if (obj == null) {
            node.set(null);
            return;
        }
        node.setList(Integer.class, List.of(obj.x(), obj.y()));
    }
}
