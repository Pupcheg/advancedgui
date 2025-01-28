package me.supcheg.advancedgui.testplugin;

import lombok.SneakyThrows;
import me.supcheg.advancedgui.api.gui.template.GuiTemplate;
import me.supcheg.advancedgui.platform.paper.PaperGuiController;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static me.supcheg.advancedgui.api.loader.yaml.YamlGuiLoader.yamlGuiLoader;
import static me.supcheg.advancedgui.platform.paper.PaperGuiController.paperGuiController;

public class TestPlugin extends JavaPlugin {

    private PaperGuiController controller;

    @Override
    public void onEnable() {
        controller = paperGuiController(paperGuiController -> paperGuiController
                .plugin(this)
        );

        controller.register(loadGuiTemplate("advancedgui/root.yaml"));
    }

    @SneakyThrows
    @NotNull
    private GuiTemplate loadGuiTemplate(@NotNull String path) {
        try (var in = getTextResource(path)) {
            Objects.requireNonNull(in);
            return yamlGuiLoader().loadResource(in);
        } finally {
            getSLF4JLogger().info("loaded {}", path);
        }
    }

    @SneakyThrows
    @Override
    public void onDisable() {
        controller.close();
    }
}
