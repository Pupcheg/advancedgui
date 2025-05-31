package me.supcheg.advancedgui.fabric.interpret;

import me.supcheg.advancedgui.api.loader.interpret.ActionInterpretContext;
import org.jetbrains.annotations.Nullable;

record AnyActionInterpretContext(
        @Nullable Object value
) implements ActionInterpretContext {
}
