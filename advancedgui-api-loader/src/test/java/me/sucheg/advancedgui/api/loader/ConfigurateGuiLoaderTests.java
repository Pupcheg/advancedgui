package me.sucheg.advancedgui.api.loader;

import me.supcheg.advancedgui.api.action.Action;
import me.supcheg.advancedgui.api.gui.template.GuiTemplate;
import me.supcheg.advancedgui.api.loader.GuiLoader;
import me.supcheg.advancedgui.api.loader.configurate.ConfigurateGuiLoader;
import me.supcheg.advancedgui.api.sequence.At;
import me.supcheg.advancedgui.api.sequence.Priority;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.IOException;

import static me.supcheg.advancedgui.api.gui.template.GuiTemplate.gui;
import static me.supcheg.advancedgui.api.layout.template.AnvilLayoutTemplate.anvilLayout;
import static net.kyori.adventure.key.Key.key;
import static net.kyori.adventure.text.Component.text;
import static org.assertj.core.api.Assertions.assertThat;

class ConfigurateGuiLoaderTests {
    GuiLoader loader;
    @Language("yaml")
    String rawYamlTemplate;
    GuiTemplate template;

    @BeforeEach
    void setup() {
        loader = new ConfigurateGuiLoader(YamlConfigurationLoader::builder);

        rawYamlTemplate = """
                key: 'advancedgui:search'
                tickers:
                  - at: 'tick_end'
                    priority: 'normal'
                    action:
                      type: 'print'
                      message: 'gui tick'
                layout:
                  type: 'anvil'
                  input-update-listeners:
                    - priority: 'normal'
                      action:
                        type: 'print'
                        message: 'input update'
                  buttons:
                    - coordinates: [ [0, 0] ]
                      enabled: 'true'
                      shown: 'true'
                      interactions:
                        - priority: 'normal'
                          action:
                            type: 'print'
                            message: 'button interaction'
                      texture: 'advancedgui:search/interaction'
                      name: 'Hi!'
                      lore:
                        - 'eee'
                      tickers:
                        - at: 'tick_end'
                          priority: 'normal'
                          action:
                            type: 'print'
                            message: 'button tick'
                      enchanted: 'true'
                background:
                  locations:
                    - 'advancedgui:search/background'
                """;

        template = gui(gui -> gui
                .key(key("advancedgui", "search"))
                .addTicker(ticker -> ticker
                        .at(At.TICK_END)
                        .priority(Priority.NORMAL)
                        .action(System.out::println)
                )
                .layout(anvilLayout(), anvilLayout -> anvilLayout
                        .addInputUpdateListener(inputUpdateListener -> inputUpdateListener
                                .priority(Priority.NORMAL)
                                .action(System.out::println)
                        )
                        .addButton(button -> button
                                .addCoordinate(0, 0)
                                .enabled(true)
                                .shown(true)
                                .addInteraction(interaction -> interaction
                                        .priority(Priority.NORMAL)
                                        .action(System.out::println)
                                )
                                .texture(key("advancedgui", "search/interaction"))
                                .name(text("Hi!"))
                                .lore(
                                        text("eee")
                                )
                                .addTicker(ticker -> ticker
                                        .at(At.TICK_END)
                                        .priority(Priority.NORMAL)
                                        .action(System.out::println)
                                )
                                .enchanted(true)
                        )
                )
                .background(background -> background
                        .addLocation(key("advancedgui", "search/background"))
                )
        );
    }

    @Test
    void yamlLoad() throws IOException {
        assertThat(loader.loadString(rawYamlTemplate))
                .usingRecursiveComparison()
                .ignoringFieldsOfTypes(Action.class)
                .ignoringCollectionOrder()
                .isEqualTo(template);
    }
}
