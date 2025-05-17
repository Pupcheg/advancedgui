package me.supcheg.advancedgui.platform.paper;

import lombok.RequiredArgsConstructor;
import net.kyori.adventure.audience.Audience;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@RequiredArgsConstructor
public class DefaultPlatformAudienceConverter implements PlatformAudienceConverter {
    private final String platformName;

    @NotNull
    @Override
    public ServerPlayer verifyAndConvert(@NotNull Audience audience) {
        Objects.requireNonNull(audience, "audience");

        if (!(audience instanceof CraftPlayer craftPlayer)) {
            throw new UnsupportedAudienceException(platformName, audience.getClass(), CraftPlayer.class);
        }

        return craftPlayer.getHandle();
    }

    public static class UnsupportedAudienceException extends RuntimeException {
        public UnsupportedAudienceException(@NotNull String platform, @NotNull Class<?> currentType, @NotNull Class<?> requiredType) {
            super(
                    "Current platform (%s) requires %s instances. Got %s"
                            .formatted(platform, requiredType, currentType)
            );
        }
    }
}
