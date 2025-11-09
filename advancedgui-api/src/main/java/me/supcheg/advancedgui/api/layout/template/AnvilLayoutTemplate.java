package me.supcheg.advancedgui.api.layout.template;

import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.api.coordinate.CoordinateTranslator;
import me.supcheg.advancedgui.api.coordinate.CoordinateTranslators;
import me.supcheg.advancedgui.api.layout.AnvilLayout;
import me.supcheg.advancedgui.api.layout.template.anvil.InputUpdateListener;
import me.supcheg.advancedgui.api.sequence.collection.SequencedSortedSet;
import org.jetbrains.annotations.Unmodifiable;

import java.util.function.Consumer;

public non-sealed interface AnvilLayoutTemplate extends LayoutTemplate<AnvilLayout, AnvilLayoutTemplate, AnvilLayoutTemplate.Builder> {

    static Builder anvilLayout() {
        return new AnvilLayoutTemplateImpl.BuilderImpl();
    }

    static AnvilLayoutTemplate anvilLayout(Consumer<Builder> consumer) {
        return Buildable.configureAndBuild(anvilLayout(), consumer);
    }

    @Unmodifiable
    SequencedSortedSet<InputUpdateListener> inputUpdateListeners();

    @Override
    default CoordinateTranslator coordinateTranslator() {
        return CoordinateTranslators.anvilCoordinateTranslator();
    }

    interface Builder extends LayoutTemplate.Builder<AnvilLayout, AnvilLayoutTemplate, Builder> {

        Builder addInputUpdateListener(InputUpdateListener inputUpdateListener);

        default Builder addInputUpdateListener(InputUpdateListener.Builder inputUpdateListener) {
            return addInputUpdateListener(inputUpdateListener.build());
        }

        default Builder addInputUpdateListener(Consumer<InputUpdateListener.Builder> consumer) {
            return addInputUpdateListener(InputUpdateListener.inputUpdateListener(consumer));
        }

        Builder inputUpdateListeners(SequencedSortedSet<InputUpdateListener> inputUpdateListeners);

        SequencedSortedSet<InputUpdateListener> inputUpdateListeners();
    }
}
