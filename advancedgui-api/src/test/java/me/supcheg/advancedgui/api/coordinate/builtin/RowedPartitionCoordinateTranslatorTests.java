package me.supcheg.advancedgui.api.coordinate.builtin;

import org.junit.jupiter.api.Test;

import static me.supcheg.advancedgui.api.coordinate.Coordinate.coordinate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RowedPartitionCoordinateTranslatorTests {
    int MAX_CHEST_ROWS = 6;
    int PLAYER_INVENTORY_ROWS = 4;

    @Test
    void chest() {
        for (int rows = 1; rows <= MAX_CHEST_ROWS; rows++) {
            validateChest(rows);
        }
    }

    private void validateChest(int rows) {
        validateRowed(0, rows);
        validateRowed(rows, PLAYER_INVENTORY_ROWS);
    }

    private void validateRowed(int start, int rows) {
        var translator = new RowedPartitionCoordinateTranslator(start, rows);

        assertEquals(translator.slotsCount(), rows * 9);

        for (int y = start; y < (start + rows); y++) {
            for (int x = 0; x < 9; x++) {

                int index = y * 9 + x;
                var coord = coordinate(x, y);

                assertTrue(translator.acceptable(index));
                assertTrue(translator.acceptable(coord));

                assertEquals(coord, translator.toCoordinate(index), "index: " + index);
                assertEquals(index, translator.toIndex(coord), "coord: " + coord);
            }
        }
    }
}
