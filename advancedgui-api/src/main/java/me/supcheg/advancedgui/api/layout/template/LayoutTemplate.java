package me.supcheg.advancedgui.api.layout.template;

import me.supcheg.advancedgui.api.builder.AbstractBuilder;
import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.api.button.template.ButtonTemplate;
import me.supcheg.advancedgui.api.coordinate.CoordinateTranslator;
import me.supcheg.advancedgui.api.layout.Layout;
import me.supcheg.advancedgui.api.lifecycle.Lifecycled;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Set;

public sealed interface LayoutTemplate<L extends Layout<L>, T extends LayoutTemplate<L, T, B>, B extends AbstractBuilder<T>>
        extends Buildable<T, B>, Lifecycled<L>
        permits AnvilLayoutTemplate, ChestLayoutTemplate {

    @Unmodifiable
    Set<ButtonTemplate> buttons();

    CoordinateTranslator coordinateTranslator();
}
