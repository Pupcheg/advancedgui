package me.supcheg.advancedgui.api.coordinate.builtin;

import me.supcheg.advancedgui.api.annotation.ChestRowsSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

import static me.supcheg.advancedgui.api.TestInventoryConstants.PLAYER_INVENTORY_ROWS;
import static me.supcheg.advancedgui.api.TestInventoryConstants.PLAYER_INVENTORY_SLOTS;
import static me.supcheg.advancedgui.api.TestInventoryConstants.SLOTS_IN_ROW;
import static me.supcheg.advancedgui.api.coordinate.builtin.BuiltinCoordinateTranslators.anvil;
import static me.supcheg.advancedgui.api.coordinate.builtin.BuiltinCoordinateTranslators.chest;
import static me.supcheg.advancedgui.api.coordinate.builtin.BuiltinCoordinateTranslators.furnace;
import static me.supcheg.advancedgui.api.coordinate.util.CoordinateIterable.matrix;
import static me.supcheg.advancedgui.api.coordinate.util.CoordinateIterable.rows;
import static org.assertj.core.api.Assertions.assertThat;

class BuiltinCoordinateTranslatorsTests {

    @ParameterizedTest
    @ChestRowsSource
    void chestTranslatorSlotsCount(int rows) {
        var translator = chest(rows);
        assertThat(translator.slotsCount())
                .isEqualTo(rows * SLOTS_IN_ROW + PLAYER_INVENTORY_SLOTS)
                .isEqualTo(translator.availableCoordinates().count());
    }

    @ParameterizedTest
    @ChestRowsSource
    void chestTranslator(int rows) {
        var translator = chest(rows);
        assertThat(translator.availableCoordinates())
                .hasSameElementsAs(matrix(
                        rows(0, rows),
                        rows(rows, PLAYER_INVENTORY_ROWS)
                ));
    }

    @Test
    void anvilTranslatorSlotsCount() {
        var translator = anvil();
        int anvilSlots = 3;

        assertThat(translator.slotsCount())
                .isEqualTo(anvilSlots + PLAYER_INVENTORY_SLOTS)
                .isEqualTo(translator.availableCoordinates().count());
    }

    @Test
    void anvilTranslator() {
        assertThat(anvil().availableCoordinates())
                .hasSameElementsAs(matrix(
                        new int[][]{
                                {0, 0}, /* + */ {1, 0}, /* -> */ {2, 0}
                        },
                        rows(1, PLAYER_INVENTORY_ROWS)
                ));
    }

    @Test
    void furnaceTranslatorSlotsCount() {
        var translator = furnace();
        int furnaceSlots = 3;

        assertThat(translator.slotsCount())
                .isEqualTo(furnaceSlots + PLAYER_INVENTORY_SLOTS)
                .isEqualTo(translator.availableCoordinates().count());
    }

    @Test
    void furnaceTranslator() {
        assertThat(furnace().availableCoordinates())
                .hasSameElementsAs(matrix(
                        new int[][]{
                                {0, 0},
                                /*🔥*/   /* -> */    {0, 1},
                                {0, 2}
                        },
                        rows(3, PLAYER_INVENTORY_ROWS)
                ));
    }
}
