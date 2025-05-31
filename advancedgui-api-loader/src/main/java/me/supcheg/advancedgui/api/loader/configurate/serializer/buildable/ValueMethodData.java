package me.supcheg.advancedgui.api.loader.configurate.serializer.buildable;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Type;

public record ValueMethodData(
        String name,
        Type type,
        MethodHandle setter,
        MethodHandle getter
) {
}
