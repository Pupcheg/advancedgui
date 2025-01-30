package me.supcheg.advancedgui.api.coordinate;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

record CoordinateImpl(
        int x,
        int y
) implements Coordinate {
    @NotNull
    @Override
    public Coordinate.Builder toBuilder() {
        return new BuilderImpl(x, y);
    }

    static final class BuilderImpl implements Coordinate.Builder {
        private Integer x;
        private Integer y;

        BuilderImpl() {
        }

        BuilderImpl(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Nullable
        @Override
        public Integer x() {
            return x;
        }

        @Nullable
        @Override
        public Integer y() {
            return y;
        }

        @NotNull
        @Override
        public Builder x(int x) {
            this.x = x;
            return this;
        }

        @NotNull
        @Override
        public Builder y(int y) {
            this.y = y;
            return this;
        }
        @NotNull
        @Override
        public Builder xy(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        @NotNull
        @Override
        public Coordinate build() {
            return new CoordinateImpl(
                    Objects.requireNonNull(x, "x"),
                    Objects.requireNonNull(y, "y")
            );
        }
    }
}
