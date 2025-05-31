package me.supcheg.advancedgui.api.layout.template.anvil;

import me.supcheg.advancedgui.api.action.Action;

@FunctionalInterface
public interface InputUpdateAction extends Action {
    void handleInputUpdate(InputUpdateContext ctx);
}
