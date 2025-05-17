package me.supcheg.advancedgui.platform.paper.view;

import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

public interface GuiViewLookup {
    Optional<GuiView> lookupGuiView(ServerPlayer serverPlayer);
}
