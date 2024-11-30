package me.supcheg.advancedgui.api.gui.tick;

import me.supcheg.advancedgui.api.action.Action;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface GuiTickAction extends Action {
    void handleGuiTick(@NotNull GuiTickContext ctx);
}
