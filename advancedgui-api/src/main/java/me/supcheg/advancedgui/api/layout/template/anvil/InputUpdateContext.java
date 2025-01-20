package me.supcheg.advancedgui.api.layout.template.anvil;

import me.supcheg.advancedgui.api.action.AudienceActionContext;
import me.supcheg.advancedgui.api.gui.Gui;
import org.jetbrains.annotations.NotNull;

public interface InputUpdateContext extends AudienceActionContext {
    @NotNull
    Gui gui();

    @NotNull
    String text();
}
