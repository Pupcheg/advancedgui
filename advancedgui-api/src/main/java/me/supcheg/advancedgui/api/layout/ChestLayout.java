package me.supcheg.advancedgui.api.layout;

import me.supcheg.advancedgui.api.coordinate.CoordinateTranslator;
import me.supcheg.advancedgui.api.coordinate.CoordinateTranslators;

public interface ChestLayout extends Layout<ChestLayout> {
    int rows();

    @Override
    default CoordinateTranslator coordinateTranslator() {
        return CoordinateTranslators.chestCoordinateTranslator(rows());
    }
}
