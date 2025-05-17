package me.supcheg.advancedgui.api.loader.configurate.interpret;

import lombok.AccessLevel;
import lombok.Lombok;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import me.supcheg.advancedgui.api.loader.interpret.SimpleActionInterpretContextParser;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class SimpleActionInterpretContextParserReflection {
    private static final MethodHandle SimpleActionInterpretContextParser_getName;
    private static final MethodHandle SimpleActionInterpretContextParser_setName;

    static {
        try {
            Field field = SimpleActionInterpretContextParser.class.getDeclaredField("name");
            field.setAccessible(true);

            MethodHandles.Lookup lookup = MethodHandles.lookup();
            SimpleActionInterpretContextParser_getName = lookup.unreflectGetter(field);
            SimpleActionInterpretContextParser_setName = lookup.unreflectSetter(field);
        } catch (Exception e) {
            throw Lombok.sneakyThrow(e);
        }
    }

    @SneakyThrows
    public static void setNameIfNotPresent(SimpleActionInterpretContextParser<?> parser, String name) {
        String existing = (String) SimpleActionInterpretContextParser_getName.invokeExact(parser);
        if (existing == null) {
            SimpleActionInterpretContextParser_setName.invokeExact(parser, name);
        }
    }
}
