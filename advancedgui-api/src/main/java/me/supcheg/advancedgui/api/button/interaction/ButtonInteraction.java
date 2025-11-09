package me.supcheg.advancedgui.api.button.interaction;

import me.supcheg.advancedgui.api.builder.AbstractBuilder;
import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.api.sequence.Priority;
import me.supcheg.advancedgui.api.sequence.Sequenced;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.function.Consumer;

public interface ButtonInteraction extends Sequenced<ButtonInteraction>,
        Buildable<ButtonInteraction, ButtonInteraction.Builder> {

    static Builder buttonInteraction() {
        return new ButtonInteractionImpl.BuilderImpl();
    }

    static ButtonInteraction buttonInteraction(Consumer<ButtonInteraction.Builder> consumer) {
        return Buildable.configureAndBuild(buttonInteraction(), consumer);
    }

    ButtonInteractionAction action();

    interface Builder extends AbstractBuilder<ButtonInteraction> {
        Builder priority(Priority priority);

        default Builder priority(int value) {
            return priority(Priority.priority(value));
        }

        @Nullable
        Priority priority();

        Builder action(ButtonInteractionAction action);

        @Nullable
        ButtonInteractionAction action();
    }
}
