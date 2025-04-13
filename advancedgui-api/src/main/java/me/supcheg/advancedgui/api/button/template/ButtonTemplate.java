package me.supcheg.advancedgui.api.button.template;

import me.supcheg.advancedgui.api.builder.AbstractBuilder;
import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.api.button.Button;
import me.supcheg.advancedgui.api.button.attribute.ButtonAttribute;
import me.supcheg.advancedgui.api.button.description.Description;
import me.supcheg.advancedgui.api.button.interaction.ButtonInteraction;
import me.supcheg.advancedgui.api.coordinate.Coordinate;
import me.supcheg.advancedgui.api.lifecycle.Lifecycled;
import me.supcheg.advancedgui.api.util.CollectionUtil;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Set;
import java.util.SortedSet;
import java.util.function.Consumer;

import static me.supcheg.advancedgui.api.coordinate.Coordinate.coordinate;
import static me.supcheg.advancedgui.api.util.CollectionUtil.makeNoNullsList;

public interface ButtonTemplate extends Buildable<ButtonTemplate, ButtonTemplate.Builder>, Lifecycled<Button> {
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

    interface Builder extends AbstractBuilder<ButtonTemplate>, Lifecycled.Builder<Button, Builder> {

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
            return addCoordinates(Set.copyOf(makeNoNullsList(first, second, coordinates)));
        }

        @NotNull
        @Contract("_ -> this")
        Builder addCoordinates(@NotNull Set<Coordinate> coordinates);

        @NotNull
        @Contract("_ -> this")
        Builder coordinates(@NotNull Set<Coordinate> coordinates);

        @NotNull
        Set<Coordinate> coordinates();

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
        Set<ButtonAttribute> attributes();

        @NotNull
        Builder attributes(@NotNull Set<ButtonAttribute> attributes);

        @NotNull
        default Builder attributes(@NotNull ButtonAttribute attribute) {
            return attributes(Set.of(attribute));
        }

        @NotNull
        default Builder attributes(@NotNull ButtonAttribute first, @NotNull ButtonAttribute second,
                                   @NotNull ButtonAttribute @NotNull ... other) {
            return attributes(CollectionUtil.makeNoNullsSet(first, second, other));
        }

        @NotNull
        Builder addAttributes(@NotNull Set<ButtonAttribute> attributes);

        @NotNull
        default Builder addAttributes(@NotNull ButtonAttribute attribute) {
            return addAttributes(Set.of(attribute));
        }

        @NotNull
        default Builder addAttributes(@NotNull ButtonAttribute first, @NotNull ButtonAttribute second,
                                      @NotNull ButtonAttribute @NotNull ... other) {
            return addAttributes(CollectionUtil.makeNoNullsSet(first, second, other));
        }
    }
}
