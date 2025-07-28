package me.supcheg.advancedgui.api.loader.interpret;

import me.supcheg.advancedgui.api.action.ActionContext;

public interface ActionHandle<C extends ActionContext> {
    void handle(C ctx);
}
