package me.supcheg.advancedgui.api.messaging;

import me.supcheg.advancedgui.api.Advancedgui;
import me.supcheg.advancedgui.api.gui.template.GuiTemplate;
import net.kyori.adventure.key.Key;

public record DebugViewGuiTemplate(
        GuiTemplate template
) implements Message {
    public static final Key KEY = Key.key(Advancedgui.NAMESPACE, "debug_view_gui_template");

    @Override
    public Key key() {
        return KEY;
    }
}
