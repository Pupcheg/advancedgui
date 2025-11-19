package me.supcheg.advancedgui.api.button.display;

import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.api.button.attribute.ButtonAttribute;
import me.supcheg.advancedgui.api.button.description.Description;
import me.supcheg.advancedgui.code.RecordInterface;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Set;
import java.util.function.Consumer;

@RecordInterface
public interface ButtonDisplay extends Buildable<ButtonDisplay, ButtonDisplayBuilder> {

    static ButtonDisplayBuilder buttonDisplay() {
        return new ButtonDisplayBuilderImpl();
    }

    static ButtonDisplay buttonDisplay(Consumer<ButtonDisplayBuilder> consumer) {
        return Buildable.configureAndBuild(buttonDisplay(), consumer);
    }

    Key texture();

    Component name();

    Description description();

    @Unmodifiable
    Set<ButtonAttribute> attributes();

    default boolean hasAttribute(ButtonAttribute attribute) {
        return attributes().contains(attribute);
    }
}
