package me.supcheg.advancedgui.api.button.interaction;

import me.supcheg.advancedgui.api.action.AudienceActionContext;
import me.supcheg.advancedgui.api.button.Button;
import me.supcheg.advancedgui.api.gui.Gui;

public interface ButtonInteractionContext extends AudienceActionContext {
    Gui gui();

    Button button();

    ButtonInteractionType interactionType();
}
