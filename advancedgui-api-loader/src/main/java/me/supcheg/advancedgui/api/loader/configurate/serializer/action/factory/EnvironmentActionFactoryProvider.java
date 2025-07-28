package me.supcheg.advancedgui.api.loader.configurate.serializer.action.factory;

public class EnvironmentActionFactoryProvider implements ActionFactoryProvider {
    @Override
    public ActionFactory makeActionFactory() {
        return new ProxyActionFactory();
    }
}
