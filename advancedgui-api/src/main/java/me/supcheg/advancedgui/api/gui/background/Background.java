package me.supcheg.advancedgui.api.gui.background;

import me.supcheg.advancedgui.api.builder.AbstractBuilder;
import me.supcheg.advancedgui.api.builder.Buildable;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;
import java.util.function.Consumer;

public interface Background extends Buildable<Background, Background.Builder> {

    @NotNull
    @Contract(value = "-> new", pure = true)
    static Builder background() {
        return new BackgroundImpl.BuilderImpl();
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    static Background background(@NotNull Consumer<Builder> consumer) {
        return Buildable.configureAndBuild(background(), consumer);
    }

    @NotNull
    @Unmodifiable
    List<Key> locations();

    interface Builder extends AbstractBuilder<Background> {
        @NotNull
        @Contract(value = "_ -> this", pure = true)
        Builder addLocation(@NotNull Key location);

        @NotNull
        @Contract(value = "_ -> this", pure = true)
        Builder locations(@NotNull List<Key> locations);

        @NotNull
        List<Key> locations();
    }
}
