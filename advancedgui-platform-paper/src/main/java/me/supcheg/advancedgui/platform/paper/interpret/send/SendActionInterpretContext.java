package me.supcheg.advancedgui.platform.paper.interpret.send;

import me.supcheg.advancedgui.api.loader.interpret.ActionInterpretContext;
import net.kyori.adventure.text.Component;

record SendActionInterpretContext(
        Component content,
        SendActionTarget target
) implements ActionInterpretContext {
}
