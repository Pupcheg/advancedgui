package me.supcheg.advancedgui.api.button.template;

import com.google.common.collect.Lists;
import com.google.common.collect.SortedMultiset;
import me.supcheg.advancedgui.api.builder.AbstractBuilder;
import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.api.button.Button;
import me.supcheg.advancedgui.api.button.attribute.ButtonAttribute;
import me.supcheg.advancedgui.api.button.description.Description;
import me.supcheg.advancedgui.api.button.interaction.ButtonInteraction;
import me.supcheg.advancedgui.api.coordinate.Coordinate;
import me.supcheg.advancedgui.api.lifecycle.Lifecycled;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.examination.Examinable;
import net.kyori.examination.ExaminableProperty;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static me.supcheg.advancedgui.api.coordinate.Coordinate.coordinate;

public interface ButtonTemplate extends Examinable, Buildable<ButtonTemplate, ButtonTemplate.Builder>, Lifecycled<Button> {

    static Builder button() {
        return new ButtonTemplateImpl.BuilderImpl();
    }

    static ButtonTemplate button(Consumer<Builder> consumer) {
        return Buildable.configureAndBuild(button(), consumer);
    }

    @Unmodifiable
    Set<Coordinate> coordinates();

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

    @Override
    default Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(
                ExaminableProperty.of("coordinates", coordinates()),
                ExaminableProperty.of("interactions", interactions()),
                ExaminableProperty.of("texture", texture()),
                ExaminableProperty.of("name", name()),
                ExaminableProperty.of("description", description()),
                ExaminableProperty.of("attributes", attributes()),
                ExaminableProperty.of("lifecycleListenerRegistry", lifecycleListenerRegistry())
        );
    }

    interface Builder extends AbstractBuilder<ButtonTemplate>, Lifecycled.Builder<Button, Builder> {

        Builder addCoordinate(Coordinate coordinate);

        default Builder addCoordinate(int x, int y) {
            return addCoordinate(coordinate(x, y));
        }

        default Builder addCoordinates(Coordinate first, Coordinate second, Coordinate... coordinates) {
            return addCoordinates(Set.copyOf(Lists.asList(first, second, coordinates)));
        }

        Builder addCoordinates(Set<Coordinate> coordinates);

        Builder coordinates(Set<Coordinate> coordinates);

        Set<Coordinate> coordinates();

        default Builder addInteraction(Consumer<ButtonInteraction.Builder> consumer) {
            return addInteraction(ButtonInteraction.buttonInteraction(consumer));
        }

        Builder addInteraction(ButtonInteraction interaction);

        Builder interactions(SortedMultiset<ButtonInteraction> interactions);

        SortedMultiset<ButtonInteraction> interactions();

        Builder texture(Key location);

        @Nullable
        Key texture();

        Builder name(Component name);

        @Nullable
        Component name();

        Builder description(Description description);

        default Builder description(Consumer<Description.Builder> consumer) {
            return description(Description.description(consumer));
        }

        @Nullable
        Description description();

        Set<ButtonAttribute> attributes();

        Builder attributes(Set<ButtonAttribute> attributes);

        default Builder attributes(ButtonAttribute attribute) {
            return attributes(Set.of(attribute));
        }

        default Builder attributes(ButtonAttribute first, ButtonAttribute second, ButtonAttribute... other) {
            return attributes(Set.copyOf(Lists.asList(first, second, other)));
        }

        Builder addAttributes(Set<ButtonAttribute> attributes);

        default Builder addAttributes(ButtonAttribute attribute) {
            return addAttributes(Set.of(attribute));
        }

        default Builder addAttributes(ButtonAttribute first, ButtonAttribute second, ButtonAttribute... other) {
            return addAttributes(Set.copyOf(Lists.asList(first, second, other)));
        }
    }
}
