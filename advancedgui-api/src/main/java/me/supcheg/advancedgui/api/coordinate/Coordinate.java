package me.supcheg.advancedgui.api.coordinate;

import me.supcheg.advancedgui.api.builder.AbstractBuilder;
import me.supcheg.advancedgui.api.builder.Buildable;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface Coordinate extends Buildable<Coordinate, Coordinate.Builder> {

    static Builder coordinate() {
        return new CoordinateImpl.BuilderImpl();
    }

    static Coordinate coordinate(int x, int y) {
        return new CoordinateImpl(x, y);
    }

    int x();

    int y();

    interface Builder extends AbstractBuilder<Coordinate> {
        @Nullable
        Integer x();

        @Nullable
        Integer y();

        default Builder xy(int x, int y) {
            return x(x).y(y);
        }

        Builder x(int x);

        Builder y(int y);
    }
}
