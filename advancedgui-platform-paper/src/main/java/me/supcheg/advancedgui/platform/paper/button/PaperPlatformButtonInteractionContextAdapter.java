package me.supcheg.advancedgui.platform.paper.button;

import me.supcheg.advancedgui.api.audience.GuiAudience;
import me.supcheg.advancedgui.api.button.interaction.ButtonInteractionContext;
import me.supcheg.advancedgui.api.button.interaction.ButtonInteractionType;
import me.supcheg.advancedgui.api.gui.Gui;
import me.supcheg.advancedgui.platform.paper.audience.PaperPlatformAudience;
import me.supcheg.advancedgui.platform.paper.gui.ButtonImpl;
import me.supcheg.advancedgui.platform.paper.view.GuiView;
import net.minecraft.world.inventory.ClickType;

public record PaperPlatformButtonInteractionContextAdapter(
        GuiView view,
        ButtonImpl button,
        ClickType clickType,
        int buttonNum
) implements ButtonInteractionContext {
    @Override
    public Gui gui() {
        return view.gui();
    }

    @Override
    public ButtonInteractionType interactionType() {
        return ButtonInteractionTypeConverter.fromVanilla(clickType, buttonNum);
    }

    @Override
    public GuiAudience audience() {
        return new PaperPlatformAudience(view);
    }
}
