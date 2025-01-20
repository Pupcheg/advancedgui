package me.supcheg.advancedgui.api.coordinate.builtin;

import me.supcheg.advancedgui.api.coordinate.Coordinate;
import org.jetbrains.annotations.NotNull;

import static me.supcheg.advancedgui.api.coordinate.Coordinate.coordinate;

record RowedPartitionCoordinateTranslator(
        int startRowInclusive,
        int rowsCount
) implements PartitionCoordinateTranslator {
    @Override
    public boolean acceptable(int index) {
        return index >= startRowInclusive * 9 && index < slotsCount();
    }

    @Override
    public int toIndex(@NotNull Coordinate coordinate) {
        return coordinate.x() + coordinate.y() * 9;
    }

    @Override
    public boolean acceptable(@NotNull Coordinate coordinate) {
        return false;
    }

    @NotNull
    @Override
    public Coordinate toCoordinate(int index) {
        return coordinate(index / 9, index % 9);
    }

    @Override
    public int slotsCount() {
        return rowsCount * 9;
    }
}