package me.supcheg.advancedgui.api.gui;

import me.supcheg.advancedgui.api.gui.background.Background;
import me.supcheg.advancedgui.api.gui.template.GuiTemplate;
import me.supcheg.advancedgui.api.layout.Layout;
import me.supcheg.advancedgui.api.lifecycle.Lifecycled;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Keyed;

public interface Gui extends Keyed, Lifecycled<Gui> {

    Background background();

    Layout<?> layout();

    void open(Audience audience);

    GuiTemplate source();
}
