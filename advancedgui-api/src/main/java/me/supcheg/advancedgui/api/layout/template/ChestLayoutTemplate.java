package me.supcheg.advancedgui.api.layout.template;

import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.api.coordinate.CoordinateTranslator;
import me.supcheg.advancedgui.api.coordinate.CoordinateTranslators;
import me.supcheg.advancedgui.api.layout.ChestLayout;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.function.Consumer;

public non-sealed interface ChestLayoutTemplate extends LayoutTemplate<ChestLayout, ChestLayoutTemplate, ChestLayoutTemplate.Builder> {

    static Builder chestLayout() {
        return new ChestLayoutTemplateImpl.BuilderImpl();
    }

    static ChestLayoutTemplate chestLayout(Consumer<Builder> consumer) {
        return Buildable.configureAndBuild(chestLayout(), consumer);
    }

    int rows();

    @Override
    default CoordinateTranslator coordinateTranslator() {
        return CoordinateTranslators.chestCoordinateTranslator(rows());
    }

    interface Builder extends LayoutTemplate.Builder<ChestLayout, ChestLayoutTemplate, Builder> {
        Builder rows(int rows);

        @Nullable
        Integer rows();
    }
}
