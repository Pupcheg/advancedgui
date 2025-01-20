package me.supcheg.advancedgui.api.coordinate;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

final class BuiltinCoordinateTranslators {
    private BuiltinCoordinateTranslators() {
    }

    @NotNull
    @Contract("_ -> new")
    static CoordinateTranslator chest(int rows) {
        return CHEST[rows];
    }

    @NotNull
    static CoordinateTranslator furnace() {
        return FURNACE;
    }

    @NotNull
    static CoordinateTranslator anvil() {
        return ANVIL;
    }

    private static final CoordinateTranslator[] CHEST = new CoordinateTranslator[6];

    static {
        for (int rows = 0; rows < CHEST.length; rows++) {
            CHEST[rows] = new RowedCoordinateTranslator(0, rows + 4);
        }
    }

    private static final CoordinateTranslator FURNACE = new CombinedCoordinateTranslator(
            new CoordinateTranslatorImpl(3) {
                @Override
                public int toIndex(@NotNull Coordinate coordinate) {
                    assertInFurnace(coordinate);
                    return coordinate.y();
                }

                private void assertInFurnace(@NotNull Coordinate coordinate) {
                    int x = coordinate.x();
                    if (x != 0) {
                        throw indexOutOfBoundsException(coordinate);
                    }

                    int y = coordinate.y();
                    if (y < 1 || y > 3) {
                        throw indexOutOfBoundsException(coordinate);
                    }
                }

                @NotNull
                @Override
                public Coordinate toCoordinate(int index) {
                    assertInFurnace(index);
                    return Coordinate.coordinate(0, index);
                }

                private void assertInFurnace(int index) {
                    if (index < 0 || index > 2) {
                        throw indexOutOfBoundsException(index);
                    }
                }
            },
            new RowedCoordinateTranslator(4, 8)
    );

    private static final CoordinateTranslator ANVIL = new CombinedCoordinateTranslator(
            new CoordinateTranslatorImpl(3) {
                @Override
                public int toIndex(@NotNull Coordinate coordinate) {
                    assertInAnvil(coordinate);
                    return coordinate.x();
                }

                private void assertInAnvil(@NotNull Coordinate coordinate) {
                    int x = coordinate.x();
                    if (x < 1 | x > 3) {
                        throw indexOutOfBoundsException(coordinate);
                    }

                    int y = coordinate.y();
                    if (y != 0) {
                        throw indexOutOfBoundsException(coordinate);
                    }
                }

                @NotNull
                @Override
                public Coordinate toCoordinate(int index) {
                    assertInAnvil(index);
                    return Coordinate.coordinate(index, 0);
                }

                private void assertInAnvil(int index) {
                    if (index < 0 || index > 2) {
                        throw indexOutOfBoundsException(index);
                    }
                }
            },
            new RowedCoordinateTranslator(1, 5)
    );

    @RequiredArgsConstructor
    private abstract static class CoordinateTranslatorImpl implements CoordinateTranslator {
        private final int upperSlotsCount;

        @Override
        public int upperSlotsCount() {
            return upperSlotsCount;
        }
    }

    private record CombinedCoordinateTranslator(
            @NotNull CoordinateTranslator upper,
            @NotNull CoordinateTranslator lower
    ) implements CoordinateTranslator {

        @Override
        public int toIndex(@NotNull Coordinate coordinate) {
            for (CoordinateTranslator translator : translators()) {
                try {
                    return translator.toIndex(coordinate);
                } catch (IndexOutOfBoundsException ignored) {
                }
            }
            throw indexOutOfBoundsException(coordinate);
        }

        @NotNull
        @Override
        public Coordinate toCoordinate(int index) {
            for (CoordinateTranslator translator : translators()) {
                try {
                    return translator.toCoordinate(index);
                } catch (IndexOutOfBoundsException ignored) {
                }
            }
            throw indexOutOfBoundsException(index);
        }

        private Iterable<CoordinateTranslator> translators() {
            return List.of(upper, lower);
        }

        @Override
        public int upperSlotsCount() {
            return upper.upperSlotsCount();
        }
    }

    private record RowedCoordinateTranslator(
            int startRowInclusive,
            int endRowInclusive
    ) implements CoordinateTranslator {

        @Override
        public int toIndex(@NotNull Coordinate coordinate) {
            assertInInventory(coordinate);
            return coordinate.x() + coordinate.y() * 9;
        }

        private void assertInInventory(@NotNull Coordinate coordinate) {
            int x = coordinate.x();
            if (x < startRowInclusive || x > endRowInclusive) {
                throw indexOutOfBoundsException(coordinate);
            }

            int y = coordinate.y();
            if (y < 1 || y > 9) {
                throw indexOutOfBoundsException(coordinate);
            }
        }

        @NotNull
        @Override
        public Coordinate toCoordinate(int index) {
            assertInInventory(index);
            return Coordinate.coordinate(
                    index / 9,
                    index % 9
            );
        }

        @Override
        public int upperSlotsCount() {
            return (endRowInclusive - startRowInclusive) * 9;
        }

        private void assertInInventory(int index) {
            if (index < startRowInclusive * 9 || index > endRowInclusive * 9) {
                throw indexOutOfBoundsException(index);
            }
        }
    }

    @NotNull
    @Contract("_ -> new")
    private static IndexOutOfBoundsException indexOutOfBoundsException(@NotNull Coordinate coordinate) {
        return new IndexOutOfBoundsException(
                "Coordinate out of bounds: (%d, %d)".formatted(coordinate.x(), coordinate.y())
        );
    }

    @NotNull
    @Contract("_ -> new")
    private static IndexOutOfBoundsException indexOutOfBoundsException(int index) {
        return new IndexOutOfBoundsException(index);
    }
}
