package me.supcheg.advancedgui.fabric.interpret;

import me.supcheg.advancedgui.api.loader.interpret.ActionInterpretContext;
import org.checkerframework.checker.nullness.qual.Nullable;

record AnyActionInterpretContext(
        @Nullable Object value
) implements ActionInterpretContext {
}
