package me.supcheg.advancedgui.api.coordinate.builtin;

import me.supcheg.advancedgui.api.coordinate.Coordinate;
import me.supcheg.advancedgui.api.coordinate.CoordinateTranslator;
import net.kyori.adventure.key.Key;

import java.util.stream.Stream;

record CombinedCoordinateTranslator(
        Key key,
        PartitionCoordinateTranslator upper,
        PartitionCoordinateTranslator lower
) implements CoordinateTranslator {
    @Override
    public boolean isInBounds(Coordinate coordinate) {
        return upper.acceptable(coordinate) || lower.acceptable(coordinate);
    }

    @Override
    public boolean isInBounds(int index) {
        return upper.acceptable(index) || lower.acceptable(index);
    }

    @Override
    public int toIndex(Coordinate coordinate) {
        if (upper.acceptable(coordinate)) {
            return upper.toIndex(coordinate);
        }

        if (lower.acceptable(coordinate)) {
            return upper.slotsCount() + lower.toIndex(coordinate);
        }

        throw indexOutOfBoundsException(coordinate);
    }

    @Override
    public Coordinate toCoordinate(int index) {
        if (upper.acceptable(index)) {
            return upper.toCoordinate(index);
        }

        if (lower.acceptable(index - upper.slotsCount())) {
            return lower.toCoordinate(index - upper.slotsCount());
        }

        throw indexOutOfBoundsException(index);
    }

    @Override
    public int upperSlotsCount() {
        return upper.slotsCount();
    }

    @Override
    public int lowerSlotsCount() {
        return lower.slotsCount();
    }

    @Override
    public Stream<Coordinate> availableCoordinates() {
        return Stream.concat(upper.availableCoordinates(), lower.availableCoordinates());
    }

    private IndexOutOfBoundsException indexOutOfBoundsException(Coordinate coordinate) {
        return new IndexOutOfBoundsException(
                "Coordinate out of bounds in %s: (%d, %d)".formatted(key, coordinate.x(), coordinate.y())
        );
    }

    private IndexOutOfBoundsException indexOutOfBoundsException(int index) {
        return new IndexOutOfBoundsException("Index out of range in %s: %d".formatted(key, index));
    }

}