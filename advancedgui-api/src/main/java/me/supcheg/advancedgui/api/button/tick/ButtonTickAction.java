package me.supcheg.advancedgui.api.button.tick;

import me.supcheg.advancedgui.api.action.Action;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface ButtonTickAction extends Action {
    void handleButtonTick(@NotNull ButtonTickContext ctx);
}
