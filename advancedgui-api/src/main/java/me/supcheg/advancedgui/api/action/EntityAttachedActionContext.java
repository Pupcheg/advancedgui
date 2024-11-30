package me.supcheg.advancedgui.api.action;

import org.jetbrains.annotations.NotNull;

public interface EntityAttachedActionContext extends ActionContext {
    @NotNull
    Object entity();
}
