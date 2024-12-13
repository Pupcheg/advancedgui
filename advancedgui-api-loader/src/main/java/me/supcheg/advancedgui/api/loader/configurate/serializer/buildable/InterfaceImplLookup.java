package me.supcheg.advancedgui.api.loader.configurate.serializer.buildable;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;

public interface InterfaceImplLookup {
    @NotNull
    Type findImpl(@NotNull Type interfaceType);
}
