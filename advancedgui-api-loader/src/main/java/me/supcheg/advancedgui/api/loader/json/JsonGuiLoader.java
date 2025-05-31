package me.supcheg.advancedgui.api.loader.json;

import me.supcheg.advancedgui.api.loader.GuiLoader;

public interface JsonGuiLoader extends GuiLoader {
    static JsonGuiLoader jsonGuiLoader() {
        return JsonGuiLoaderImpl.Instances.INSTANCE;
    }

    interface Provider {
        JsonGuiLoader jsonGuiLoader();
    }
}
