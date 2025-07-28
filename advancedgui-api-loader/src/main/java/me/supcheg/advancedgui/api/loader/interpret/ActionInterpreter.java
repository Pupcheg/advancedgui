package me.supcheg.advancedgui.api.loader.interpret;

import me.supcheg.advancedgui.api.action.ActionContext;

public interface ActionInterpreter<IC extends ActionInterpretContext> {
    <AC extends ActionContext> ActionHandle<AC> interpretActionHandle(IC interpretContext, Class<AC> targetActionContextType);
}
