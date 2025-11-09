package me.supcheg.advancedgui.api.button.attribute;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;

import java.util.Objects;

public interface ButtonAttribute extends Keyed {

    static ButtonAttribute glowing() {
        return BuiltinButtonAttributes.GLOWING;
    }

    static ButtonAttribute hidden() {
        return BuiltinButtonAttributes.HIDDEN;
    }

    static ButtonAttribute disabled() {
        return BuiltinButtonAttributes.DISABLED;
    }

    static ButtonAttribute buttonAttribute(Key key) {
        Objects.requireNonNull(key, "key");
        return new ButtonAttributeImpl(key);
    }
}
