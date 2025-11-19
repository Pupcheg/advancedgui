package me.supcheg.advancedgui.api.layout.template;

import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.api.button.template.ButtonTemplate;
import me.supcheg.advancedgui.api.coordinate.CoordinateTranslator;
import me.supcheg.advancedgui.api.coordinate.CoordinateTranslators;
import me.supcheg.advancedgui.api.layout.AnvilLayout;
import me.supcheg.advancedgui.api.layout.template.anvil.InputUpdateListener;
import me.supcheg.advancedgui.api.lifecycle.LifecycleListenerRegistry;
import me.supcheg.advancedgui.api.sequence.collection.SequencedSortedSet;
import me.supcheg.advancedgui.code.RecordInterface;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Set;
import java.util.function.Consumer;

@RecordInterface
public non-sealed interface AnvilLayoutTemplate extends LayoutTemplate<AnvilLayout, AnvilLayoutTemplate, AnvilLayoutTemplateBuilder> {

    static AnvilLayoutTemplateBuilder anvilLayout() {
        return new AnvilLayoutTemplateBuilderImpl();
    }

    static AnvilLayoutTemplate anvilLayout(Consumer<AnvilLayoutTemplateBuilder> consumer) {
        return Buildable.configureAndBuild(anvilLayout(), consumer);
    }

    @Unmodifiable
    SequencedSortedSet<InputUpdateListener> inputUpdateListeners();

    @Override
    @Unmodifiable
    Set<ButtonTemplate> buttons();

    @Override
    LifecycleListenerRegistry<AnvilLayout> lifecycleListenerRegistry();

    @Override
    default CoordinateTranslator coordinateTranslator() {
        return CoordinateTranslators.anvilCoordinateTranslator();
    }
}
