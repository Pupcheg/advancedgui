package me.supcheg.advancedgui.platform.paper.interpret.close;

import lombok.extern.slf4j.Slf4j;
import me.supcheg.advancedgui.api.action.AudienceActionContext;
import me.supcheg.advancedgui.api.loader.interpret.GenericActionInterpreter;
import org.jetbrains.annotations.NotNull;

@Slf4j
final class CloseActionInterpreter implements GenericActionInterpreter<CloseActionInterpretContext> {
    private final GenericAction action = ctx -> ((AudienceActionContext) ctx).audience().close();

    @NotNull
    @Override
    public GenericAction interpretGenericAction(@NotNull CloseActionInterpretContext ctx) {
        return action;
    }
}
