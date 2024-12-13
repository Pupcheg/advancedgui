package me.supcheg.advancedgui.api.loader.configurate.serializer.adventure;

import me.supcheg.advancedgui.api.key.AdvancedGuiKeys;
import net.kyori.adventure.key.Key;
import org.spongepowered.configurate.serialize.ScalarSerializer;

import java.lang.reflect.Type;
import java.util.function.Predicate;

public final class KeyTypeSerializer extends ScalarSerializer<Key> {
    public KeyTypeSerializer() {
        super(Key.class);
    }

    @Override
    public Key deserialize(Type type, Object obj) {
        return AdvancedGuiKeys.advancedguiKey(String.valueOf(obj));
    }

    @Override
    protected Object serialize(Key item, Predicate<Class<?>> typeSupported) {
        return item.asMinimalString();
    }
}
