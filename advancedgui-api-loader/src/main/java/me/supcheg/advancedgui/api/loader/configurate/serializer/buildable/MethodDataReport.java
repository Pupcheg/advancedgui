package me.supcheg.advancedgui.api.loader.configurate.serializer.buildable;

import java.lang.invoke.MethodHandle;
import java.util.List;

public record MethodDataReport(
        MethodHandle builderFactory,
        List<ValueMethodData> values
) {
}
