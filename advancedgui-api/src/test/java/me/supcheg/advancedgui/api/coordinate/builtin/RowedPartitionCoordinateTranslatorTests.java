package me.supcheg.advancedgui.api.coordinate.builtin;

import me.supcheg.advancedgui.api.annotation.ChestRowsSource;
import org.junit.jupiter.params.ParameterizedTest;

import static me.supcheg.advancedgui.api.TestInventoryConstants.PLAYER_INVENTORY_ROWS;
import static me.supcheg.advancedgui.api.TestInventoryConstants.SLOTS_IN_ROW;
import static me.supcheg.advancedgui.api.coordinate.Coordinate.coordinate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RowedPartitionCoordinateTranslatorTests {

    @ParameterizedTest
    @ChestRowsSource
    void chest(int rows) {
        validateChest(rows);
    }

    private void validateChest(int rows) {
        validateRowed(0, rows);
        validateRowed(rows, PLAYER_INVENTORY_ROWS);
    }

    private void validateRowed(int start, int rows) {
        var translator = new RowedPartitionCoordinateTranslator(start, rows);

        assertEquals(translator.slotsCount(), rows * SLOTS_IN_ROW);

        for (int y = start; y < (start + rows); y++) {
            for (int x = 0; x < SLOTS_IN_ROW; x++) {

                int index = y * SLOTS_IN_ROW + x;
                var coord = coordinate(x, y);

                assertTrue(translator.acceptable(index));
                assertTrue(translator.acceptable(coord));

                assertEquals(coord, translator.toCoordinate(index), "index: " + index);
                assertEquals(index, translator.toIndex(coord), "coord: " + coord);
            }
        }
    }
}
