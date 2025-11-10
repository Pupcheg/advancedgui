package me.supcheg.advancedgui.api.button.template;

import me.supcheg.advancedgui.api.button.Button;
import me.supcheg.advancedgui.api.button.display.ButtonDisplayProvider;
import me.supcheg.advancedgui.api.button.interaction.ButtonInteraction;
import me.supcheg.advancedgui.api.coordinate.Coordinate;
import me.supcheg.advancedgui.api.lifecycle.LifecycleListenerRegistry;
import me.supcheg.advancedgui.api.sequence.collection.SequencedSortedSet;
import me.supcheg.advancedgui.api.sequence.collection.SequencedSortedSets;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

record ButtonTemplateImpl(
        Set<Coordinate> coordinates,
        SequencedSortedSet<ButtonInteraction> interactions,
        ButtonDisplayProvider displayProvider,
        LifecycleListenerRegistry<Button> lifecycleListenerRegistry
) implements ButtonTemplate {
    @Override
    public Builder toBuilder() {
        return new BuilderImpl(this);
    }

    static final class BuilderImpl implements ButtonTemplate.Builder {
        private final Set<Coordinate> coordinates;
        private final SequencedSortedSet<ButtonInteraction> interactions;
        private @Nullable ButtonDisplayProvider displayProvider;
        private @Nullable LifecycleListenerRegistry<Button> lifecycleListenerRegistry;

        BuilderImpl() {
            this.coordinates = new HashSet<>();
            this.interactions = SequencedSortedSets.create();
        }

        BuilderImpl(ButtonTemplateImpl impl) {
            this.coordinates = new HashSet<>(impl.coordinates);
            this.interactions = SequencedSortedSets.createCopy(impl.interactions);
            this.displayProvider = impl.displayProvider;
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
        public Builder interactions(SequencedSortedSet<ButtonInteraction> interactions) {
            Objects.requireNonNull(interactions, "interactions");
            this.interactions.clear();
            this.interactions.addAll(interactions);
            return this;
        }

        @Override
        public SequencedSortedSet<ButtonInteraction> interactions() {
            return interactions;
        }

        @Override
        public Builder displayProvider(ButtonDisplayProvider displayProvider) {
            Objects.requireNonNull(displayProvider, "displayProvider");
            this.displayProvider = displayProvider;
            return this;
        }

        @Nullable
        @Override
        public ButtonDisplayProvider displayProvider() {
            return displayProvider;
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
                    SequencedSortedSets.copyOf(interactions),
                    Objects.requireNonNull(displayProvider, "displayProvider"),
                    Objects.requireNonNull(lifecycleListenerRegistry, "lifecycleListenerRegistry")
            );
        }
    }

}
