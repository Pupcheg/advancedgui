package me.supcheg.advancedgui.api.layout;

import me.supcheg.advancedgui.api.button.Button;
import me.supcheg.advancedgui.api.coordinate.Coordinate;
import me.supcheg.advancedgui.api.coordinate.CoordinateTranslator;
import me.supcheg.advancedgui.api.lifecycle.Lifecycled;

import java.util.Optional;
import java.util.Set;

public interface Layout<T extends Layout<T>> extends Lifecycled<T> {

    Set<? extends Button> buttons();

    CoordinateTranslator coordinateTranslator();

    default Optional<? extends Button> buttonAt(Coordinate coordinate) {
        if (!coordinateTranslator().isInBounds(coordinate)) {
            throw new IllegalArgumentException("Coordinate: " + coordinate + " is not in range of " + coordinateTranslator());
        }

        return buttons().stream()
                .filter(button -> coordinate.equals(button.coordinate()))
                .findFirst();
    }

    default Optional<? extends Button> buttonAt(int index) {
        CoordinateTranslator coordinateTranslator = coordinateTranslator();

        if (!coordinateTranslator.isInBounds(index)) {
            throw new IllegalArgumentException("Index: " + index + " is not in range of " + coordinateTranslator);
        }

        Coordinate coordinate = coordinateTranslator.toCoordinate(index);

        return buttons().stream()
                .filter(button -> coordinate.equals(button.coordinate()))
                .findFirst();
    }
}
