package me.supcheg.advancedgui.api.loader.configurate.serializer.button.display;

import me.supcheg.advancedgui.api.button.display.ButtonDisplay;
import me.supcheg.advancedgui.api.button.display.ButtonDisplayProvider;
import me.supcheg.advancedgui.api.button.display.LoopedButtonDisplayProvider;
import me.supcheg.advancedgui.api.button.display.SingleButtonDisplayProvider;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;

import static io.leangen.geantyref.GenericTypeReflector.erase;

public final class ButtonDisplayProviderTypeSerializer implements TypeSerializer<ButtonDisplayProvider> {
    public static boolean isExactButtonDisplayProvider(Type type) {
        return erase(type) == ButtonDisplayProvider.class;
    }

    @Override
    public ButtonDisplayProvider deserialize(Type type, ConfigurationNode node) throws SerializationException {
        try {
            @Nullable ButtonDisplay single = node.get(ButtonDisplay.class);
            if (single != null) {
                return SingleButtonDisplayProvider.singleButtonDisplayProvider(single);
            }
        } catch (SerializationException ignored) {
            // this is not a SingleButtonDisplayProvider btw
        }

        return node.require(LoopedButtonDisplayProvider.class);
    }

    @Override
    public void serialize(Type type, @Nullable ButtonDisplayProvider obj, ConfigurationNode node) throws SerializationException {
        if (obj == null) {
            node.set(null);
            return;
        }

        if (obj instanceof SingleButtonDisplayProvider single) {
            node.set(single.display());
            return;
        }

        node.set(obj);
    }
}
