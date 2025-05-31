package me.supcheg.advancedgui.api.gui.background;

import net.kyori.adventure.key.Key;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

record BackgroundImpl(
        List<Key> locations
) implements Background {
    @Override
    public Background.Builder toBuilder() {
        return new BuilderImpl(this);
    }

    static final class BuilderImpl implements Builder {
        private final List<Key> locations;

        BuilderImpl() {
            this.locations = new ArrayList<>();
        }

        BuilderImpl(BackgroundImpl impl) {
            this.locations = new ArrayList<>(impl.locations);
        }

        @Override
        public Builder addLocation(Key location) {
            Objects.requireNonNull(location, "location");
            locations.add(location);
            return this;
        }

        @Override
        public Builder locations(List<Key> locations) {
            Objects.requireNonNull(locations, "locations");
            this.locations.clear();
            this.locations.addAll(locations);
            return this;
        }

        @Override
        public List<Key> locations() {
            return locations;
        }

        @Override
        public Background build() {
            return new BackgroundImpl(
                    List.copyOf(locations)
            );
        }
    }
}
