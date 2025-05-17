package me.supcheg.advancedgui.api.layout.template;

import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.api.coordinate.CoordinateTranslator;
import me.supcheg.advancedgui.api.coordinate.CoordinateTranslators;
import me.supcheg.advancedgui.api.layout.AnvilLayout;
import me.supcheg.advancedgui.api.layout.template.anvil.InputUpdateListener;
import net.kyori.examination.ExaminableProperty;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.SortedSet;
import java.util.function.Consumer;
import java.util.stream.Stream;

public non-sealed interface AnvilLayoutTemplate extends LayoutTemplate<AnvilLayout, AnvilLayoutTemplate, AnvilLayoutTemplate.Builder> {

    @NotNull
    @Contract("-> new")
    static Builder anvilLayout() {
        return new AnvilLayoutTemplateImpl.BuilderImpl();
    }

    @NotNull
    @Contract("_ -> new")
    static AnvilLayoutTemplate anvilLayout(@NotNull Consumer<Builder> consumer) {
        return Buildable.configureAndBuild(anvilLayout(), consumer);
    }

    @NotNull
    @Unmodifiable
    SortedSet<InputUpdateListener> inputUpdateListeners();

    @NotNull
    @Override
    default CoordinateTranslator coordinateTranslator() {
        return CoordinateTranslators.anvilCoordinateTranslator();
    }

    @NotNull
    @Override
    default Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.concat(
                Stream.of(
                        ExaminableProperty.of("inputUpdateListeners", inputUpdateListeners())
                ),
                LayoutTemplate.super.examinableProperties()
        );
    }

    interface Builder extends LayoutTemplate.Builder<AnvilLayout, AnvilLayoutTemplate, Builder> {

        @NotNull
        @Contract("_ -> this")
        Builder addInputUpdateListener(@NotNull InputUpdateListener inputUpdateListener);

        @NotNull
        @Contract("_ -> this")
        default Builder addInputUpdateListener(@NotNull InputUpdateListener.Builder inputUpdateListener) {
            return addInputUpdateListener(inputUpdateListener.build());
        }

        @NotNull
        @Contract("_ -> this")
        default Builder addInputUpdateListener(@NotNull Consumer<InputUpdateListener.Builder> consumer) {
            return addInputUpdateListener(InputUpdateListener.inputUpdateListener(consumer));
        }

        @NotNull
        Builder inputUpdateListeners(@NotNull SortedSet<InputUpdateListener> inputUpdateListeners);

        @NotNull
        SortedSet<InputUpdateListener> inputUpdateListeners();
    }
}
