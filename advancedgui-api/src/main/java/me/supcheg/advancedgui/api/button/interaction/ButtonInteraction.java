package me.supcheg.advancedgui.api.button.interaction;

import me.supcheg.advancedgui.api.builder.AbstractBuilder;
import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.api.sequence.Priority;
import me.supcheg.advancedgui.api.sequence.Sequenced;
import net.kyori.examination.Examinable;
import net.kyori.examination.ExaminableProperty;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.stream.Stream;

public interface ButtonInteraction extends Examinable, Sequenced<ButtonInteraction>,
        Buildable<ButtonInteraction, ButtonInteraction.Builder> {

    @NotNull
    @Contract("-> new")
    static Builder buttonInteraction() {
        return new ButtonInteractionImpl.BuilderImpl();
    }

    @NotNull
    @Contract("_ -> new")
    static ButtonInteraction buttonInteraction(@NotNull Consumer<ButtonInteraction.Builder> consumer) {
        return Buildable.configureAndBuild(buttonInteraction(), consumer);
    }

    @NotNull
    ButtonInteractionAction action();

    @NotNull
    @Override
    default Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(
                ExaminableProperty.of("action", action()),
                ExaminableProperty.of("priority", priority())
        );
    }

    interface Builder extends AbstractBuilder<ButtonInteraction> {
        @NotNull
        @Contract("_ -> this")
        Builder priority(@NotNull Priority priority);

        @NotNull
        @Contract("_ -> this")
        default Builder priority(int value) {
            return priority(Priority.priority(value));
        }

        @Nullable
        Priority priority();

        @NotNull
        @Contract("_ -> this")
        Builder action(@NotNull ButtonInteractionAction action);

        @Nullable
        ButtonInteractionAction action();
    }
}
