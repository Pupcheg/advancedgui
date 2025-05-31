package me.supcheg.advancedgui.api.coordinate.builtin;

import me.supcheg.advancedgui.api.coordinate.Coordinate;

import java.util.stream.Stream;

public interface PartitionCoordinateTranslator {
    boolean acceptable(int index);

    int toIndex(Coordinate coordinate);

    boolean acceptable(Coordinate coordinate);

    Coordinate toCoordinate(int index);

    int slotsCount();

    Stream<Coordinate> availableCoordinates();
}
