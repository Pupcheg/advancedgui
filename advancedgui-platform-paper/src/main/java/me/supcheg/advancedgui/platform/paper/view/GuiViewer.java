package me.supcheg.advancedgui.platform.paper.view;

import me.supcheg.advancedgui.api.coordinate.Coordinate;
import me.supcheg.advancedgui.platform.paper.gui.GuiImpl;
import net.kyori.adventure.audience.Audience;

public interface GuiViewer {
    void open(Audience audience, GuiImpl gui);

    void updateFull(Audience audience);

    void updateLayout(Audience audience);

    void updateButton(Audience audience, Coordinate coordinate);

    void emptyCursor(Audience audience);
}
