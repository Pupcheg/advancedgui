package me.supcheg.advancedgui.api.layout.template;

import me.supcheg.advancedgui.api.builder.Buildable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public non-sealed interface EnchantmentLayoutTemplate extends LayoutTemplate<EnchantmentLayoutTemplate, EnchantmentLayoutTemplate.Builder> {

    @NotNull
    @Contract(value = "-> new", pure = true)
    static  Builder enchantmentLayoutTemplate() {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    static  EnchantmentLayoutTemplate enchantmentLayoutTemplate(@NotNull Consumer<Builder> consumer) {
        return Buildable.configureAndBuild(enchantmentLayoutTemplate(), consumer);
    }

    interface Builder extends LayoutTemplate.Builder<EnchantmentLayoutTemplate, EnchantmentLayoutTemplate.Builder> {
    }
}
