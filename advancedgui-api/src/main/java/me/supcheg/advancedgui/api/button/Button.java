package me.supcheg.advancedgui.api.button;

import com.google.common.collect.SortedMultiset;
import me.supcheg.advancedgui.api.button.attribute.ButtonAttribute;
import me.supcheg.advancedgui.api.button.description.Description;
import me.supcheg.advancedgui.api.button.interaction.ButtonInteraction;
import me.supcheg.advancedgui.api.coordinate.Coordinate;
import me.supcheg.advancedgui.api.lifecycle.Lifecycled;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Set;

public interface Button extends Lifecycled<Button> {
    Coordinate coordinate();

    @Unmodifiable
    SortedMultiset<ButtonInteraction> interactions();

    Key texture();

    Component name();

    Description description();

    @Unmodifiable
    Set<ButtonAttribute> attributes();

    default boolean hasAttribute(ButtonAttribute attribute) {
        return attributes().contains(attribute);
    }
}
