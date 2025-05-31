package me.supcheg.advancedgui.api.audience;

import net.kyori.adventure.audience.Audience;

public interface GuiAudience extends Audience {
    Audience platformAudience();

    void close();
}
