package me.supcheg.advancedgui.api.loader.interpret;

import me.supcheg.advancedgui.api.action.ActionContext;
import me.supcheg.advancedgui.api.action.AudienceActionContext;

public interface AudienceActionInterpreter<IC extends ActionInterpretContext> extends ActionInterpreter<IC> {
    @Override
    default <AC extends ActionContext> ActionHandle<AC> interpretActionHandle(
            IC interpretContext,
            Class<AC> targetActionContextType
    ) {
        if (!AudienceActionContext.class.isAssignableFrom(targetActionContextType)) {
            throw new IllegalArgumentException(
                    "Target action context %s is not of type AudienceActionContext".formatted(targetActionContextType)
            );
        }

        // noinspection unchecked, rawtypes
        return interpretAudienceActionHandle(interpretContext, (Class) targetActionContextType);
    }

    <AC extends AudienceActionContext> ActionHandle<AC> interpretAudienceActionHandle(IC interpretContext, Class<AC> targetActionContextType);
}
