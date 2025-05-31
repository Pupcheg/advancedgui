package me.supcheg.advancedgui.api.action;

import me.supcheg.advancedgui.api.audience.GuiAudience;

public interface AudienceActionContext extends ActionContext {
    GuiAudience audience();
}
