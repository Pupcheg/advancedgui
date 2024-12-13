package me.supcheg.advancedgui.api.button.template;

import me.supcheg.advancedgui.api.builder.AbstractBuilder;
import me.supcheg.advancedgui.api.button.Button;
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
        boolean enabled,
        boolean shown,
        @NotNull SortedSet<ButtonInteraction> interactions,
        @NotNull Key texture,
        @NotNull Component name,
        @NotNull Description description,
        @NotNull LifecycleListenerRegistry<Button> lifecycleListenerRegistry,
        boolean glowing
) implements ButtonTemplate {
    @NotNull
    @Override
    public Builder toBuilder() {
        return new BuilderImpl(this);
    }

    static final class BuilderImpl implements ButtonTemplate.Builder {
        private final Set<Coordinate> coordinates;
        private Boolean enabled;
        private Boolean shown;
        private final SortedSet<ButtonInteraction> interactions;
        private Key texture;
        private Component name;
        private Description description;
        private LifecycleListenerRegistry<Button> lifecycleListenerRegistry;
        private Boolean glowing;

        BuilderImpl() {
            this.coordinates = new HashSet<>();
            this.interactions = new TreeSet<>();
        }

        BuilderImpl(@NotNull ButtonTemplateImpl impl) {
            this.coordinates = impl.coordinates;
            this.enabled = impl.enabled;
            this.shown = impl.shown;
            this.interactions = new TreeSet<>(impl.interactions);
            this.texture = impl.texture;
            this.name = impl.name;
            this.description = impl.description;
            this.lifecycleListenerRegistry = impl.lifecycleListenerRegistry;
            this.glowing = impl.glowing;
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
        public Builder enabled(boolean value) {
            this.enabled = value;
            return this;
        }

        @Nullable
        @Override
        public Boolean enabled() {
            return enabled;
        }

        @NotNull
        @Override
        public Builder shown(boolean value) {
            shown = value;
            return this;
        }

        @Nullable
        @Override
        public Boolean shown() {
            return shown;
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
        public Builder glowing(boolean value) {
            this.glowing = value;
            return this;
        }

        @Nullable
        @Override
        public Boolean glowing() {
            return glowing;
        }


        @NotNull
        @Override
        public ButtonTemplate build() {
            return new ButtonTemplateImpl(
                    Set.copyOf(coordinates),
                    Objects.requireNonNull(enabled, "enabled"),
                    Objects.requireNonNull(shown, "shown"),
                    Collections.unmodifiableSortedSet(new TreeSet<>(interactions)),
                    Objects.requireNonNull(texture, "texture"),
                    Objects.requireNonNull(name, "name"),
                    Objects.requireNonNull(description, "description"),
                    Objects.requireNonNull(lifecycleListenerRegistry, "lifecycleListenerRegistry"),
                    Objects.requireNonNull(glowing, "glowing")
            );
        }
    }

}
