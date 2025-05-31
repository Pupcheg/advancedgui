package me.supcheg.advancedgui.api.layout.template.anvil;

import me.supcheg.advancedgui.api.action.AudienceActionContext;
import me.supcheg.advancedgui.api.gui.Gui;

public interface InputUpdateContext extends AudienceActionContext {
    Gui gui();

    String text();
}
