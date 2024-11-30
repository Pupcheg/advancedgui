package me.supcheg.advancedgui.api.gui.tick;

import me.supcheg.advancedgui.api.action.ActionContext;
import me.supcheg.advancedgui.api.gui.Gui;
import org.jetbrains.annotations.NotNull;

public interface GuiTickContext extends ActionContext {
    @NotNull
    Gui gui();
}
