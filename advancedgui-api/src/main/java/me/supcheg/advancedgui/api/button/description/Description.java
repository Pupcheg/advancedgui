package me.supcheg.advancedgui.api.button.description;

import me.supcheg.advancedgui.api.builder.AbstractBuilder;
import me.supcheg.advancedgui.api.builder.Buildable;
import net.kyori.adventure.text.Component;
import net.kyori.examination.Examinable;
import net.kyori.examination.ExaminableProperty;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static me.supcheg.advancedgui.api.util.CollectionUtil.makeNoNullsList;

public interface Description extends Examinable, Buildable<Description, Description.Builder> {

    static Builder description() {
        return new DescriptionImpl.BuilderImpl();
    }

    static Description description(Consumer<Builder> consumer) {
        return Buildable.configureAndBuild(description(), consumer);
    }

    @Unmodifiable
    List<Component> lines();

    @Override
    default Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(
                ExaminableProperty.of("lines", lines())
        );
    }

    interface Builder extends AbstractBuilder<Description> {

        List<Component> lines();

        Builder addLines(List<Component> lines);

        default Builder addLines(Component line) {
            return addLines(List.of(line));
        }

        default Builder addLines(Component first, Component second, Component... other) {
            return addLines(makeNoNullsList(first, second, other));
        }

        Builder lines(List<Component> lines);

        default Builder lines(Component line) {
            return lines(List.of(line));
        }

        default Builder lines(Component first, Component second, Component... other) {
            return lines(makeNoNullsList(first, second, other));
        }
    }
}
