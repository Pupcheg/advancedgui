package me.supcheg.advancedgui.api.button;

import me.supcheg.advancedgui.api.button.attribute.ButtonAttribute;
import me.supcheg.advancedgui.api.button.description.Description;
import me.supcheg.advancedgui.api.button.interaction.ButtonInteraction;
import me.supcheg.advancedgui.api.coordinate.Coordinate;
import me.supcheg.advancedgui.api.lifecycle.Lifecycled;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Set;
import java.util.SortedSet;

public interface Button extends Lifecycled<Button> {
    @NotNull
    Coordinate coordinate();

    @NotNull
    @Unmodifiable
    SortedSet<ButtonInteraction> interactions();

    @NotNull
    Key texture();

    @NotNull
    Component name();

    @NotNull
    Description description();

    @NotNull
    @Unmodifiable
    Set<ButtonAttribute> attributes();

    default boolean hasAttribute(@NotNull ButtonAttribute attribute) {
        return attributes().contains(attribute);
    }
}
