package me.supcheg.advancedgui.api.loader.interpret;

import me.supcheg.advancedgui.api.action.ActionContext;

final class DummyActionInterpreter implements ActionInterpreter<DummyActionInterpretContext> {
    @Override
    public <AC extends ActionContext> ActionHandle<AC> interpretActionHandle(
            DummyActionInterpretContext interpretContext,
            Class<AC> targetActionContextType
    ) {
        return ctx -> {/* nothing */};
    }
}
