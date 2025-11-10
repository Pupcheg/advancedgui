package me.supcheg.advancedgui.api.button.attribute;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;

import java.util.Objects;

public interface ButtonAttribute<T> extends Keyed {

    static ButtonAttribute<Boolean> glowing() {
        return BuiltinButtonAttributes.GLOWING;
    }

    static<T>  ButtonAttribute<T> buttonAttribute(Key key, T value) {
        Objects.requireNonNull(key, "key");
        Objects.requireNonNull(value, "value");
        return new ButtonAttributeImpl<>(key, value);
    }
}
