package me.supcheg.advancedgui.api.coordinate;

import org.jetbrains.annotations.NotNull;

public interface CoordinateTranslator {

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
