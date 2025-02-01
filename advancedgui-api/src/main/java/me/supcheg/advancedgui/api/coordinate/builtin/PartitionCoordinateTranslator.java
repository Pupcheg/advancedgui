package me.supcheg.advancedgui.api.coordinate.builtin;

import me.supcheg.advancedgui.api.coordinate.Coordinate;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public interface PartitionCoordinateTranslator {
    boolean acceptable(int index);

    int toIndex(@NotNull Coordinate coordinate);

    boolean acceptable(@NotNull Coordinate coordinate);

    @NotNull
    Coordinate toCoordinate(int index);

    int slotsCount();

    @NotNull
    Stream<Coordinate> availableCoordinates();
}
