package me.supcheg.advancedgui.api.loader.interpret;

import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandle;

public interface ActionInterpreter<C extends ActionInterpretContext> {
    /**
     * Contract:
     * one Object argument
     * return type must be void
     */
    @NotNull
    MethodHandle interpretMethodHandle(@NotNull C ctx);
}
