package me.supcheg.advancedgui.api;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.kyori.adventure.key.KeyPattern.Namespace;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Advancedgui {
    @Namespace
    public static final String NAMESPACE = "advancedgui";
}
