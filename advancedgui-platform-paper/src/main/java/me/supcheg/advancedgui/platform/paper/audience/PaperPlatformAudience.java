package me.supcheg.advancedgui.platform.paper.audience;

import me.supcheg.advancedgui.api.audience.ForwardingGuiAudience;
import me.supcheg.advancedgui.platform.paper.view.GuiView;
import net.kyori.adventure.audience.Audience;

public record PaperPlatformAudience(
        GuiView view
) implements ForwardingGuiAudience {
    @Override
    public Audience platformAudience() {
        return view.serverPlayer().getBukkitEntity();
    }

    @Override
    public void close() {
        view.sendClose();
        view.handleClose();
    }
}
