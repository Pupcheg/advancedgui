package me.supcheg.advancedgui.api.button.display;

import com.google.common.collect.Iterators;
import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.code.RecordInterface;
import org.jetbrains.annotations.Unmodifiable;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

@RecordInterface
public interface LoopedButtonDisplayProvider extends UpdatableButtonDisplayProvider, Buildable<LoopedButtonDisplayProvider, LoopedButtonDisplayProviderBuilder> {

    static LoopedButtonDisplayProviderBuilder loopedButtonDisplayProvider() {
        return new LoopedButtonDisplayProviderBuilderImpl();
    }

    static LoopedButtonDisplayProvider loopedButtonDisplayProvider(Consumer<LoopedButtonDisplayProviderBuilder> consumer) {
        return Buildable.configureAndBuild(loopedButtonDisplayProvider(), consumer);
    }

    @Unmodifiable
    List<ButtonDisplay> displays();

    @Override
    Duration switchDuration();

    @Override
    default Iterator<ButtonDisplay> displaysLoop() {
        return Iterators.cycle(displays());
    }

    @Override
    default boolean updatable() {
        return displays().size() > 1;
    }
}
