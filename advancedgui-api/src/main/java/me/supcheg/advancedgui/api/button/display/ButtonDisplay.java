package me.supcheg.advancedgui.api.button.display;

import me.supcheg.advancedgui.api.builder.AbstractBuilder;
import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.api.button.description.Description;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;

public interface ButtonDisplay extends Buildable<ButtonDisplay, ButtonDisplay.Builder> {
    Key texture();

    Component name();

    Description description();

    interface Builder extends AbstractBuilder<ButtonDisplay> {

    }
}
