package me.supcheg.advancedgui.api.layout.template;

import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.api.button.template.ButtonTemplate;
import me.supcheg.advancedgui.api.coordinate.CoordinateTranslator;
import me.supcheg.advancedgui.api.coordinate.CoordinateTranslators;
import me.supcheg.advancedgui.api.layout.ChestLayout;
import me.supcheg.advancedgui.api.lifecycle.LifecycleListenerRegistry;
import me.supcheg.advancedgui.code.RecordInterface;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Set;
import java.util.function.Consumer;

@RecordInterface
public non-sealed interface ChestLayoutTemplate extends LayoutTemplate<ChestLayout, ChestLayoutTemplate, ChestLayoutTemplateBuilder> {

    static ChestLayoutTemplateBuilder chestLayout() {
        return new ChestLayoutTemplateBuilderImpl();
    }

    static ChestLayoutTemplate chestLayout(Consumer<ChestLayoutTemplateBuilder> consumer) {
        return Buildable.configureAndBuild(chestLayout(), consumer);
    }

    int rows();

    @Override
    @Unmodifiable
    Set<ButtonTemplate> buttons();

    @Override
    LifecycleListenerRegistry<ChestLayout> lifecycleListenerRegistry();

    @Override
    default CoordinateTranslator coordinateTranslator() {
        return CoordinateTranslators.chestCoordinateTranslator(rows());
    }
}
