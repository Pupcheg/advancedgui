package me.supcheg.advancedgui.api.layout.template;

import me.supcheg.advancedgui.api.builder.AbstractBuilder;
import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.api.button.template.ButtonTemplate;
import me.supcheg.advancedgui.api.coordinate.CoordinateTranslator;
import me.supcheg.advancedgui.api.layout.Layout;
import me.supcheg.advancedgui.api.lifecycle.Lifecycled;
import net.kyori.examination.Examinable;
import net.kyori.examination.ExaminableProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;

public sealed interface LayoutTemplate<L extends Layout<L>, T extends LayoutTemplate<L, T, B>, B extends LayoutTemplate.Builder<L, T, B>>
        extends Examinable, Buildable<T, B>, Lifecycled<L>
        permits AnvilLayoutTemplate {

    @NotNull
    @Unmodifiable
    Set<ButtonTemplate> buttons();

    @NotNull
    CoordinateTranslator coordinateTranslator();

    @NotNull
    @Override
    default Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(
                ExaminableProperty.of("buttons", buttons()),
                ExaminableProperty.of("coordinateTranslator", coordinateTranslator()),
                ExaminableProperty.of("lifecycleListenerRegistry", lifecycleListenerRegistry())
        );
    }

    interface Builder<L, T, B extends Builder<L, T, B>> extends AbstractBuilder<T>, Lifecycled.Builder<L, B> {

        @NotNull
        B addButton(@NotNull ButtonTemplate button);

        @NotNull
        default B addButton(@NotNull Consumer<ButtonTemplate.Builder> consumer) {
            return addButton(ButtonTemplate.button(consumer));
        }

        @NotNull
        default B addButton(@NotNull ButtonTemplate.Builder button) {
            return addButton(button.build());
        }

        @NotNull
        B buttons(@NotNull Set<ButtonTemplate> buttons);

        @NotNull
        Set<ButtonTemplate> buttons();
    }
}
