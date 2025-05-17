package me.supcheg.advancedgui.api.button.attribute;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import net.kyori.examination.Examinable;
import net.kyori.examination.ExaminableProperty;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.stream.Stream;

public interface ButtonAttribute extends Examinable, Keyed {

    @NotNull
    static ButtonAttribute glowing() {
        return BuiltinButtonAttributes.GLOWING;
    }

    @NotNull
    static ButtonAttribute hidden() {
        return BuiltinButtonAttributes.HIDDEN;
    }

    @NotNull
    static ButtonAttribute disabled() {
        return BuiltinButtonAttributes.DISABLED;
    }

    @NotNull
    @Contract("_ -> new")
    static ButtonAttribute buttonAttribute(@NotNull Key key) {
        Objects.requireNonNull(key, "key");
        return new ButtonAttributeImpl(key);
    }

    @NotNull
    @Override
    default Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(
                ExaminableProperty.of("key", key())
        );
    }
}
