package me.supcheg.advancedgui.api.button.template;

import me.supcheg.advancedgui.api.builder.AbstractBuilder;
import me.supcheg.advancedgui.api.button.Button;
import me.supcheg.advancedgui.api.button.attribute.ButtonAttribute;
import me.supcheg.advancedgui.api.button.description.Description;
import me.supcheg.advancedgui.api.button.interaction.ButtonInteraction;
import me.supcheg.advancedgui.api.coordinate.Coordinate;
import me.supcheg.advancedgui.api.lifecycle.LifecycleListenerRegistry;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

record ButtonTemplateImpl(
        @NotNull Set<Coordinate> coordinates,
        @NotNull SortedSet<ButtonInteraction> interactions,
        @NotNull Key texture,
        @NotNull Component name,
        @NotNull Description description,
        @NotNull Set<ButtonAttribute> attributes,
        @NotNull LifecycleListenerRegistry<Button> lifecycleListenerRegistry
) implements ButtonTemplate {
    @NotNull
    @Override
    public Builder toBuilder() {
        return new BuilderImpl(this);
    }

    static final class BuilderImpl implements ButtonTemplate.Builder {
        private final Set<Coordinate> coordinates;
        private final SortedSet<ButtonInteraction> interactions;
        private final Set<ButtonAttribute> attributes;
        private Key texture;
        private Component name;
        private Description description;
        private LifecycleListenerRegistry<Button> lifecycleListenerRegistry;

        BuilderImpl() {
            this.coordinates = new HashSet<>();
            this.interactions = new TreeSet<>();
            this.attributes = new HashSet<>();
        }

        BuilderImpl(@NotNull ButtonTemplateImpl impl) {
            this.coordinates = new HashSet<>(impl.coordinates);
            this.interactions = new TreeSet<>(impl.interactions);
            this.attributes = new HashSet<>(impl.attributes);
            this.texture = impl.texture;
            this.name = impl.name;
            this.description = impl.description;
            this.lifecycleListenerRegistry = impl.lifecycleListenerRegistry;
        }

        @NotNull
        @Override
        public Builder addCoordinate(@NotNull Coordinate coordinate) {
            Objects.requireNonNull(coordinate, "coordinate");
            coordinates.add(coordinate);
            return this;
        }

        @NotNull
        @Override
        public Builder addCoordinates(@NotNull Collection<Coordinate> coordinates) {
            this.coordinates.addAll(coordinates);
            return this;
        }

        @NotNull
        @Override
        public Builder coordinates(@NotNull Collection<Coordinate> coordinates) {
            AbstractBuilder.replaceCollectionContents(this.coordinates, coordinates);
            return this;
        }

        @NotNull
        @Override
        public Set<Coordinate> coordinates() {
            return coordinates;
        }

        @NotNull
        @Override
        public Builder addInteraction(@NotNull ButtonInteraction interaction) {
            Objects.requireNonNull(interactions, "interactions");
            interactions.add(interaction);
            return this;
        }

        @NotNull
        @Override
        public Builder interactions(@NotNull SortedSet<ButtonInteraction> interactions) {
            AbstractBuilder.replaceCollectionContents(this.interactions, interactions);
            return this;
        }

        @NotNull
        @Override
        public SortedSet<ButtonInteraction> interactions() {
            return interactions;
        }

        @NotNull
        @Override
        public Builder texture(@NotNull Key location) {
            Objects.requireNonNull(location, "location");
            this.texture = location;
            return this;
        }

        @Nullable
        @Override
        public Key texture() {
            return texture;
        }

        @NotNull
        @Override
        public Builder name(@NotNull Component name) {
            this.name = name;
            return this;
        }

        @Nullable
        @Override
        public Component name() {
            return name;
        }

        @NotNull
        @Override
        public Builder description(@NotNull Description description) {
            Objects.requireNonNull(description, "description");
            this.description = description;
            return this;
        }

        @Nullable
        @Override
        public Description description() {
            return description;
        }

        @NotNull
        @Override
        public Set<ButtonAttribute> attributes() {
            return attributes;
        }

        @NotNull
        @Override
        public Builder attributes(@NotNull Set<ButtonAttribute> attributes) {
            AbstractBuilder.replaceCollectionContents(this.attributes, attributes);
            return this;
        }

        @NotNull
        @Override
        public Builder addAttributes(@NotNull Set<ButtonAttribute> attributes) {
            this.attributes.addAll(attributes);
            return this;
        }

        @Nullable
        @Override
        public LifecycleListenerRegistry<Button> lifecycleListenerRegistry() {
            return lifecycleListenerRegistry;
        }

        @NotNull
        @Override
        public Builder lifecycleListenerRegistry(@NotNull LifecycleListenerRegistry<Button> lifecycleListenerRegistry) {
            Objects.requireNonNull(lifecycleListenerRegistry, "lifecycleListenerRegistry");
            this.lifecycleListenerRegistry = lifecycleListenerRegistry;
            return this;
        }

        @NotNull
        @Override
        public ButtonTemplate build() {
            return new ButtonTemplateImpl(
                    Set.copyOf(coordinates),
                    Collections.unmodifiableSortedSet(new TreeSet<>(interactions)),
                    Objects.requireNonNull(texture, "texture"),
                    Objects.requireNonNull(name, "name"),
                    Objects.requireNonNull(description, "description"),
                    Set.copyOf(attributes),
                    Objects.requireNonNull(lifecycleListenerRegistry, "lifecycleListenerRegistry")
            );
        }
    }

}
