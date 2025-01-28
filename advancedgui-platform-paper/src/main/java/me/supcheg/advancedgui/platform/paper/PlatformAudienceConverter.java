package me.supcheg.advancedgui.platform.paper;

import net.kyori.adventure.audience.Audience;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public interface PlatformAudienceConverter {
    @NotNull
    ServerPlayer verifyAndConvert(@NotNull Audience audience);
}
