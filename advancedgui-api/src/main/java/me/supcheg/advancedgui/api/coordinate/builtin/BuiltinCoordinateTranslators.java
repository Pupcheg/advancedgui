package me.supcheg.advancedgui.api.coordinate.builtin;

import me.supcheg.advancedgui.api.coordinate.Coordinate;
import me.supcheg.advancedgui.api.coordinate.CoordinateTranslator;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

import static me.supcheg.advancedgui.api.coordinate.Coordinate.coordinate;

public final class BuiltinCoordinateTranslators {

    private static final int PLAYER_INVENTORY_ROWS = 4;
    private static final int MAX_CHEST_INVENTORY_ROWS = 6;

    private BuiltinCoordinateTranslators() {
    }

    @NotNull
    public static CoordinateTranslator chest(int rows) {
        return CHEST[rows - 1];
    }

    @NotNull
    public static CoordinateTranslator furnace() {
        return FURNACE;
    }

    @NotNull
    public static CoordinateTranslator anvil() {
        return ANVIL;
    }

    private static final CoordinateTranslator[] CHEST = new CoordinateTranslator[MAX_CHEST_INVENTORY_ROWS];

    static {
        for (int rows = 1; rows <= MAX_CHEST_INVENTORY_ROWS; rows++) {
            CHEST[rows - 1] = new CombinedCoordinateTranslator(
                    "chest9x" + rows,
                    new RowedPartitionCoordinateTranslator(0, rows),
                    new RowedPartitionCoordinateTranslator(rows, PLAYER_INVENTORY_ROWS)
            );
        }
    }

    private static final CoordinateTranslator FURNACE = new CombinedCoordinateTranslator(
            "furnace",
            new SimplePartitionCoordinateTranslator(3) {
                @Override
                public int toIndex(@NotNull Coordinate coordinate) {
                    return coordinate.y();
                }

                @Override
                public boolean acceptable(@NotNull Coordinate coordinate) {
                    int x = coordinate.x();
                    int y = coordinate.y();
                    return x == 0 && y >= 0 && y < slotsCount;
                }

                @NotNull
                @Override
                public Coordinate toCoordinate(int index) {
                    return coordinate(0, index);
                }

                @NotNull
                @Override
                public Stream<Coordinate> availableCoordinates() {
                    return Stream.of(
                            coordinate(0, 0),
                            coordinate(0, 1),
                            coordinate(0, 2)
                    );
                }
            },
            new RowedPartitionCoordinateTranslator(3, PLAYER_INVENTORY_ROWS)
    );

    private static final CoordinateTranslator ANVIL = new CombinedCoordinateTranslator(
            "anvil",
            new SimplePartitionCoordinateTranslator(3) {
                @Override
                public boolean acceptable(@NotNull Coordinate coordinate) {
                    int x = coordinate.x();
                    int y = coordinate.y();
                    return x >= 0 && x < slotsCount && y == 0;
                }

                @Override
                public int toIndex(@NotNull Coordinate coordinate) {
                    return coordinate.x();
                }

                @NotNull
                @Override
                public Coordinate toCoordinate(int index) {
                    return coordinate(index, 0);
                }

                @NotNull
                @Override
                public Stream<Coordinate> availableCoordinates() {
                    return Stream.of(
                            coordinate(0, 0),
                            coordinate(1, 0),
                            coordinate(2, 0)
                    );
                }
            },
            new RowedPartitionCoordinateTranslator(1, PLAYER_INVENTORY_ROWS)
    );
}
