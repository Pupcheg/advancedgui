package me.supcheg.advancedgui.api.layout.template;

import me.supcheg.advancedgui.api.builder.Buildable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public non-sealed interface BeaconLayoutTemplate extends LayoutTemplate<BeaconLayoutTemplate, BeaconLayoutTemplate.Builder> {

    @NotNull
    @Contract(value = "-> new", pure = true)
    static Builder beaconLayoutTemplate() {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    static BeaconLayoutTemplate beaconLayoutTemplate(@NotNull Consumer<Builder> consumer) {
        return Buildable.configureAndBuild(beaconLayoutTemplate(), consumer);
    }

    interface Builder extends LayoutTemplate.Builder<BeaconLayoutTemplate, BeaconLayoutTemplate.Builder> {
    }
}
