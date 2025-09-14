package me.supcheg.advancedgui.platform.paper;

import lombok.RequiredArgsConstructor;
import net.kyori.adventure.audience.Audience;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.craftbukkit.entity.CraftPlayer;

import java.util.Objects;

@RequiredArgsConstructor
public class DefaultPlatformAudienceConverter implements PlatformAudienceConverter {
    private final String platformName;

    @Override
    public ServerPlayer verifyAndConvert(Audience audience) {
        Objects.requireNonNull(audience, "audience");

        if (!(audience instanceof CraftPlayer craftPlayer)) {
            throw new UnsupportedAudienceException(platformName, audience.getClass(), CraftPlayer.class);
        }

        return craftPlayer.getHandle();
    }

    public static class UnsupportedAudienceException extends RuntimeException {
        public UnsupportedAudienceException(String platform, Class<?> currentType, Class<?> requiredType) {
            super(
                    "Current platform (%s) requires %s instances. Got %s"
                            .formatted(platform, requiredType, currentType)
            );
        }
    }
}
