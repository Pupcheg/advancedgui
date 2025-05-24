package me.supcheg.advancedgui.platform.paper.button;

import me.supcheg.advancedgui.api.audience.GuiAudience;
import me.supcheg.advancedgui.api.button.interaction.ButtonInteractionContext;
import me.supcheg.advancedgui.api.button.interaction.ButtonInteractionType;
import me.supcheg.advancedgui.api.gui.Gui;
import me.supcheg.advancedgui.platform.paper.audience.PaperPlatformAudience;
import me.supcheg.advancedgui.platform.paper.gui.ButtonImpl;
import me.supcheg.advancedgui.platform.paper.view.GuiView;
import net.minecraft.world.inventory.ClickType;
import org.jetbrains.annotations.NotNull;

public record PaperPlatformButtonInteractionContextAdapter(
        @NotNull GuiView view,
        @NotNull ButtonImpl button,
        @NotNull ClickType clickType,
        int buttonNum
) implements ButtonInteractionContext {
    @NotNull
    @Override
    public Gui gui() {
        return view.gui();
    }

    @NotNull
    @Override
    public ButtonInteractionType interactionType() {
        return ButtonInteractionTypeConverter.fromVanilla(clickType, buttonNum);
    }

    @NotNull
    @Override
    public GuiAudience audience() {
        return new PaperPlatformAudience(view);
    }
}
