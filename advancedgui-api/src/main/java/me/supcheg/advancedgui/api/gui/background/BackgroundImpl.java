package me.supcheg.advancedgui.api.gui.background;

import me.supcheg.advancedgui.api.builder.AbstractBuilder;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

record BackgroundImpl(
        @NotNull List<Key> locations
) implements Background {
    @NotNull
    @Override
    public Background.Builder toBuilder() {
        return new BuilderImpl(this);
    }

    static final class BuilderImpl implements Builder {
        private final List<Key> locations;

        BuilderImpl() {
            this.locations = new ArrayList<>();
        }

        BuilderImpl(@NotNull BackgroundImpl impl) {
            this.locations = new ArrayList<>(impl.locations);
        }

        @NotNull
        @Override
        public Builder addLocation(@NotNull Key location) {
            Objects.requireNonNull(location, "location");
            locations.add(location);
            return this;
        }

        @NotNull
        @Override
        public Builder locations(@NotNull List<Key> locations) {
            AbstractBuilder.replaceCollectionContents(this.locations, locations);
            return this;
        }

        @NotNull
        @Override
        public List<Key> locations() {
            return locations;
        }

        @NotNull
        @Override
        public Background build() {
            if (locations.isEmpty()) {
                throw new IllegalStateException("locations is empty");
            }
            return new BackgroundImpl(List.copyOf(locations));
        }
    }
}
