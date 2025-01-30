package me.supcheg.advancedgui.api.coordinate.builtin;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import me.supcheg.advancedgui.api.coordinate.Coordinate;
import org.jetbrains.annotations.NotNull;

import static me.supcheg.advancedgui.api.coordinate.Coordinate.coordinate;

@ToString
@EqualsAndHashCode
final class RowedPartitionCoordinateTranslator implements PartitionCoordinateTranslator {
    private final int startRow;
    private final int endRow;

    private final int startIndex;
    private final int endIndex;

    RowedPartitionCoordinateTranslator(
            int start,
            int count
    ) {
        this.startRow = start;
        this.endRow = start + count;

        this.startIndex = startRow * 9;
        this.endIndex = endRow * 9;
    }

    @Override
    public boolean acceptable(int index) {
        return index >= startIndex && index < endIndex;
    }

    @Override
    public boolean acceptable(@NotNull Coordinate coordinate) {
        int x = coordinate.x();
        int y = coordinate.y();
        return y >= startRow && y < endRow
                && x >= 0 && x < 9;
    }

    @Override
    public int toIndex(@NotNull Coordinate coordinate) {
        return coordinate.x() + coordinate.y() * 9;
    }

    @NotNull
    @Override
    public Coordinate toCoordinate(int index) {
        return coordinate(index % 9, index / 9);
    }

    @Override
    public int slotsCount() {
        return endIndex - startIndex;
    }

}