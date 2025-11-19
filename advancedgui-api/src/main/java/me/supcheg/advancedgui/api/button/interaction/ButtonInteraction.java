package me.supcheg.advancedgui.api.button.interaction;

import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.api.sequence.Priority;
import me.supcheg.advancedgui.api.sequence.Sequenced;
import me.supcheg.advancedgui.code.RecordInterface;

import java.util.function.Consumer;

@RecordInterface
public interface ButtonInteraction extends Sequenced<ButtonInteraction>,
        Buildable<ButtonInteraction, ButtonInteractionBuilder> {

    static ButtonInteractionBuilder buttonInteraction() {
        return new ButtonInteractionBuilderImpl();
    }

    static ButtonInteraction buttonInteraction(Consumer<ButtonInteractionBuilder> consumer) {
        return Buildable.configureAndBuild(buttonInteraction(), consumer);
    }

    @Override
    Priority priority();

    ButtonInteractionAction action();
}
