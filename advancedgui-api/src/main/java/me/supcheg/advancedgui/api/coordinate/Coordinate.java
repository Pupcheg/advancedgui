package me.supcheg.advancedgui.api.coordinate;

import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.code.RecordInterface;

import java.util.function.Consumer;

@RecordInterface
public interface Coordinate extends Buildable<Coordinate, CoordinateBuilder> {

    static CoordinateBuilder coordinate() {
        return new CoordinateBuilderImpl();
    }

    static Coordinate coordinate(Consumer<CoordinateBuilder> consumer) {
        return Buildable.configureAndBuild(coordinate(), consumer);
    }

    static Coordinate coordinate(int x, int y) {
        return new CoordinateImpl(x, y);
    }

    int x();

    int y();
}
