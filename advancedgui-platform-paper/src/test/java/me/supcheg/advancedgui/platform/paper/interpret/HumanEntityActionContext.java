package me.supcheg.advancedgui.platform.paper.interpret;

import me.supcheg.advancedgui.api.action.AudienceActionContext;
import me.supcheg.advancedgui.api.audience.ForwardingGuiAudience;
import me.supcheg.advancedgui.api.audience.GuiAudience;
import net.kyori.adventure.audience.Audience;
import org.bukkit.entity.HumanEntity;
import org.jetbrains.annotations.NotNull;

public interface HumanEntityActionContext extends AudienceActionContext {
    @NotNull
    HumanEntity humanEntity();

    @Override
    @NotNull
    default GuiAudience audience() {
        return new ForwardingGuiAudience() {
            @NotNull
            @Override
            public Audience platformAudience() {
                return humanEntity();
            }

            @Override
            public void close() {
                humanEntity().closeInventory();
            }
        };
    }
}