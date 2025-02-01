package me.supcheg.advancedgui.api;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestInventoryConstants {
    public static final int SLOTS_IN_ROW = 9;

    public static final int PLAYER_INVENTORY_ROWS = 4;
    public static final int PLAYER_INVENTORY_SLOTS = PLAYER_INVENTORY_ROWS * SLOTS_IN_ROW;

    public static final int MAX_CHEST_INVENTORY_ROWS = 6;
}
