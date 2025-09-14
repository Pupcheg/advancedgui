package me.supcheg.advancedgui.platform.paper.interpret.close;

import me.supcheg.advancedgui.api.loader.interpret.ActionInterpretContext;

record CloseActionInterpretContext(
        CloseErrorStrategy errorStrategy
) implements ActionInterpretContext {

    static final CloseActionInterpretContext DEFAULT =
            new CloseActionInterpretContext(
                    CloseErrorStrategy.LOG
            );
}
