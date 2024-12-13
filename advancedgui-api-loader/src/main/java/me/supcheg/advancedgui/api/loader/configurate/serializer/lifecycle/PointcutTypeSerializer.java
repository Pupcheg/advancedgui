package me.supcheg.advancedgui.api.loader.configurate.serializer.lifecycle;

import me.supcheg.advancedgui.api.key.AdvancedGuiKeys;
import me.supcheg.advancedgui.api.lifecycle.Pointcut;
import org.spongepowered.configurate.serialize.ScalarSerializer;
import org.spongepowered.configurate.serialize.SerializationException;

import java.lang.reflect.Type;
import java.util.function.Predicate;

public final class PointcutTypeSerializer extends ScalarSerializer<Pointcut> {
    public PointcutTypeSerializer() {
        super(Pointcut.class);
    }

    @Override
    public Pointcut deserialize(Type type, Object obj) throws SerializationException {
        return Pointcut.newPointcut(AdvancedGuiKeys.advancedguiKey(String.valueOf(obj)));
    }

    @Override
    protected Object serialize(Pointcut item, Predicate<Class<?>> typeSupported) {
        return item.key().asString();
    }
}
