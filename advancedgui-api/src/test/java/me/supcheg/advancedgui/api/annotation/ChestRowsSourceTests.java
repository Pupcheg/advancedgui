package me.supcheg.advancedgui.api.annotation;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;

import static me.supcheg.advancedgui.api.TestInventoryConstants.MAX_CHEST_INVENTORY_ROWS;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ChestRowsSourceTests {

    int runsCount;

    @Order(1)
    @Test
    void before() {
        runsCount = 0;
    }

    @Order(2)
    @ParameterizedTest
    @ChestRowsSource
    void run(int rows) {
        assertEquals(++runsCount, rows);
    }

    @Order(3)
    @Test
    void after() {
        assertEquals(MAX_CHEST_INVENTORY_ROWS, runsCount);
    }
}
