package me.supcheg.advancedgui.api.loader;

import me.supcheg.advancedgui.api.action.Action;
import me.supcheg.advancedgui.api.gui.template.GuiTemplate;
import me.supcheg.advancedgui.api.loader.configurate.ConfigurateGuiLoader;
import me.supcheg.advancedgui.api.sequence.NamedPriority;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.spongepowered.configurate.gson.GsonConfigurationLoader;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.IOException;

import static me.supcheg.advancedgui.api.gui.template.GuiTemplate.gui;
import static me.supcheg.advancedgui.api.key.AdvancedGuiKeys.advancedguiKey;
import static me.supcheg.advancedgui.api.layout.template.AnvilLayoutTemplate.anvilLayout;
import static me.supcheg.advancedgui.api.lifecycle.tick.TickPointcut.afterTickPointcut;
import static me.supcheg.advancedgui.api.lifecycle.tick.TickPointcut.beforeTickPointcut;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.Style.style;
import static org.assertj.core.api.Assertions.assertThat;

class ConfigurateGuiLoaderTests {
    @Language("yaml")
    static String rawYamlTemplate;
    @Language("json")
    static String rawJsonTemplate;

    static GuiTemplate template;

    @BeforeAll
    static void setup() {
        rawYamlTemplate = """
                key: 'yaml_test'
                layout:
                  type: 'anvil'
                  input-update-listeners:
                    - priority: 'normal'
                      action: 'dummy'
                  buttons:
                    - coordinates: [ [ 0, 0 ] ]
                      enabled: 'true'
                      shown: 'true'
                      interactions:
                        - priority: 'normal'
                          action: 'dummy'
                      texture: 'yaml_test/interaction'
                      name: '<bold>Hi!'
                      description:
                        lines:
                          - '<red>eee'
                          - 'eEe'
                      lifecycle-listener-registry:
                        before_tick:
                          - priority: 'normal'
                            action: 'dummy'
                      glowing: 'true'
                  lifecycle-listener-registry:
                    after_tick:
                      - priority: 'highest'
                        action: 'dummy'
                lifecycle-listener-registry:
                  before_tick:
                    - priority: 'normal'
                      action: 'dummy'
                  after_tick:
                    - priority: 'low'
                      action: 'dummy'
                background:
                  locations:
                    - 'yaml_test/background'
                """;

        rawJsonTemplate = """
                {
                  "key": "yaml_test",
                  "layout": {
                    "type": "anvil",
                    "input-update-listeners": [
                      {
                        "priority": "normal",
                        "action": "dummy"
                      }
                    ],
                    "buttons": [
                      {
                        "coordinates": [ [0, 0] ],
                        "enabled": "true",
                        "shown": "true",
                        "interactions": [
                          {
                            "priority": "normal",
                            "action": "dummy"
                          }
                        ],
                        "texture": "yaml_test/interaction",
                        "name": "<bold>Hi!",
                        "description": {
                          "lines": [
                            "<red>eee",
                            "eEe"
                          ]
                        },
                        "lifecycle-listener-registry": {
                          "before_tick": [
                            {
                              "priority": "normal",
                              "action": "dummy"
                            }
                          ]
                        },
                        "glowing": "true"
                      }
                    ],
                    "lifecycle-listener-registry": {
                      "after_tick": [
                        {
                          "priority": "highest",
                          "action": "dummy"
                        }
                      ]
                    }
                  },
                  "lifecycle-listener-registry": {
                    "before_tick": [
                      {
                        "priority": "normal",
                        "action": "dummy"
                      }
                    ],
                    "after_tick": [
                      {
                        "priority": "low",
                        "action": "dummy"
                      }
                    ]
                  },
                  "background": {
                    "locations": [
                      "yaml_test/background"
                    ]
                  }
                }
                """;

        template = gui(gui -> gui
                .key(advancedguiKey("yaml_test"))
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
                                .texture(advancedguiKey("yaml_test/interaction"))
                                .name(text("Hi!", style(TextDecoration.BOLD)))
                                .description(description -> description
                                        .lines(
                                                text("eee", NamedTextColor.RED),
                                                text("eEe")
                                        )
                                )
                                .glowing(true)
                                .lifecycleListenerRegistry(lifecycleListenerRegistry -> lifecycleListenerRegistry
                                        .add(beforeTickPointcut(), lifecycleListener -> lifecycleListener
                                                .priority(NamedPriority.NORMAL)
                                                .action(System.out::println)
                                        )
                                )
                        )
                        .lifecycleListenerRegistry(lifecycleListenerRegistry -> lifecycleListenerRegistry
                                .add(afterTickPointcut(), lifecycleListener -> lifecycleListener
                                        .priority(NamedPriority.HIGHEST)
                                        .action(System.out::println)
                                )
                        )
                )
                .background(background -> background
                        .addLocation(advancedguiKey("yaml_test/background"))
                )
                .lifecycleListenerRegistry(lifecycleListenerRegistry -> lifecycleListenerRegistry
                        .add(beforeTickPointcut(), lifecycleListener -> lifecycleListener
                                .priority(NamedPriority.NORMAL)
                                .action(System.out::println)
                        )
                        .add(afterTickPointcut(), lifecycleListener -> lifecycleListener
                                .priority(NamedPriority.LOW)
                                .action(System.out::println)
                        )
                )
        );
    }

    @Test
    void yamlLoad() throws IOException {
        GuiLoader yamlLoader = new ConfigurateGuiLoader(YamlConfigurationLoader::builder);

        assertThat(yamlLoader.loadString(rawYamlTemplate))
                .usingRecursiveComparison()
                .ignoringFieldsOfTypes(Action.class)
                .isEqualTo(template);
    }

    @Test
    void jsonLoad() throws IOException {
        GuiLoader jsonLoader = new ConfigurateGuiLoader(GsonConfigurationLoader::builder);

        assertThat(jsonLoader.loadString(rawJsonTemplate))
                .usingRecursiveComparison()
                .ignoringFieldsOfTypes(Action.class)
                .isEqualTo(template);
    }
}
