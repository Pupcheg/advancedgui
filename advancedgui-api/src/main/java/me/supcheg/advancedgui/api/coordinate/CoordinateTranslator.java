package me.supcheg.advancedgui.api.coordinate;

import net.kyori.adventure.key.Keyed;
import net.kyori.examination.Examinable;
import net.kyori.examination.ExaminableProperty;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public interface CoordinateTranslator extends Keyed, Examinable {

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

    @NotNull
    Stream<Coordinate> availableCoordinates();

    @NotNull
    @Override
    default Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(
                ExaminableProperty.of("key", key())
        );
    }
}
