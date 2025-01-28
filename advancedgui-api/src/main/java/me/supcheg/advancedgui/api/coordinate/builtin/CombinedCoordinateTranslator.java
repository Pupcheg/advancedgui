package me.supcheg.advancedgui.api.coordinate.builtin;

import me.supcheg.advancedgui.api.coordinate.Coordinate;
import me.supcheg.advancedgui.api.coordinate.CoordinateTranslator;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

record CombinedCoordinateTranslator(
        @NotNull PartitionCoordinateTranslator upper,
        @NotNull PartitionCoordinateTranslator lower
) implements CoordinateTranslator {

    @Override
    public boolean isInBounds(@NotNull Coordinate coordinate) {
        return upper.acceptable(coordinate) || lower.acceptable(coordinate);
    }

    @Override
    public boolean isInBounds(int index) {
        return upper.acceptable(index) || lower.acceptable(index);
    }

    @Override
    public int toIndex(@NotNull Coordinate coordinate) {
        for (PartitionCoordinateTranslator translator : translators()) {
            if (translator.acceptable(coordinate)) {
                return translator.toIndex(coordinate);
            }
        }
        throw indexOutOfBoundsException(coordinate);
    }

    @NotNull
    @Override
    public Coordinate toCoordinate(int index) {
        for (PartitionCoordinateTranslator translator : translators()) {
            if (translator.acceptable(index)) {
                return translator.toCoordinate(index);
            }
        }
        throw indexOutOfBoundsException(index);
    }

    private List<PartitionCoordinateTranslator> translators() {
        return List.of(upper, lower);
    }

    @Override
    public int upperSlotsCount() {
        return upper.slotsCount();
    }

    @Override
    public int lowerSlotsCount() {
        return lower.slotsCount();
    }

    @NotNull
    @Contract("_ -> new")
    private static IndexOutOfBoundsException indexOutOfBoundsException(@NotNull Coordinate coordinate) {
        return new IndexOutOfBoundsException(
                "Coordinate out of bounds: (%d, %d)".formatted(coordinate.x(), coordinate.y())
        );
    }

    @NotNull
    @Contract("_ -> new")
    private static IndexOutOfBoundsException indexOutOfBoundsException(int index) {
        return new IndexOutOfBoundsException(index);
    }
}