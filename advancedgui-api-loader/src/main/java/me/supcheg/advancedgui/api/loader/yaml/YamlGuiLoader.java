package me.supcheg.advancedgui.api.loader.yaml;

import me.supcheg.advancedgui.api.loader.GuiLoader;
import org.jetbrains.annotations.NotNull;

public interface YamlGuiLoader extends GuiLoader {

    @NotNull
    static YamlGuiLoader yamlGuiLoader() {
        return YamlGuiLoaderImpl.Instances.INSTANCE;
    }

    interface Provider {
        @NotNull
        YamlGuiLoader yamlGuiLoader();
    }
}
