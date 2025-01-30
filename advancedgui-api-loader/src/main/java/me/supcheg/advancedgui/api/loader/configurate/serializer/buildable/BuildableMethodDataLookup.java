package me.supcheg.advancedgui.api.loader.configurate.serializer.buildable;

import org.jetbrains.annotations.NotNull;

public interface BuildableMethodDataLookup {
    @NotNull
    MethodDataReport buildReport(@NotNull Class<?> clazz);
}
