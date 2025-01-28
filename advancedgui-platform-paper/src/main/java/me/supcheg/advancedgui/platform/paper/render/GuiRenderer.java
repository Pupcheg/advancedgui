package me.supcheg.advancedgui.platform.paper.render;

import me.supcheg.advancedgui.api.gui.Gui;
import net.kyori.adventure.audience.Audience;

public interface GuiRenderer {
    void renderFor(Audience audience, Gui gui);
}
