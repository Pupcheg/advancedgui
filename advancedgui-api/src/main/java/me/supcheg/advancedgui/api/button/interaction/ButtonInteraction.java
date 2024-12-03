package me.supcheg.advancedgui.api.button.interaction;

import me.supcheg.advancedgui.api.builder.AbstractBuilder;
import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.api.sequence.Priority;
import me.supcheg.advancedgui.api.sequence.Sequenced;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public interface ButtonInteraction extends Sequenced<ButtonInteraction>,
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
