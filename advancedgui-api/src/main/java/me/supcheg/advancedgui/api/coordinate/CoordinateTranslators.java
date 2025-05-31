package me.supcheg.advancedgui.api.coordinate;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.supcheg.advancedgui.api.coordinate.builtin.BuiltinCoordinateTranslators;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CoordinateTranslators {

    public static CoordinateTranslator chestCoordinateTranslator(int rows) {
        return BuiltinCoordinateTranslators.chest(rows);
    }

    public static CoordinateTranslator furnaceCoordinateTranslator() {
        return BuiltinCoordinateTranslators.furnace();
    }

    public static CoordinateTranslator anvilCoordinateTranslator() {
        return BuiltinCoordinateTranslators.anvil();
    }
}
