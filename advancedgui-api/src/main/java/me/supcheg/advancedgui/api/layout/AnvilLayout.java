package me.supcheg.advancedgui.api.layout;

import me.supcheg.advancedgui.api.coordinate.CoordinateTranslator;
import me.supcheg.advancedgui.api.coordinate.CoordinateTranslators;
import me.supcheg.advancedgui.api.layout.template.anvil.InputUpdateListener;
import me.supcheg.advancedgui.api.sequence.collection.SequencedSortedSet;
import org.jetbrains.annotations.Unmodifiable;

public interface AnvilLayout extends Layout<AnvilLayout> {

    @Unmodifiable
    SequencedSortedSet<InputUpdateListener> inputUpdateListeners();

    @Override
    default CoordinateTranslator coordinateTranslator() {
        return CoordinateTranslators.anvilCoordinateTranslator();
    }
}
