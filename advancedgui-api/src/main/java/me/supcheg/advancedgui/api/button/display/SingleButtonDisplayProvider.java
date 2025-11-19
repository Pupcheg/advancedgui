package me.supcheg.advancedgui.api.button.display;

import com.google.common.collect.Iterators;
import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.code.RecordInterface;

import java.util.Iterator;
import java.util.function.Consumer;

@RecordInterface
public non-sealed interface SingleButtonDisplayProvider extends ButtonDisplayProvider, Buildable<SingleButtonDisplayProvider, SingleButtonDisplayProviderBuilder> {

    static SingleButtonDisplayProviderBuilder singleButtonDisplayProvider() {
        return new SingleButtonDisplayProviderBuilderImpl();
    }

    static SingleButtonDisplayProvider singleButtonDisplayProvider(Consumer<SingleButtonDisplayProviderBuilder> consumer) {
        return Buildable.configureAndBuild(singleButtonDisplayProvider(), consumer);
    }

    static SingleButtonDisplayProvider singleButtonDisplayProvider(ButtonDisplay buttonDisplay) {
        return new  SingleButtonDisplayProviderImpl(buttonDisplay);
    }

    ButtonDisplay display();

    @Override
    default Iterator<ButtonDisplay> displaysLoop() {
        return Iterators.cycle(display());
    }
}
