package me.supcheg.advancedgui.api.loader.yaml;

import me.supcheg.advancedgui.api.loader.GuiLoader;

public interface YamlGuiLoader extends GuiLoader {
    static YamlGuiLoader yamlGuiLoader() {
        return YamlGuiLoaderImpl.Instances.INSTANCE;
    }

    interface Provider {
        YamlGuiLoader yamlGuiLoader();
    }
}
