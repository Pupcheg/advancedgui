package me.supcheg.advancedgui.api.layout.template;

import com.google.common.collect.SortedMultiset;
import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.api.coordinate.CoordinateTranslator;
import me.supcheg.advancedgui.api.coordinate.CoordinateTranslators;
import me.supcheg.advancedgui.api.layout.AnvilLayout;
import me.supcheg.advancedgui.api.layout.template.anvil.InputUpdateListener;
import net.kyori.examination.ExaminableProperty;
import org.jetbrains.annotations.Unmodifiable;

import java.util.function.Consumer;
import java.util.stream.Stream;

public non-sealed interface AnvilLayoutTemplate extends LayoutTemplate<AnvilLayout, AnvilLayoutTemplate, AnvilLayoutTemplate.Builder> {

    static Builder anvilLayout() {
        return new AnvilLayoutTemplateImpl.BuilderImpl();
    }

    static AnvilLayoutTemplate anvilLayout(Consumer<Builder> consumer) {
        return Buildable.configureAndBuild(anvilLayout(), consumer);
    }

    @Unmodifiable
    SortedMultiset<InputUpdateListener> inputUpdateListeners();

    @Override
    default CoordinateTranslator coordinateTranslator() {
        return CoordinateTranslators.anvilCoordinateTranslator();
    }

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

        Builder addInputUpdateListener(InputUpdateListener inputUpdateListener);

        default Builder addInputUpdateListener(InputUpdateListener.Builder inputUpdateListener) {
            return addInputUpdateListener(inputUpdateListener.build());
        }

        default Builder addInputUpdateListener(Consumer<InputUpdateListener.Builder> consumer) {
            return addInputUpdateListener(InputUpdateListener.inputUpdateListener(consumer));
        }

        Builder inputUpdateListeners(SortedMultiset<InputUpdateListener> inputUpdateListeners);

        SortedMultiset<InputUpdateListener> inputUpdateListeners();
    }
}
