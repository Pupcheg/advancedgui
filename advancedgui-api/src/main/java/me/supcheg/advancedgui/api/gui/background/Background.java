package me.supcheg.advancedgui.api.gui.background;

import me.supcheg.advancedgui.api.builder.AbstractBuilder;
import me.supcheg.advancedgui.api.builder.Buildable;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;
import java.util.function.Consumer;

public interface Background extends Buildable<Background, Background.Builder> {

    static Builder background() {
        return new BackgroundImpl.BuilderImpl();
    }

    static Background background(Consumer<Builder> consumer) {
        return Buildable.configureAndBuild(background(), consumer);
    }

    @Unmodifiable
    List<Key> locations();

    interface Builder extends AbstractBuilder<Background> {

        Builder addLocation(Key location);

        Builder locations(List<Key> locations);

        List<Key> locations();
    }
}
