package me.supcheg.advancedgui.platform.paper.interpret.send;

import me.supcheg.advancedgui.api.loader.interpret.ActionInterpretContext;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

record SendActionInterpretContext(
        @NotNull Component content,
        @NotNull SendActionTarget target
) implements ActionInterpretContext {
}
