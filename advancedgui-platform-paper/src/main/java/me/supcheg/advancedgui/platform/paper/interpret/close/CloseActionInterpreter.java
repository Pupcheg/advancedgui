package me.supcheg.advancedgui.platform.paper.interpret.close;

import me.supcheg.advancedgui.api.action.AudienceActionContext;
import me.supcheg.advancedgui.api.loader.interpret.ActionHandle;
import me.supcheg.advancedgui.api.loader.interpret.AudienceActionInterpreter;

final class CloseActionInterpreter implements AudienceActionInterpreter<CloseActionInterpretContext> {
    @Override
    public <AC extends AudienceActionContext> ActionHandle<AC> interpretAudienceActionHandle(
            CloseActionInterpretContext interpretContext,
            Class<AC> targetActionContextType
    ) {
        CloseErrorStrategy errorStrategy = interpretContext.errorStrategy();
        return ctx -> {
            try {
                ctx.audience().close();
            } catch (Exception exception) {
                errorStrategy.handle(exception);
            }
        };
    }
}
