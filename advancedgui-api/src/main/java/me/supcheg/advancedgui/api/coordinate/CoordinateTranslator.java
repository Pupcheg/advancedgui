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

    default int lowerSlotsCount() {
        return 36;
    }

    default int slotsCount() {
        return upperSlotsCount() + lowerSlotsCount();
    }
}
