package me.supcheg.advancedgui.api.button.template;

import me.supcheg.advancedgui.api.button.Button;
import me.supcheg.advancedgui.api.button.attribute.ButtonAttribute;
import me.supcheg.advancedgui.api.button.description.Description;
import me.supcheg.advancedgui.api.button.interaction.ButtonInteraction;
import me.supcheg.advancedgui.api.coordinate.Coordinate;
import me.supcheg.advancedgui.api.lifecycle.LifecycleListenerRegistry;
import me.supcheg.advancedgui.api.util.Queues;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

record ButtonTemplateImpl(
        @NotNull Set<Coordinate> coordinates,
        @NotNull Queue<ButtonInteraction> interactions,
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
        private final Queue<ButtonInteraction> interactions;
        private final Set<ButtonAttribute> attributes;
        private Key texture;
        private Component name;
        private Description description;
        private LifecycleListenerRegistry<Button> lifecycleListenerRegistry;

        BuilderImpl() {
            this.coordinates = new HashSet<>();
            this.interactions = new PriorityQueue<>();
            this.attributes = new HashSet<>();
        }

        BuilderImpl(@NotNull ButtonTemplateImpl impl) {
            this.coordinates = new HashSet<>(impl.coordinates);
            this.interactions = new PriorityQueue<>(impl.interactions);
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
        public Builder addCoordinates(@NotNull Set<Coordinate> coordinates) {
            this.coordinates.addAll(coordinates);
            return this;
        }

        @NotNull
        @Override
        public Builder coordinates(@NotNull Set<Coordinate> coordinates) {
            Objects.requireNonNull(coordinates, "coordinates");
            this.coordinates.clear();
            this.coordinates.addAll(coordinates);
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
        public Builder interactions(@NotNull Queue<ButtonInteraction> interactions) {
            Objects.requireNonNull(interactions, "interactions");
            this.interactions.clear();
            this.interactions.addAll(interactions);
            return this;
        }

        @NotNull
        @Override
        public Queue<ButtonInteraction> interactions() {
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
            Objects.requireNonNull(attributes, "attributes");
            this.attributes.clear();
            this.attributes.addAll(attributes);
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
                    Queues.copyOf(interactions),
                    Objects.requireNonNull(texture, "texture"),
                    Objects.requireNonNull(name, "name"),
                    Objects.requireNonNull(description, "description"),
                    Set.copyOf(attributes),
                    Objects.requireNonNull(lifecycleListenerRegistry, "lifecycleListenerRegistry")
            );
        }
    }

}
