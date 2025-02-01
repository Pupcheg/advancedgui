package me.supcheg.advancedgui.api.coordinate.builtin;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import me.supcheg.advancedgui.api.coordinate.Coordinate;
import me.supcheg.advancedgui.api.coordinate.CoordinateTranslator;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

@EqualsAndHashCode
@ToString
final class CombinedCoordinateTranslator implements CoordinateTranslator {
    private final String key;
    private final PartitionCoordinateTranslator upper;
    private final PartitionCoordinateTranslator lower;
    private final PartitionCoordinateTranslator[] translators;

    CombinedCoordinateTranslator(
            @NotNull String key,
            @NotNull PartitionCoordinateTranslator upper,
            @NotNull PartitionCoordinateTranslator lower
    ) {
        this.key = key;
        this.upper = upper;
        this.lower = lower;
        this.translators = new PartitionCoordinateTranslator[]{upper, lower};
    }

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
        for (PartitionCoordinateTranslator translator : translators) {
            if (translator.acceptable(coordinate)) {
                return translator.toIndex(coordinate);
            }
        }
        throw indexOutOfBoundsException(coordinate);
    }

    @NotNull
    @Override
    public Coordinate toCoordinate(int index) {
        for (PartitionCoordinateTranslator translator : translators) {
            if (translator.acceptable(index)) {
                return translator.toCoordinate(index);
            }
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

    @NotNull
    @Override
    public Stream<Coordinate> availableCoordinates() {
        return Stream.concat(upper.availableCoordinates(), lower.availableCoordinates());
    }

    @NotNull
    @Contract("_ -> new")
    private IndexOutOfBoundsException indexOutOfBoundsException(@NotNull Coordinate coordinate) {
        return new IndexOutOfBoundsException(
                "Coordinate out of bounds in %s: (%d, %d)".formatted(key, coordinate.x(), coordinate.y())
        );
    }

    @NotNull
    @Contract("_ -> new")
    private IndexOutOfBoundsException indexOutOfBoundsException(int index) {
        return new IndexOutOfBoundsException("Index out of range in %s: %d".formatted(key, index));
    }

}