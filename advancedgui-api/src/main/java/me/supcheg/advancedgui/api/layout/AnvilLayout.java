package me.supcheg.advancedgui.api.layout;

import me.supcheg.advancedgui.api.coordinate.CoordinateTranslator;
import me.supcheg.advancedgui.api.coordinate.CoordinateTranslators;
import me.supcheg.advancedgui.api.layout.template.anvil.InputUpdateListener;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Queue;

public interface AnvilLayout extends Layout<AnvilLayout> {

    @Unmodifiable
    Queue<InputUpdateListener> inputUpdateListeners();

    @Override
    default CoordinateTranslator coordinateTranslator() {
        return CoordinateTranslators.anvilCoordinateTranslator();
    }
}
