package me.supcheg.advancedgui.api.button.description;

import me.supcheg.advancedgui.api.builder.AbstractBuilder;
import me.supcheg.advancedgui.api.builder.Buildable;
import net.kyori.adventure.text.Component;
import net.kyori.examination.Examinable;
import net.kyori.examination.ExaminableProperty;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static me.supcheg.advancedgui.api.util.CollectionUtil.makeNoNullsList;

public interface Description extends Examinable, Buildable<Description, Description.Builder> {

    @NotNull
    @Contract("-> new")
    static Builder description() {
        return new DescriptionImpl.BuilderImpl();
    }

    @NotNull
    @Contract("_ -> new")
    static Description description(@NotNull Consumer<Builder> consumer) {
        return Buildable.configureAndBuild(description(), consumer);
    }

    @NotNull
    @Unmodifiable
    List<Component> lines();

    @NotNull
    @Override
    default Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(
                ExaminableProperty.of("lines", lines())
        );
    }

    interface Builder extends AbstractBuilder<Description> {

        @NotNull
        List<Component> lines();

        @NotNull
        @Contract("_ -> this")
        Builder addLines(@NotNull List<Component> lines);

        @NotNull
        @Contract("_ -> this")
        default Builder addLines(@NotNull Component line) {
            return addLines(List.of(line));
        }

        @NotNull
        @Contract("_, _, _ -> this")
        default Builder addLines(@NotNull Component first, @NotNull Component second, @NotNull Component @NotNull ... other) {
            return addLines(makeNoNullsList(first, second, other));
        }

        @NotNull
        @Contract("_ -> this")
        Builder lines(@NotNull List<Component> lines);

        @NotNull
        @Contract("_ -> this")
        default Builder lines(@NotNull Component line) {
            return lines(List.of(line));
        }

        @NotNull
        @Contract("_, _, _ -> this")
        default Builder lines(@NotNull Component first, @NotNull Component second, @NotNull Component @NotNull ... other) {
            return lines(makeNoNullsList(first, second, other));
        }
    }
}
