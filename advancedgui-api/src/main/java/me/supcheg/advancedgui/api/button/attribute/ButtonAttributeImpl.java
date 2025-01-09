package me.supcheg.advancedgui.api.button.attribute;

import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

record ButtonAttributeImpl(
        @NotNull Key key
) implements ButtonAttribute {
}
