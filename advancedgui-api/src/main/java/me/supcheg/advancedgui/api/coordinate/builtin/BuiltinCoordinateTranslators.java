package me.supcheg.advancedgui.api.coordinate.builtin;

import me.supcheg.advancedgui.api.coordinate.Coordinate;
import me.supcheg.advancedgui.api.coordinate.CoordinateTranslator;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class BuiltinCoordinateTranslators {

    private static final int PLAYER_INVENTORY_ROWS = 4;

    private BuiltinCoordinateTranslators() {
    }

    @NotNull
    @Contract("_ -> new")
    public static CoordinateTranslator chest(int rows) {
        return CHEST[rows];
    }

    @NotNull
    public static CoordinateTranslator furnace() {
        return FURNACE;
    }

    @NotNull
    public static CoordinateTranslator anvil() {
        return ANVIL;
    }

    private static final CoordinateTranslator[] CHEST = new CoordinateTranslator[6];

    static {
        for (int rows = 1; rows <= CHEST.length; rows++) {
            CHEST[rows - 1] = new CombinedCoordinateTranslator(
                    "chest9x" + rows,
                    new RowedPartitionCoordinateTranslator(0, rows),
                    new RowedPartitionCoordinateTranslator(rows, rows + PLAYER_INVENTORY_ROWS)
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
                    return Coordinate.coordinate(0, index);
                }
            },
            new RowedPartitionCoordinateTranslator(4, PLAYER_INVENTORY_ROWS)
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
                    return Coordinate.coordinate(index, 0);
                }
            },
            new RowedPartitionCoordinateTranslator(1, PLAYER_INVENTORY_ROWS)
    );
}
