package me.supcheg.advancedgui.platform.paper.view;

import me.supcheg.advancedgui.platform.paper.gui.GuiImpl;
import me.supcheg.advancedgui.platform.paper.tick.Tickable;
import net.kyori.adventure.audience.Audience;

public interface GuiViewer extends Tickable {
    GuiView open(Audience audience, GuiImpl gui);
}
