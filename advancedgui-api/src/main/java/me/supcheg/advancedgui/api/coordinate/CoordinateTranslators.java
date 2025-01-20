package me.supcheg.advancedgui.api.coordinate;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.supcheg.advancedgui.api.coordinate.builtin.BuiltinCoordinateTranslators;
import org.jetbrains.annotations.NotNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CoordinateTranslators {
    @NotNull
    public static CoordinateTranslator chestCoordinateTranslator(int rows) {
        return BuiltinCoordinateTranslators.chest(rows);
    }

    @NotNull
    public static CoordinateTranslator furnaceCoordinateTranslator() {
        return BuiltinCoordinateTranslators.furnace();
    }

    @NotNull
    public static CoordinateTranslator anvilCoordinateTranslator() {
        return BuiltinCoordinateTranslators.anvil();
    }
}
