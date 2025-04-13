package me.supcheg.advancedgui.api.lifecycle.pointcut;

import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

record PointcutImpl(
        @NotNull Key key
) implements Pointcut {
}
