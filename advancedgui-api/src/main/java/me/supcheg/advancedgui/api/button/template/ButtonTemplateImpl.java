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
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.HashSet;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

record ButtonTemplateImpl(
        Set<Coordinate> coordinates,
        Queue<ButtonInteraction> interactions,
        Key texture,
        Component name,
        Description description,
        Set<ButtonAttribute> attributes,
        LifecycleListenerRegistry<Button> lifecycleListenerRegistry
) implements ButtonTemplate {
    @Override
    public Builder toBuilder() {
        return new BuilderImpl(this);
    }

    static final class BuilderImpl implements ButtonTemplate.Builder {
        private final Set<Coordinate> coordinates;
        private final Queue<ButtonInteraction> interactions;
        private final Set<ButtonAttribute> attributes;
        private @Nullable Key texture;
        private @Nullable Component name;
        private @Nullable Description description;
        private @Nullable LifecycleListenerRegistry<Button> lifecycleListenerRegistry;

        BuilderImpl() {
            this.coordinates = new HashSet<>();
            this.interactions = new PriorityQueue<>();
            this.attributes = new HashSet<>();
        }

        BuilderImpl(ButtonTemplateImpl impl) {
            this.coordinates = new HashSet<>(impl.coordinates);
            this.interactions = new PriorityQueue<>(impl.interactions);
            this.attributes = new HashSet<>(impl.attributes);
            this.texture = impl.texture;
            this.name = impl.name;
            this.description = impl.description;
            this.lifecycleListenerRegistry = impl.lifecycleListenerRegistry;
        }

        @Override
        public Builder addCoordinate(Coordinate coordinate) {
            Objects.requireNonNull(coordinate, "coordinate");
            coordinates.add(coordinate);
            return this;
        }

        @Override
        public Builder addCoordinates(Set<Coordinate> coordinates) {
            this.coordinates.addAll(coordinates);
            return this;
        }

        @Override
        public Builder coordinates(Set<Coordinate> coordinates) {
            Objects.requireNonNull(coordinates, "coordinates");
            this.coordinates.clear();
            this.coordinates.addAll(coordinates);
            return this;
        }

        @Override
        public Set<Coordinate> coordinates() {
            return coordinates;
        }

        @Override
        public Builder addInteraction(ButtonInteraction interaction) {
            Objects.requireNonNull(interactions, "interactions");
            interactions.add(interaction);
            return this;
        }

        @Override
        public Builder interactions(Queue<ButtonInteraction> interactions) {
            Objects.requireNonNull(interactions, "interactions");
            this.interactions.clear();
            this.interactions.addAll(interactions);
            return this;
        }

        @Override
        public Queue<ButtonInteraction> interactions() {
            return interactions;
        }

        @Override
        public Builder texture(Key location) {
            Objects.requireNonNull(location, "location");
            this.texture = location;
            return this;
        }

        @Override
        @Nullable
        public Key texture() {
            return texture;
        }

        @Override
        public Builder name(Component name) {
            this.name = name;
            return this;
        }

        @Override
        @Nullable
        public Component name() {
            return name;
        }

        @Override
        public Builder description(Description description) {
            Objects.requireNonNull(description, "description");
            this.description = description;
            return this;
        }

        @Override
        @Nullable
        public Description description() {
            return description;
        }

        @Override
        public Set<ButtonAttribute> attributes() {
            return attributes;
        }

        @Override
        public Builder attributes(Set<ButtonAttribute> attributes) {
            Objects.requireNonNull(attributes, "attributes");
            this.attributes.clear();
            this.attributes.addAll(attributes);
            return this;
        }

        @Override
        public Builder addAttributes(Set<ButtonAttribute> attributes) {
            this.attributes.addAll(attributes);
            return this;
        }

        @Override
        @Nullable
        public LifecycleListenerRegistry<Button> lifecycleListenerRegistry() {
            return lifecycleListenerRegistry;
        }

        @Override
        public Builder lifecycleListenerRegistry(LifecycleListenerRegistry<Button> lifecycleListenerRegistry) {
            Objects.requireNonNull(lifecycleListenerRegistry, "lifecycleListenerRegistry");
            this.lifecycleListenerRegistry = lifecycleListenerRegistry;
            return this;
        }

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
