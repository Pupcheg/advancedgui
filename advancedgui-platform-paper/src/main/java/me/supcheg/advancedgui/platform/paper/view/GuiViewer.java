package me.supcheg.advancedgui.platform.paper.view;

import me.supcheg.advancedgui.platform.paper.gui.GuiImpl;
import net.kyori.adventure.audience.Audience;

public interface GuiViewer {
    GuiView open(Audience audience, GuiImpl gui);
}
