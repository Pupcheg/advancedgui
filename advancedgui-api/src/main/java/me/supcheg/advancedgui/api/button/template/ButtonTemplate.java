package me.supcheg.advancedgui.api.button.template;

import me.supcheg.advancedgui.api.builder.AbstractBuilder;
import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.api.button.description.Description;
import me.supcheg.advancedgui.api.button.interaction.ButtonInteraction;
import me.supcheg.advancedgui.api.button.tick.ButtonTicker;
import me.supcheg.advancedgui.api.coordinate.Coordinate;
import me.supcheg.advancedgui.api.sequence.collection.MutablePositionedCollection;
import me.supcheg.advancedgui.api.sequence.collection.PositionedCollection;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;
import java.util.Set;
import java.util.SortedSet;
import java.util.function.Consumer;

import static me.supcheg.advancedgui.api.button.tick.ButtonTicker.buttonTicker;
import static me.supcheg.advancedgui.api.coordinate.Coordinate.coordinate;
import static me.supcheg.advancedgui.api.util.CollectionUtil.makeNoNullsList;

public interface ButtonTemplate extends Buildable<ButtonTemplate, ButtonTemplate.Builder> {
    @NotNull
    @Contract("-> new")
    static Builder button() {
        return new ButtonTemplateImpl.BuilderImpl();
    }

    @NotNull
    @Contract("_ -> new")
    static ButtonTemplate button(@NotNull Consumer<Builder> consumer) {
        return Buildable.configureAndBuild(button(), consumer);
    }

    @NotNull
    @Unmodifiable
    Set<Coordinate> coordinates();

    boolean enabled();

    default boolean disabled() {
        return !enabled();
    }

    boolean shown();

    default boolean hidden() {
        return !shown();
    }

    @NotNull
    @Unmodifiable
    SortedSet<ButtonInteraction> interactions();

    @NotNull
    Key texture();

    @NotNull
    Component name();

    @NotNull
    Description description();

    boolean enchanted();

    @NotNull
    @Unmodifiable
    PositionedCollection<ButtonTicker> tickers();

    @NotNull
    @Contract("-> new")
    Builder toBuilder();

    interface Builder extends AbstractBuilder<ButtonTemplate> {

        @NotNull
        @Contract("_ -> this")
        Builder addCoordinate(@NotNull Coordinate coordinate);

        @NotNull
        @Contract("_, _ -> this")
        default Builder addCoordinate(int x, int y) {
            return addCoordinate(coordinate(x, y));
        }

        @NotNull
        @Contract("_, _, _ -> this")
        default Builder addCoordinates(@NotNull Coordinate first, @NotNull Coordinate second,
                                       @NotNull Coordinate @NotNull ... coordinates) {
            return addCoordinates(makeNoNullsList(first, second, coordinates));
        }

        @NotNull
        @Contract("_ -> this")
        Builder addCoordinates(@NotNull Collection<Coordinate> coordinates);

        @NotNull
        @Contract("_ -> this")
        Builder coordinates(@NotNull Collection<Coordinate> coordinates);

        @NotNull
        Set<Coordinate> coordinates();

        @NotNull
        @Contract("_ -> this")
        Builder enabled(boolean value);

        @Nullable
        Boolean enabled();

        @NotNull
        @Contract("_ -> this")
        Builder shown(boolean value);

        @Nullable
        Boolean shown();

        @NotNull
        @Contract("_ -> this")
        default Builder addInteraction(@NotNull Consumer<ButtonInteraction.Builder> consumer) {
            return addInteraction(ButtonInteraction.buttonInteraction(consumer));
        }

        @NotNull
        @Contract("_ -> this")
        Builder addInteraction(@NotNull ButtonInteraction interaction);

        @NotNull
        @Contract("_ -> this")
        Builder interactions(@NotNull SortedSet<ButtonInteraction> interactions);

        @NotNull
        SortedSet<ButtonInteraction> interactions();

        @NotNull
        @Contract("_ -> this")
        Builder texture(@NotNull Key location);

        @Nullable
        Key texture();

        @NotNull
        @Contract("_ -> this")
        Builder name(@NotNull Component name);

        @Nullable
        Component name();

        @NotNull
        @Contract("_ -> this")
        Builder description(@NotNull Description description);

        @NotNull
        @Contract("_ -> this")
        default Builder description(@NotNull Consumer<Description.Builder> consumer) {
            return description(Description.description(consumer));
        }

        @Nullable
        Description description();

        @NotNull
        @Contract("_ -> this")
        default Builder addTicker(@NotNull ButtonTicker.Builder builder) {
            return addTicker(builder.build());
        }

        @NotNull
        @Contract("_ -> this")
        default Builder addTicker(@NotNull Consumer<ButtonTicker.Builder> consumer) {
            return addTicker(buttonTicker(consumer));
        }

        @NotNull
        @Contract("_ -> this")
        Builder addTicker(@NotNull ButtonTicker ticker);

        @NotNull
        MutablePositionedCollection<ButtonTicker> tickers();

        @NotNull
        @Contract("_ -> this")
        Builder enchanted(boolean value);

        @Nullable
        Boolean enchanted();

        @NotNull
        @Contract("-> new")
        @Override
        ButtonTemplate build();
    }
}
