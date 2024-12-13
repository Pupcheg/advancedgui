package me.supcheg.advancedgui.api.button;

import me.supcheg.advancedgui.api.coordinate.Coordinate;
import me.supcheg.advancedgui.api.lifecycle.Lifecycled;
import org.jetbrains.annotations.NotNull;

public interface Button extends Lifecycled<Button> {
    @NotNull
    Coordinate coordinate();
}
