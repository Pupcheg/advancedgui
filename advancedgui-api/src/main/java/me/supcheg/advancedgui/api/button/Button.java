package me.supcheg.advancedgui.api.button;

import me.supcheg.advancedgui.api.button.display.ButtonDisplayProvider;
import me.supcheg.advancedgui.api.button.interaction.ButtonInteraction;
import me.supcheg.advancedgui.api.coordinate.Coordinate;
import me.supcheg.advancedgui.api.lifecycle.Lifecycled;
import me.supcheg.advancedgui.api.sequence.collection.SequencedSortedSet;
import org.jetbrains.annotations.Unmodifiable;

public interface Button extends Lifecycled<Button> {
    Coordinate coordinate();

    @Unmodifiable
    SequencedSortedSet<ButtonInteraction> interactions();

    ButtonDisplayProvider displayProvider();
}
