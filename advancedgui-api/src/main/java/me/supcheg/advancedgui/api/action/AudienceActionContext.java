package me.supcheg.advancedgui.api.action;

import me.supcheg.advancedgui.api.audience.GuiAudience;
import org.jetbrains.annotations.NotNull;

public interface AudienceActionContext extends ActionContext {
    @NotNull
    GuiAudience audience();
}
