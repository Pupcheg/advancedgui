package me.supcheg.advancedgui.api.coordinate;

import net.kyori.adventure.key.Keyed;
import net.kyori.examination.Examinable;
import net.kyori.examination.ExaminableProperty;

import java.util.stream.Stream;

public interface CoordinateTranslator extends Keyed, Examinable {

    boolean isInBounds(Coordinate coordinate);

    default boolean isInBounds(int x, int y) {
        return isInBounds(Coordinate.coordinate(x, y));
    }

    boolean isInBounds(int index);

    int toIndex(Coordinate coordinate);

    default int toIndex(int x, int y) {
        return toIndex(Coordinate.coordinate(x, y));
    }

    Coordinate toCoordinate(int index);

    int upperSlotsCount();

    int lowerSlotsCount();

    default int slotsCount() {
        return upperSlotsCount() + lowerSlotsCount();
    }

    Stream<Coordinate> availableCoordinates();

    @Override
    default Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(
                ExaminableProperty.of("key", key())
        );
    }
}
