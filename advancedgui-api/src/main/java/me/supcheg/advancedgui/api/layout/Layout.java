package me.supcheg.advancedgui.api.layout;

import me.supcheg.advancedgui.api.button.Button;
import me.supcheg.advancedgui.api.coordinate.Coordinate;
import me.supcheg.advancedgui.api.coordinate.CoordinateTranslator;
import me.supcheg.advancedgui.api.lifecycle.Lifecycled;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public interface Layout<T extends Layout<T>> extends Lifecycled<T> {
    @NotNull
    Set<? extends Button> buttons();

    @NotNull
    CoordinateTranslator coordinateTranslator();

    @Nullable
    default Button buttonAt(@NotNull Coordinate coordinate) {
        if (!coordinateTranslator().isInBounds(coordinate)) {
            throw new IllegalArgumentException("Coordinate: " + coordinate + " is not in range of " + coordinateTranslator());
        }

        return buttons().stream()
                .filter(button -> coordinate.equals(button.coordinate()))
                .findFirst()
                .orElse(null);
    }
}
