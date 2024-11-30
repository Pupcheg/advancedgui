package me.supcheg.advancedgui.api.layout;

import me.supcheg.advancedgui.api.button.Button;
import me.supcheg.advancedgui.api.coordinate.CoordinateTranslator;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public interface Layout {
    @NotNull
    Set<? extends Button> buttons();

    @NotNull
    CoordinateTranslator coordinateTranslator();
}
