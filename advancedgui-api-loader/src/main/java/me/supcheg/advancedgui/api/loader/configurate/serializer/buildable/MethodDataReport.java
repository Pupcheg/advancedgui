package me.supcheg.advancedgui.api.loader.configurate.serializer.buildable;

import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandle;
import java.util.List;

public record MethodDataReport(
        @NotNull MethodHandle builderFactory,
        @NotNull List<ValueMethodData> values
) {
}
