package me.supcheg.advancedgui.api.gui.background;

import me.supcheg.advancedgui.api.builder.AbstractBuilder;
import me.supcheg.advancedgui.api.builder.Buildable;
import net.kyori.adventure.key.Key;
import net.kyori.examination.Examinable;
import net.kyori.examination.ExaminableProperty;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public interface Background extends Examinable, Buildable<Background, Background.Builder> {

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

    @NotNull
    @Override
    default Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(
                ExaminableProperty.of("locations", locations())
        );
    }

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
