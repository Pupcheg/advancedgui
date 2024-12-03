package me.supcheg.advancedgui.api.layout.template.anvil;

import me.supcheg.advancedgui.api.builder.AbstractBuilder;
import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.api.sequence.Priority;
import me.supcheg.advancedgui.api.sequence.Sequenced;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public interface InputUpdateListener extends Sequenced<InputUpdateListener>, Buildable<InputUpdateListener, InputUpdateListener.Builder> {

    @NotNull
    @Contract("-> new")
    static  Builder inputUpdateListener() {
        return new InputUpdateListenerImpl.BuilderImpl();
    }

    @NotNull
    @Contract("_ -> new")
    static  InputUpdateListener inputUpdateListener(@NotNull Consumer<Builder> consumer) {
        return Buildable.configureAndBuild(inputUpdateListener(), consumer);
    }

    @NotNull
    InputUpdateAction action();

    interface Builder extends AbstractBuilder<InputUpdateListener> {
        @NotNull
        @Contract("_ -> this")
        Builder priority(@NotNull Priority priority);

        @Nullable
        Priority priority();

        @NotNull
        @Contract("_ -> this")
        Builder action(@NotNull InputUpdateAction action);

        @Nullable
        InputUpdateAction action();
    }
}
