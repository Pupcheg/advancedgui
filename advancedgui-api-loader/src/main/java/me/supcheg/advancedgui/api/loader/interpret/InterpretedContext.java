package me.supcheg.advancedgui.api.loader.interpret;

import java.lang.invoke.MethodHandle;

public record InterpretedContext(
        ActionInterpretContextParser<?> parser,
        ActionInterpretContext context,
        MethodHandle methodHandle
) {
}