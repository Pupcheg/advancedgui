package me.supcheg.advancedgui.api.coordinate.builtin;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.supcheg.advancedgui.api.Advancedgui;
import me.supcheg.advancedgui.api.coordinate.Coordinate;
import me.supcheg.advancedgui.api.coordinate.CoordinateTranslator;
import net.kyori.adventure.key.Key;

import java.util.stream.Stream;

import static me.supcheg.advancedgui.api.coordinate.Coordinate.coordinate;
import static net.kyori.adventure.key.Key.key;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BuiltinCoordinateTranslators {

    private static final int PLAYER_INVENTORY_ROWS = 4;
    private static final int MAX_CHEST_INVENTORY_ROWS = 6;

    public static CoordinateTranslator chest(int rows) {
        return CHEST[rows - 1];
    }

    public static CoordinateTranslator furnace() {
        return FURNACE;
    }

    public static CoordinateTranslator anvil() {
        return ANVIL;
    }

    private static final CoordinateTranslator[] CHEST = new CoordinateTranslator[MAX_CHEST_INVENTORY_ROWS];

    static {
        for (int rows = 1; rows <= MAX_CHEST_INVENTORY_ROWS; rows++) {
            CHEST[rows - 1] = new CombinedCoordinateTranslator(
                    key(Advancedgui.NAMESPACE, "chest9x" + rows),
                    new RowedPartitionCoordinateTranslator(0, rows),
                    new RowedPartitionCoordinateTranslator(rows, PLAYER_INVENTORY_ROWS)
            );
        }
    }

    private static final CoordinateTranslator FURNACE = new CombinedCoordinateTranslator(
            key(Advancedgui.NAMESPACE, "furnace"),
            new SimplePartitionCoordinateTranslator(3) {
                @Override
                public int toIndex(Coordinate coordinate) {
                    return coordinate.y();
                }

                @Override
                public boolean acceptable(Coordinate coordinate) {
                    int x = coordinate.x();
                    int y = coordinate.y();
                    return x == 0 && y >= 0 && y < slotsCount;
                }

                @Override
                public Coordinate toCoordinate(int index) {
                    return coordinate(0, index);
                }

                @Override
                public Stream<Coordinate> availableCoordinates() {
                    return Stream.of(
                            coordinate(0, 0),
                            coordinate(0, 1),
                            coordinate(0, 2)
                    );
                }
            },
            new RowedPartitionCoordinateTranslator(PLAYER_INVENTORY_ROWS)
    );

    private static final CoordinateTranslator ANVIL = new CombinedCoordinateTranslator(
            Key.key(Advancedgui.NAMESPACE, "anvil"),
            new SimplePartitionCoordinateTranslator(3) {
                @Override
                public boolean acceptable(Coordinate coordinate) {
                    int x = coordinate.x();
                    int y = coordinate.y();
                    return x >= 0 && x < slotsCount && y == 0;
                }

                @Override
                public int toIndex(Coordinate coordinate) {
                    return coordinate.x();
                }

                @Override
                public Coordinate toCoordinate(int index) {
                    return coordinate(index, 0);
                }

                @Override
                public Stream<Coordinate> availableCoordinates() {
                    return Stream.of(
                            coordinate(0, 0),
                            coordinate(1, 0),
                            coordinate(2, 0)
                    );
                }
            },
            new RowedPartitionCoordinateTranslator(PLAYER_INVENTORY_ROWS)
    );
}
