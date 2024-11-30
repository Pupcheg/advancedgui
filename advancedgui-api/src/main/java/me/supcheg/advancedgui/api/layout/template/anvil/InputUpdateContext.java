package me.supcheg.advancedgui.api.layout.template.anvil;

import me.supcheg.advancedgui.api.action.EntityAttachedActionContext;
import me.supcheg.advancedgui.api.gui.Gui;
import org.jetbrains.annotations.NotNull;

public interface InputUpdateContext extends EntityAttachedActionContext {
    @NotNull
    Gui gui();

    @NotNull
    String text();
}
