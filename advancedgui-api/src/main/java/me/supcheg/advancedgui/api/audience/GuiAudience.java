package me.supcheg.advancedgui.api.audience;

import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;

public interface GuiAudience extends Audience {
    @NotNull
    Audience platformAudience();

    void close();
}
