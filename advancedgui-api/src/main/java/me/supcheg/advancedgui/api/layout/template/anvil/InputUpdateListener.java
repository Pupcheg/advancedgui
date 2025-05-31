package me.supcheg.advancedgui.api.layout.template.anvil;

import me.supcheg.advancedgui.api.builder.AbstractBuilder;
import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.api.sequence.Priority;
import me.supcheg.advancedgui.api.sequence.Sequenced;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.function.Consumer;

public interface InputUpdateListener extends Sequenced<InputUpdateListener>, Buildable<InputUpdateListener, InputUpdateListener.Builder> {

    static  Builder inputUpdateListener() {
        return new InputUpdateListenerImpl.BuilderImpl();
    }

    static  InputUpdateListener inputUpdateListener(Consumer<Builder> consumer) {
        return Buildable.configureAndBuild(inputUpdateListener(), consumer);
    }

    InputUpdateAction action();

    interface Builder extends AbstractBuilder<InputUpdateListener> {

        Builder priority(Priority priority);

        @Nullable
        Priority priority();

        Builder action(InputUpdateAction action);

        @Nullable
        InputUpdateAction action();
    }
}
