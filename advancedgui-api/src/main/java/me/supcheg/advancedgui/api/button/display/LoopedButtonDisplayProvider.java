package me.supcheg.advancedgui.api.button.display;

import com.google.common.collect.Lists;
import me.supcheg.advancedgui.api.builder.AbstractBuilder;
import me.supcheg.advancedgui.api.builder.Buildable;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.time.Duration;
import java.util.List;
import java.util.function.Consumer;

public interface LoopedButtonDisplayProvider extends ButtonDisplayProvider, Buildable<LoopedButtonDisplayProvider, LoopedButtonDisplayProvider.Builder> {

    static Builder loopedButtonDisplayProvider() {
        return new LoopedButtonDisplayProviderImpl.BuilderImpl();
    }

    static LoopedButtonDisplayProvider loopedButtonDisplayProvider(Consumer<Builder> consumer) {
        return Buildable.configureAndBuild(loopedButtonDisplayProvider(), consumer);
    }

    @Unmodifiable
    List<? extends ButtonDisplay> displays();

    Duration switchDuration();

    interface Builder extends AbstractBuilder<LoopedButtonDisplayProvider> {
        Builder addDisplay(ButtonDisplay display);

        default Builder addDisplays(ButtonDisplay first, ButtonDisplay second, ButtonDisplay... displays) {
            return addDisplays(Lists.asList(first, second, displays));
        }

        Builder addDisplays(List<ButtonDisplay> displays);

        Builder displays(List<ButtonDisplay> displays);

        List<ButtonDisplay> displays();

        Builder switchDuration(Duration switchDuration);

        @Nullable
        Duration switchDuration();
    }
}
