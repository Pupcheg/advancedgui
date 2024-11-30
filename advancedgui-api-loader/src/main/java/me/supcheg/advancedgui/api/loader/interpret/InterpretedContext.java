package me.supcheg.advancedgui.api.loader.interpret;

import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandle;

public record InterpretedContext(
        @NotNull ActionInterpretContext context,
        @NotNull MethodHandle methodHandle
) {
}