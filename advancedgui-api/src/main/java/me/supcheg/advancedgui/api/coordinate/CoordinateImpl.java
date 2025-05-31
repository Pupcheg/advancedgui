package me.supcheg.advancedgui.api.coordinate;

import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Objects;

record CoordinateImpl(
        int x,
        int y
) implements Coordinate {
    @Override
    public Coordinate.Builder toBuilder() {
        return new BuilderImpl(x, y);
    }

    static final class BuilderImpl implements Coordinate.Builder {
        private @Nullable Integer x;
        private @Nullable Integer y;

        BuilderImpl() {
        }

        BuilderImpl(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        @Nullable
        public Integer x() {
            return x;
        }

        @Override
        @Nullable
        public Integer y() {
            return y;
        }

        @Override
        public Builder x(int x) {
            this.x = x;
            return this;
        }

        @Override
        public Builder y(int y) {
            this.y = y;
            return this;
        }

        @Override
        public Builder xy(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        @Override
        public Coordinate build() {
            return new CoordinateImpl(
                    Objects.requireNonNull(x, "x"),
                    Objects.requireNonNull(y, "y")
            );
        }
    }
}
