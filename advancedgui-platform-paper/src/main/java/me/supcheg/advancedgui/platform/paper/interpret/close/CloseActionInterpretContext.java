package me.supcheg.advancedgui.platform.paper.interpret.close;

import me.supcheg.advancedgui.api.loader.interpret.ActionInterpretContext;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@ConfigSerializable
record CloseActionInterpretContext(
        @NotNull CloseErrorStrategy errorStrategy
) implements ActionInterpretContext {

    static final CloseActionInterpretContext DEFAULT =
            new CloseActionInterpretContext(
                    CloseErrorStrategy.LOG
            );
}
