package me.supcheg.advancedgui.api.coordinate;

import me.supcheg.advancedgui.api.builder.AbstractBuilder;
import me.supcheg.advancedgui.api.builder.Buildable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Coordinate extends Buildable<Coordinate, Coordinate.Builder> {
    @NotNull
    @Contract(value = "-> new", pure = true)
    static Builder coordinate() {
        return new CoordinateImpl.BuilderImpl();
    }

    @NotNull
    @Contract(value = "_, _ -> new", pure = true)
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

        @NotNull
        @Contract("_, _ -> this")
        default Builder xy(int x, int y) {
            return x(x).y(y);
        }

        @NotNull
        @Contract("_ -> this")
        Builder x(int x);

        @NotNull
        @Contract("_ -> this")
        Builder y(int y);
    }
}
