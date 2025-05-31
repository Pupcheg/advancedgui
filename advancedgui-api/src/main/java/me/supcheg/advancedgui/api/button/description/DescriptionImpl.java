package me.supcheg.advancedgui.api.button.description;

import net.kyori.adventure.text.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public record DescriptionImpl(
        List<Component> lines
) implements Description {
    @Override
    public Description.Builder toBuilder() {
        return new BuilderImpl(this);
    }

    static class BuilderImpl implements Builder {
        private final LinkedList<Component> lines;

        BuilderImpl() {
            this.lines = new LinkedList<>();
        }

        BuilderImpl(DescriptionImpl impl) {
            this.lines = new LinkedList<>(impl.lines);
        }

        @Override
        public List<Component> lines() {
            return lines;
        }

        @Override
        public Builder addLines(List<Component> lines) {
            Objects.requireNonNull(lines, "lines");
            this.lines.addAll(lines);
            return this;
        }

        @Override
        public Builder lines(List<Component> lines) {
            Objects.requireNonNull(lines, "lines");
            this.lines.clear();
            this.lines.addAll(lines);
            return this;
        }

        @Override
        public Description build() {
            return new DescriptionImpl(
                    List.copyOf(lines)
            );
        }
    }
}
