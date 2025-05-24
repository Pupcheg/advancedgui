package me.supcheg.advancedgui.platform.paper.interpret;

import me.supcheg.advancedgui.api.action.Action;
import me.supcheg.advancedgui.api.action.AudienceActionContext;
import org.jetbrains.annotations.NotNull;

public interface AudienceContextAction extends Action {
    void handle(@NotNull AudienceActionContext ctx);
}