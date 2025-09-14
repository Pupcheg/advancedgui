package me.supcheg.advancedgui.platform.paper.interpret.send;

import me.supcheg.advancedgui.api.action.AudienceActionContext;
import me.supcheg.advancedgui.api.loader.interpret.ActionHandle;
import me.supcheg.advancedgui.api.loader.interpret.AudienceActionInterpreter;
import net.kyori.adventure.text.Component;

final class SendActionInterpreter implements AudienceActionInterpreter<SendActionInterpretContext> {
    @Override
    public <AC extends AudienceActionContext> ActionHandle<AC> interpretAudienceActionHandle(
            SendActionInterpretContext interpretContext,
            Class<AC> targetActionContextType
    ) {
        SendActionTarget target = interpretContext.target();
        Component content = interpretContext.content();
        return ctx -> target.send(ctx.audience(), content);
    }
}
