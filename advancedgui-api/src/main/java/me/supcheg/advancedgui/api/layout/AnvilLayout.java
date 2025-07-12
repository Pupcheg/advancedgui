package me.supcheg.advancedgui.api.layout;

import com.google.common.collect.SortedMultiset;
import me.supcheg.advancedgui.api.coordinate.CoordinateTranslator;
import me.supcheg.advancedgui.api.coordinate.CoordinateTranslators;
import me.supcheg.advancedgui.api.layout.template.anvil.InputUpdateListener;
import org.jetbrains.annotations.Unmodifiable;

public interface AnvilLayout extends Layout<AnvilLayout> {

    @Unmodifiable
    SortedMultiset<InputUpdateListener> inputUpdateListeners();

    @Override
    default CoordinateTranslator coordinateTranslator() {
        return CoordinateTranslators.anvilCoordinateTranslator();
    }
}
