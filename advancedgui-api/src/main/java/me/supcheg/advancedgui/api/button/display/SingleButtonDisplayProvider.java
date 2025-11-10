package me.supcheg.advancedgui.api.button.display;

public interface SingleButtonDisplayProvider extends ButtonDisplayProvider {

    static SingleButtonDisplayProvider singleButtonDisplayProvider(ButtonDisplay display) {
        return new SingleButtonDisplayProviderImpl(display);
    }

    ButtonDisplay display();
}
