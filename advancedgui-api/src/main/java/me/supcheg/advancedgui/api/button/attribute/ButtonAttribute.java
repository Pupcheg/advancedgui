package me.supcheg.advancedgui.api.button.attribute;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public interface ButtonAttribute extends Keyed {

    @NotNull
    static ButtonAttribute glowing() {
        return BuiltinButtonAttributes.GLOWING;
    }

    static ButtonAttribute hidden() {
        return BuiltinButtonAttributes.HIDDEN;
    }

    static ButtonAttribute disabled() {
        return BuiltinButtonAttributes.DISABLED;
    }

    @NotNull
    @Contract("_ -> new")
    static ButtonAttribute buttonAttribute(@NotNull Key key) {
        Objects.requireNonNull(key, "key");
        return new ButtonAttributeImpl(key);
    }
}
