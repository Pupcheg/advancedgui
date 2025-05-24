package me.supcheg.advancedgui.api.audience;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import org.jetbrains.annotations.NotNull;

public interface ForwardingGuiAudience extends GuiAudience, ForwardingAudience.Single {
    @NotNull
    @Override
    default Audience audience() {
        return platformAudience();
    }
}
