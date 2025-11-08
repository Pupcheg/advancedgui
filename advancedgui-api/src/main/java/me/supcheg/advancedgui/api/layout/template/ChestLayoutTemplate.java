package me.supcheg.advancedgui.api.layout.template;

import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.api.coordinate.CoordinateTranslator;
import me.supcheg.advancedgui.api.coordinate.CoordinateTranslators;
import me.supcheg.advancedgui.api.layout.ChestLayout;
import net.kyori.examination.ExaminableProperty;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.function.Consumer;
import java.util.stream.Stream;

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

    @Override
    default Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.concat(
                Stream.of(
                        ExaminableProperty.of("rows", rows())
                ),
                LayoutTemplate.super.examinableProperties()
        );
    }

    interface Builder extends LayoutTemplate.Builder<ChestLayout, ChestLayoutTemplate, Builder> {
        Builder rows(int rows);

        @Nullable
        Integer rows();
    }
}
