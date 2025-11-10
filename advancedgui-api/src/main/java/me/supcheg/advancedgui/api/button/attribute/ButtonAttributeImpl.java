package me.supcheg.advancedgui.api.button.attribute;

import net.kyori.adventure.key.Key;

record ButtonAttributeImpl<T>(
        Key key,
        T value
) implements ButtonAttribute<T> {
}
