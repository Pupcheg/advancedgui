package me.supcheg.advancedgui.api.layout.template;

import me.supcheg.advancedgui.api.builder.AbstractBuilder;
import me.supcheg.advancedgui.api.button.template.ButtonTemplate;
import me.supcheg.advancedgui.api.coordinate.CoordinateTranslator;
import me.supcheg.advancedgui.api.builder.Buildable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Set;
import java.util.function.Consumer;

public sealed interface LayoutTemplate<T, B extends LayoutTemplate.Builder<T, B>> extends Buildable<T, B>
        permits AnvilLayoutTemplate, BeaconLayoutTemplate, BrewingLayoutTemplate, CartographyLayoutTemplate,
                ChestLayoutTemplate, CraftingLayoutTemplate, DispenserLayoutTemplate, EnchantmentLayoutTemplate,
                FurnaceLayoutTemplate, GrindstoneLayoutTemplate, HopperLayoutTemplate, HorseLayoutTemplate, LoomLayoutTemplate,
                SmithingLayoutTemplate, StonecutterLayoutTemplate, TradingLayoutTemplate {

    @NotNull
    @Unmodifiable
    Set<ButtonTemplate> buttons();

    @NotNull
    CoordinateTranslator coordinateTranslator();

    interface Builder<T, B extends Builder<T, B>> extends AbstractBuilder<T> {

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
        AnvilLayoutTemplate.Builder buttons(@NotNull Set<ButtonTemplate> buttons);

        @NotNull
        Set<ButtonTemplate> buttons();
    }
}
