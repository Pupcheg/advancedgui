package me.supcheg.advancedgui.api.button.display;

import com.google.common.collect.Iterators;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

record LoopedButtonDisplayProviderImpl(
        List<? extends ButtonDisplay> displays,
        Duration switchDuration
) implements LoopedButtonDisplayProvider {
    @Override
    public Iterator<? extends ButtonDisplay> displaysLoop() {
        return Iterators.cycle(displays);
    }

    @Override
    public Builder toBuilder() {
        return new BuilderImpl(this);
    }

    static class BuilderImpl implements Builder {
        private final List<ButtonDisplay> displays;
        private @Nullable Duration switchDuration;

        BuilderImpl() {
            this.displays = new ArrayList<>();
        }

        BuilderImpl(LoopedButtonDisplayProviderImpl impl) {
            this.displays = new ArrayList<>(impl.displays);
            this.switchDuration = impl.switchDuration;
        }

        @Override
        public Builder addDisplay(ButtonDisplay display) {
            Objects.requireNonNull(display, "display");
            this.displays.add(display);
            return this;
        }

        @Override
        public Builder addDisplays(List<ButtonDisplay> displays) {
            Objects.requireNonNull(displays, "displays");
            this.displays.addAll(displays);
            return this;
        }

        @Override
        public Builder displays(List<ButtonDisplay> displays) {
            Objects.requireNonNull(displays, "displays");
            this.displays.clear();
            this.displays.addAll(displays);
            return this;
        }

        @Override
        public List<ButtonDisplay> displays() {
            return displays;
        }

        @Override
        public Builder switchDuration(Duration switchDuration) {
            Objects.requireNonNull(switchDuration, "switchDuration");
            this.switchDuration = switchDuration;
            return this;
        }

        @Nullable
        @Override
        public Duration switchDuration() {
            return switchDuration;
        }

        @Override
        public LoopedButtonDisplayProvider build() {
            return new LoopedButtonDisplayProviderImpl(
                    List.copyOf(displays),
                    Objects.requireNonNull(switchDuration, "switchDuration")
            );
        }
    }
}
