package me.supcheg.advancedgui.api.layout.template.anvil;

import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.api.sequence.Priority;
import me.supcheg.advancedgui.api.sequence.Sequenced;
import me.supcheg.advancedgui.code.RecordInterface;

import java.util.function.Consumer;

@RecordInterface
public interface InputUpdateListener extends Sequenced<InputUpdateListener>, Buildable<InputUpdateListener, InputUpdateListenerBuilder> {

    static InputUpdateListenerBuilder inputUpdateListener() {
        return new InputUpdateListenerBuilderImpl();
    }

    static InputUpdateListener inputUpdateListener(Consumer<InputUpdateListenerBuilder> consumer) {
        return Buildable.configureAndBuild(inputUpdateListener(), consumer);
    }

    @Override
    Priority priority();

    InputUpdateAction action();
}
