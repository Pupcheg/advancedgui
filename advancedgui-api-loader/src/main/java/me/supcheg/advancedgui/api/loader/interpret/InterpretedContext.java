package me.supcheg.advancedgui.api.loader.interpret;

public record InterpretedContext(
        ActionInterpretContextParser<?> parser,
        ActionInterpretContext context,
        ActionHandle<?> actionHandle
) {
}