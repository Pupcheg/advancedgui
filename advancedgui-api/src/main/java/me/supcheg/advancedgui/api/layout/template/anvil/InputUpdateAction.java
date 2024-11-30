package me.supcheg.advancedgui.api.layout.template.anvil;

import me.supcheg.advancedgui.api.action.Action;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface InputUpdateAction extends Action {
    void handleInputUpdate(@NotNull InputUpdateContext ctx);
}
