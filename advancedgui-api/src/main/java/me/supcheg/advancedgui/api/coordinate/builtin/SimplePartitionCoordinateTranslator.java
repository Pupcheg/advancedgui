package me.supcheg.advancedgui.api.coordinate.builtin;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class SimplePartitionCoordinateTranslator implements PartitionCoordinateTranslator {
    protected final int slotsCount;

    @Override
    public boolean acceptable(int index) {
        return index >= 0 && index < slotsCount;
    }

    @Override
    public int slotsCount() {
        return slotsCount;
    }
}
