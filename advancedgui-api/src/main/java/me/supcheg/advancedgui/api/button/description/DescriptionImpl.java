package me.supcheg.advancedgui.api.button.description;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public record DescriptionImpl(
        @NotNull List<Component> lines
) implements Description {
    @NotNull
    @Override
    public Description.Builder toBuilder() {
        return new BuilderImpl(this);
    }

    static class BuilderImpl implements Builder {
        private final LinkedList<Component> lines;

        BuilderImpl() {
            this.lines = new LinkedList<>();
        }

        BuilderImpl(@NotNull DescriptionImpl impl) {
            this.lines = new LinkedList<>(impl.lines);
        }

        @NotNull
        @Override
        public List<Component> lines() {
            return lines;
        }

        @NotNull
        @Override
        public Builder addLines(@NotNull List<Component> lines) {
            Objects.requireNonNull(lines, "lines");
            this.lines.addAll(lines);
            return this;
        }

        @NotNull
        @Override
        public Builder lines(@NotNull List<Component> lines) {
            Objects.requireNonNull(lines, "lines");
            this.lines.clear();
            this.lines.addAll(lines);
            return this;
        }

        @NotNull
        @Override
        public Description build() {
            return new DescriptionImpl(
                    List.copyOf(lines)
            );
        }
    }
}
