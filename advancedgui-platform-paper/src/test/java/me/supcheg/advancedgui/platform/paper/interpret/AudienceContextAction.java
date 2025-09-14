package me.supcheg.advancedgui.platform.paper.interpret;

import me.supcheg.advancedgui.api.action.Action;
import me.supcheg.advancedgui.api.action.AudienceActionContext;

public interface AudienceContextAction extends Action {
    void handle(AudienceActionContext ctx);
}