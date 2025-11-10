package me.supcheg.advancedgui.api.button.template;

import com.google.common.collect.Lists;
import me.supcheg.advancedgui.api.builder.AbstractBuilder;
import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.api.button.Button;
import me.supcheg.advancedgui.api.button.display.ButtonDisplay;
import me.supcheg.advancedgui.api.button.display.ButtonDisplayProvider;
import me.supcheg.advancedgui.api.button.interaction.ButtonInteraction;
import me.supcheg.advancedgui.api.coordinate.Coordinate;
import me.supcheg.advancedgui.api.lifecycle.Lifecycled;
import me.supcheg.advancedgui.api.sequence.collection.SequencedSortedSet;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Set;
import java.util.function.Consumer;

import static me.supcheg.advancedgui.api.button.display.SingleButtonDisplayProvider.singleButtonDisplayProvider;
import static me.supcheg.advancedgui.api.coordinate.Coordinate.coordinate;

public interface ButtonTemplate extends Buildable<ButtonTemplate, ButtonTemplate.Builder>, Lifecycled<Button> {

    static Builder button() {
        return new ButtonTemplateImpl.BuilderImpl();
    }

    static ButtonTemplate button(Consumer<Builder> consumer) {
        return Buildable.configureAndBuild(button(), consumer);
    }

    @Unmodifiable
    Set<Coordinate> coordinates();

    ButtonDisplayProvider displayProvider();

    @Unmodifiable
    SequencedSortedSet<ButtonInteraction> interactions();

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

        Builder interactions(SequencedSortedSet<ButtonInteraction> interactions);

        SequencedSortedSet<ButtonInteraction> interactions();

        Builder displayProvider(ButtonDisplayProvider displayProvider);

        default Builder display(ButtonDisplay display) {
            return displayProvider(singleButtonDisplayProvider(display));
        }

        @Nullable
        ButtonDisplayProvider displayProvider();
    }
}
