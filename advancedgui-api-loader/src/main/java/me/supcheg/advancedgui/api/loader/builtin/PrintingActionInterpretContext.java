package me.supcheg.advancedgui.api.loader.builtin;

import me.supcheg.advancedgui.api.loader.interpret.ActionInterpretContext;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@ConfigSerializable
public record PrintingActionInterpretContext(
        @NotNull String message
) implements ActionInterpretContext {
}
