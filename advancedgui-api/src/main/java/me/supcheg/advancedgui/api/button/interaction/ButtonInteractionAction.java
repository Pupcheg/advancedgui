package me.supcheg.advancedgui.api.button.interaction;

import me.supcheg.advancedgui.api.action.Action;

@FunctionalInterface
public interface ButtonInteractionAction extends Action {
    void handleButtonInteraction(ButtonInteractionContext ctx);
}
