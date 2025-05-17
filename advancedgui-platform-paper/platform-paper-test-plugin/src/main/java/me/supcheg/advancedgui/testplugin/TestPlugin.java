package me.supcheg.advancedgui.testplugin;

import com.google.common.cache.CacheBuilder;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import lombok.SneakyThrows;
import me.supcheg.advancedgui.api.gui.template.GuiTemplate;
import me.supcheg.advancedgui.platform.paper.PaperGuiController;
import me.supcheg.advancedgui.platform.paper.util.AdvancedguiCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

import static me.supcheg.advancedgui.api.component.guava.GuavaComponentRendererCacheProviders.guava;
import static me.supcheg.advancedgui.api.loader.yaml.YamlGuiLoader.yamlGuiLoader;
import static me.supcheg.advancedgui.platform.paper.PaperGuiController.paperGuiController;

@SuppressWarnings("UnstableApiUsage")
public class TestPlugin extends JavaPlugin {

    private PaperGuiController controller;

    @Override
    public void onEnable() {
        controller = paperGuiController(paperGuiController -> paperGuiController
                .plugin(this)
                .componentRenderer(componentRenderer -> componentRenderer
                        .noItalicByDefault()
                        .enableCache(guava(CacheBuilder::weakKeys))
                )
        );

        controller.register(loadGuiTemplate("testplugin/question.yaml"));

        getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event -> event
                .registrar()
                .register(
                        new AdvancedguiCommand(controller)
                                .build()
                )
        );
    }

    @SneakyThrows
    @NotNull
    private GuiTemplate loadGuiTemplate(@NotNull String path) {
        try (var in = Objects.requireNonNull(getTextResource(path))) {
            var template = yamlGuiLoader().loadResource(in);
            getSLF4JLogger().info("Successfully loaded template '{}' from '{}'", template.key(), path);
            return template;
        }
    }

    @Override
    public void onDisable() {
        try {
            controller.close();
        } catch (IOException e) {
            getSLF4JLogger().error("Failed to close paper gui controller", e);
        }
    }
}
