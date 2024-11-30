package me.supcheg.advancedgui.api.loader.configurate.serializer.buildable;

import org.jetbrains.annotations.NotNull;

public interface InterfaceImplLookup {
    @NotNull
    Class<?> findImpl(@NotNull Class<?> interfaceClass);
}
