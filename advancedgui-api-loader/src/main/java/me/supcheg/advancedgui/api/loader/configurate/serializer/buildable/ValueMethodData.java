package me.supcheg.advancedgui.api.loader.configurate.serializer.buildable;

import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Type;

public record ValueMethodData(
        @NotNull String name,
        @NotNull Type type,
        @NotNull MethodHandle setter,
        @NotNull MethodHandle getter
) {
}
