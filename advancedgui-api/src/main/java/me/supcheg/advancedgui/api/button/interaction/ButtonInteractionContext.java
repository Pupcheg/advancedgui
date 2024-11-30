package me.supcheg.advancedgui.api.button.interaction;

import me.supcheg.advancedgui.api.action.EntityAttachedActionContext;
import me.supcheg.advancedgui.api.button.Button;
import me.supcheg.advancedgui.api.gui.Gui;
import org.jetbrains.annotations.NotNull;

public interface ButtonInteractionContext extends EntityAttachedActionContext {
    @NotNull
    Gui gui();

    @NotNull
    Button button();

    int slot();

    @NotNull
    ButtonInteractionType interactionType();
}
