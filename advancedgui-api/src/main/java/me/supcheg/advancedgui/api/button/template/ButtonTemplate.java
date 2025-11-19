package me.supcheg.advancedgui.api.button.template;

import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.api.button.Button;
import me.supcheg.advancedgui.api.button.display.ButtonDisplayProvider;
import me.supcheg.advancedgui.api.button.interaction.ButtonInteraction;
import me.supcheg.advancedgui.api.coordinate.Coordinate;
import me.supcheg.advancedgui.api.lifecycle.LifecycleListenerRegistry;
import me.supcheg.advancedgui.api.lifecycle.Lifecycled;
import me.supcheg.advancedgui.api.sequence.collection.SequencedSortedSet;
import me.supcheg.advancedgui.code.RecordInterface;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Set;
import java.util.function.Consumer;

@RecordInterface
public interface ButtonTemplate extends Buildable<ButtonTemplate, ButtonTemplateBuilder>, Lifecycled<Button> {

    static ButtonTemplateBuilder buttonTemplate() {
        return new ButtonTemplateBuilderImpl();
    }

    static ButtonTemplate buttonTemplate(Consumer<ButtonTemplateBuilder> consumer) {
        return Buildable.configureAndBuild(buttonTemplate(), consumer);
    }

    @Unmodifiable
    Set<Coordinate> coordinates();

    @Unmodifiable
    SequencedSortedSet<ButtonInteraction> interactions();

    ButtonDisplayProvider displayProvider();

    @Override
    LifecycleListenerRegistry<Button> lifecycleListenerRegistry();
}
