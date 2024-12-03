package me.supcheg.advancedgui.api.loader;

import me.supcheg.advancedgui.api.action.Action;
import me.supcheg.advancedgui.api.gui.template.GuiTemplate;
import me.supcheg.advancedgui.api.loader.configurate.ConfigurateGuiLoader;
import me.supcheg.advancedgui.api.sequence.At;
import me.supcheg.advancedgui.api.sequence.NamedPriority;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.IOException;

import static me.supcheg.advancedgui.api.gui.template.GuiTemplate.gui;
import static me.supcheg.advancedgui.api.layout.template.AnvilLayoutTemplate.anvilLayout;
import static net.kyori.adventure.key.Key.key;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.Style.style;
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
                key: 'advancedgui:yaml_test'
                tickers:
                  - at: 'tick_start'
                    priority: 'normal'
                    action: 'dummy'
                  - at: 'tick_end'
                    priority: 'low'
                    action: 'dummy'
                layout:
                  type: 'anvil'
                  input-update-listeners:
                    - priority: 'normal'
                      action: 'dummy'
                  buttons:
                    - coordinates: [ [0, 0] ]
                      enabled: 'true'
                      shown: 'true'
                      interactions:
                        - priority: 'normal'
                          action: 'dummy'
                      texture: 'advancedgui:yaml_test/interaction'
                      name: '<bold>Hi!'
                      description:
                        lines:
                          - '<red>eee'
                          - 'eEe'
                      tickers:
                        - at: 'tick_end'
                          priority: 'normal'
                          action: 'dummy'
                      glowing: 'true'
                background:
                  locations:
                    - 'advancedgui:yaml_test/background'
                """;

        template = gui(gui -> gui
                .key(key("advancedgui", "yaml_test"))
                .addTicker(ticker -> ticker
                        .at(At.TICK_START)
                        .priority(NamedPriority.NORMAL)
                        .action(System.out::println)
                )
                .addTicker(ticker -> ticker
                        .at(At.TICK_END)
                        .priority(NamedPriority.LOW)
                        .action(System.out::println)
                )
                .layout(anvilLayout(), anvilLayout -> anvilLayout
                        .addInputUpdateListener(inputUpdateListener -> inputUpdateListener
                                .priority(NamedPriority.NORMAL)
                                .action(System.out::println)
                        )
                        .addButton(button -> button
                                .addCoordinate(0, 0)
                                .enabled(true)
                                .shown(true)
                                .addInteraction(interaction -> interaction
                                        .priority(NamedPriority.NORMAL)
                                        .action(System.out::println)
                                )
                                .texture(key("advancedgui", "yaml_test/interaction"))
                                .name(text("Hi!", style(TextDecoration.BOLD)))
                                .description(description -> description
                                        .lines(
                                                text("eee", NamedTextColor.RED),
                                                text("eEe")
                                        )
                                )
                                .addTicker(ticker -> ticker
                                        .at(At.TICK_END)
                                        .priority(NamedPriority.NORMAL)
                                        .action(System.out::println)
                                )
                                .glowing(true)
                        )
                )
                .background(background -> background
                        .addLocation(key("advancedgui", "yaml_test/background"))
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
