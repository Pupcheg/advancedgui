package me.supcheg.advancedgui.api.loader.configurate.button;

import me.supcheg.advancedgui.api.button.attribute.ButtonAttribute;
import net.kyori.adventure.key.Key;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;

import static me.supcheg.advancedgui.api.button.attribute.ButtonAttribute.buttonAttribute;

public class ButtonAttributeTypeSerializer implements TypeSerializer<ButtonAttribute> {
    @Override
    public ButtonAttribute deserialize(Type type, ConfigurationNode node) throws SerializationException {
        return buttonAttribute(node.require(Key.class));
    }

    @Override
    public void serialize(Type type, @Nullable ButtonAttribute obj, ConfigurationNode node) throws SerializationException {
        if (obj == null) {
            node.set(null);
            return;
        }
        node.set(obj.key());
    }
}
