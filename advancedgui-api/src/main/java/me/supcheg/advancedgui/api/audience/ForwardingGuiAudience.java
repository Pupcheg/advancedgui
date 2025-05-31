package me.supcheg.advancedgui.api.audience;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;

public interface ForwardingGuiAudience extends GuiAudience, ForwardingAudience.Single {
    @Override
    default Audience audience() {
        return platformAudience();
    }
}
