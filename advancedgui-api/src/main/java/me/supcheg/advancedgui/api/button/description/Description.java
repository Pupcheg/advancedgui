package me.supcheg.advancedgui.api.button.description;

import com.google.common.collect.Lists;
import me.supcheg.advancedgui.api.builder.AbstractBuilder;
import me.supcheg.advancedgui.api.builder.Buildable;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;
import java.util.function.Consumer;

public interface Description extends Buildable<Description, Description.Builder> {

    static Builder description() {
        return new DescriptionImpl.BuilderImpl();
    }

    static Description description(Consumer<Builder> consumer) {
        return Buildable.configureAndBuild(description(), consumer);
    }

    @Unmodifiable
    List<Component> lines();

    interface Builder extends AbstractBuilder<Description> {

        List<Component> lines();

        Builder addLines(List<Component> lines);

        default Builder addLines(Component line) {
            return addLines(List.of(line));
        }

        default Builder addLines(Component first, Component second, Component... other) {
            return addLines(Lists.asList(first, second, other));
        }

        Builder lines(List<Component> lines);

        default Builder lines(Component line) {
            return lines(List.of(line));
        }

        default Builder lines(Component first, Component second, Component... other) {
            return lines(Lists.asList(first, second, other));
        }
    }
}
