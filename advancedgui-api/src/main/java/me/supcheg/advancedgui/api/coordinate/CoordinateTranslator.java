package me.supcheg.advancedgui.api.coordinate;

import org.jetbrains.annotations.NotNull;

public interface CoordinateTranslator {

    boolean isInBounds(@NotNull Coordinate coordinate);

    default boolean isInBounds(int x, int y) {
        return isInBounds(Coordinate.coordinate(x, y));
    }

    boolean isInBounds(int index);

    int toIndex(@NotNull Coordinate coordinate);

    default int toIndex(int x, int y) {
        return toIndex(Coordinate.coordinate(x, y));
    }

    @NotNull
    Coordinate toCoordinate(int index);

    int upperSlotsCount();

    int lowerSlotsCount();

    default int slotsCount() {
        return upperSlotsCount() + lowerSlotsCount();
    }
}
