package me.supcheg.advancedgui.api.action;

import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;

public interface AudienceActionContext extends ActionContext {
    @NotNull
    Audience audience();
}
