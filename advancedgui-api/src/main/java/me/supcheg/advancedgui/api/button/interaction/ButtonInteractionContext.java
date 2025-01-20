package me.supcheg.advancedgui.api.button.interaction;

import me.supcheg.advancedgui.api.action.AudienceActionContext;
import me.supcheg.advancedgui.api.button.Button;
import me.supcheg.advancedgui.api.gui.Gui;
import org.jetbrains.annotations.NotNull;

public interface ButtonInteractionContext extends AudienceActionContext {
    @NotNull
    Gui gui();

    @NotNull
    Button button();

    @NotNull
    ButtonInteractionType interactionType();
}
