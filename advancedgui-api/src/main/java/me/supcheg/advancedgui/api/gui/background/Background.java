package me.supcheg.advancedgui.api.gui.background;

import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.code.RecordInterface;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;
import java.util.function.Consumer;

@RecordInterface
public interface Background extends Buildable<Background, BackgroundBuilder> {

    static BackgroundBuilder background() {
        return new BackgroundBuilderImpl();
    }

    static Background background(Consumer<BackgroundBuilder> consumer) {
        return Buildable.configureAndBuild(background(), consumer);
    }

    @Unmodifiable
    List<Key> locations();
}
