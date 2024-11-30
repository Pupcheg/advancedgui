package me.supcheg.advancedgui.api.button.tick;

import me.supcheg.advancedgui.api.action.ActionContext;
import me.supcheg.advancedgui.api.button.Button;
import me.supcheg.advancedgui.api.gui.Gui;
import org.jetbrains.annotations.NotNull;

public interface ButtonTickContext extends ActionContext {
    @NotNull
    Gui gui();

    @NotNull
    Button button();
}
