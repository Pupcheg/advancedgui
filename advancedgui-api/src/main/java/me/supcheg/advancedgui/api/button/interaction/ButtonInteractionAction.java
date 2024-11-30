package me.supcheg.advancedgui.api.button.interaction;

import me.supcheg.advancedgui.api.action.Action;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface ButtonInteractionAction extends Action {
    void handleButtonInteraction(@NotNull ButtonInteractionContext ctx);
}
