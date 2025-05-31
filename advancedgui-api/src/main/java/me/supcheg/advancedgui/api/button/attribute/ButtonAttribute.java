package me.supcheg.advancedgui.api.button.attribute;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import net.kyori.examination.Examinable;
import net.kyori.examination.ExaminableProperty;

import java.util.Objects;
import java.util.stream.Stream;

public interface ButtonAttribute extends Examinable, Keyed {

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

    @Override
    default Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(
                ExaminableProperty.of("key", key())
        );
    }
}
