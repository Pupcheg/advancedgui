package me.supcheg.advancedgui.platform.paper;

import net.kyori.adventure.audience.Audience;
import net.minecraft.server.level.ServerPlayer;

public interface PlatformAudienceConverter {
    ServerPlayer verifyAndConvert(Audience audience);
}
