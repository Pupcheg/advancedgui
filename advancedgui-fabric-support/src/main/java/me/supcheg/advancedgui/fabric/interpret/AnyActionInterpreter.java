package me.supcheg.advancedgui.fabric.interpret;

import me.supcheg.advancedgui.api.action.ActionContext;
import me.supcheg.advancedgui.api.loader.interpret.ActionHandle;
import me.supcheg.advancedgui.api.loader.interpret.ActionInterpreter;

final class AnyActionInterpreter implements ActionInterpreter<AnyActionInterpretContext> {
    @Override
    public <AC extends ActionContext> ActionHandle<AC> interpretActionHandle(
            AnyActionInterpretContext interpretContext,
            Class<AC> targetActionContextType
    ) {
        return ctx -> {
            if (!targetActionContextType.isInstance(ctx)) {
                throw new IllegalArgumentException(
                        "Target action context is not an instance of " + targetActionContextType.getName()
                );
            }
        };
    }
}
