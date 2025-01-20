package me.supcheg.advancedgui.api.coordinate.builtin;

import me.supcheg.advancedgui.api.coordinate.Coordinate;
import org.jetbrains.annotations.NotNull;

public interface PartitionCoordinateTranslator {
    boolean acceptable(int index);

    int toIndex(@NotNull Coordinate coordinate);

    boolean acceptable(@NotNull Coordinate coordinate);

    @NotNull
    Coordinate toCoordinate(int index);

    int slotsCount();
}
