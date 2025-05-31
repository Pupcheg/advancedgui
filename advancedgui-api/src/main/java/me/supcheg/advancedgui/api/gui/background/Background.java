package me.supcheg.advancedgui.api.gui.background;

import me.supcheg.advancedgui.api.builder.AbstractBuilder;
import me.supcheg.advancedgui.api.builder.Buildable;
import net.kyori.adventure.key.Key;
import net.kyori.examination.Examinable;
import net.kyori.examination.ExaminableProperty;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public interface Background extends Examinable, Buildable<Background, Background.Builder> {

    static Builder background() {
        return new BackgroundImpl.BuilderImpl();
    }

    static Background background(Consumer<Builder> consumer) {
        return Buildable.configureAndBuild(background(), consumer);
    }

    @Unmodifiable
    List<Key> locations();

    @Override
    default Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(
                ExaminableProperty.of("locations", locations())
        );
    }

    interface Builder extends AbstractBuilder<Background> {

        Builder addLocation(Key location);

        Builder locations(List<Key> locations);

        List<Key> locations();
    }
}
