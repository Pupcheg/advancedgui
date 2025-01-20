package me.supcheg.advancedgui.api.loader;

import me.supcheg.advancedgui.api.gui.template.GuiTemplate;
import me.supcheg.advancedgui.api.loader.configurate.ConfigurateGuiLoader;
import me.supcheg.advancedgui.api.sequence.NamedPriority;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.spongepowered.configurate.gson.GsonConfigurationLoader;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.IOException;

import static me.supcheg.advancedgui.api.button.attribute.ButtonAttribute.glowing;
import static me.supcheg.advancedgui.api.button.attribute.ButtonAttribute.hidden;
import static me.supcheg.advancedgui.api.gui.template.GuiTemplate.gui;
import static me.supcheg.advancedgui.api.layout.template.AnvilLayoutTemplate.anvilLayout;
import static me.supcheg.advancedgui.api.lifecycle.tick.TickPointcut.afterTickPointcut;
import static me.supcheg.advancedgui.api.lifecycle.tick.TickPointcut.beforeTickPointcut;
import static me.supcheg.advancedgui.api.loader.interpret.DummyAction.dummyAction;
import static net.kyori.adventure.key.Key.key;
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
                key: 'advancedgui:test/test'
                layout:
                  type: 'anvil'
                  input-update-listeners:
                    - priority: 'normal'
                      action: 'dummy'
                  buttons:
                    - coordinates: [ [ 0, 0 ] ]
                      interactions:
                        - priority: 'normal'
                          action: 'dummy'
                      texture: 'advancedgui:test/interaction'
                      name: '<bold>Hi!'
                      description:
                        lines:
                          - '<red>eee'
                          - 'eEe'
                      lifecycle-listener-registry:
                        before_tick:
                          - priority: 'normal'
                            action: 'dummy'
                      attributes:
                        - 'glowing'
                        - 'hidden'
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
                    - 'advancedgui:test/background'
                """;

        rawJsonTemplate = """
                {
                  "key": "advancedgui:test/test",
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
                        "interactions": [
                          {
                            "priority": "normal",
                            "action": "dummy"
                          }
                        ],
                        "texture": "advancedgui:test/interaction",
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
                        "attributes": [ "glowing", "hidden" ]
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
                      "advancedgui:test/background"
                    ]
                  }
                }
                """;

        template = gui(gui -> gui
                .key(key("advancedgui:test/test"))
                .layout(anvilLayout(), anvilLayout -> anvilLayout
                        .addInputUpdateListener(inputUpdateListener -> inputUpdateListener
                                .priority(NamedPriority.NORMAL)
                                .action(dummyAction())
                        )
                        .addButton(button -> button
                                .addCoordinate(0, 0)
                                .addInteraction(interaction -> interaction
                                        .priority(NamedPriority.NORMAL)
                                        .action(dummyAction())
                                )
                                .texture(key("advancedgui:test/interaction"))
                                .name(text("Hi!", style(TextDecoration.BOLD)))
                                .description(description -> description
                                        .lines(
                                                text("eee", NamedTextColor.RED),
                                                text("eEe")
                                        )
                                )
                                .attributes(glowing(), hidden())
                                .lifecycleListenerRegistry(lifecycleListenerRegistry -> lifecycleListenerRegistry
                                        .add(beforeTickPointcut(), lifecycleListener -> lifecycleListener
                                                .priority(NamedPriority.NORMAL)
                                                .action(dummyAction())
                                        )
                                )
                        )
                        .lifecycleListenerRegistry(lifecycleListenerRegistry -> lifecycleListenerRegistry
                                .add(afterTickPointcut(), lifecycleListener -> lifecycleListener
                                        .priority(NamedPriority.HIGHEST)
                                        .action(dummyAction())
                                )
                        )
                )
                .background(background -> background
                        .addLocation(key("advancedgui:test/background"))
                )
                .lifecycleListenerRegistry(lifecycleListenerRegistry -> lifecycleListenerRegistry
                        .add(beforeTickPointcut(), lifecycleListener -> lifecycleListener
                                .priority(NamedPriority.NORMAL)
                                .action(dummyAction())
                        )
                        .add(afterTickPointcut(), lifecycleListener -> lifecycleListener
                                .priority(NamedPriority.LOW)
                                .action(dummyAction())
                        )
                )
        );
    }

    @Test
    void yamlLoad() throws IOException {
        assertThat(yamlLoader().loadString(rawYamlTemplate))
                .usingRecursiveComparison()
                .isEqualTo(template);
    }

    @Disabled("#saveString is not implemented")
    @Test
    void yamlLoadAndSave() throws IOException {
        GuiLoader yamlLoader = yamlLoader();
        GuiTemplate template = yamlLoader.loadString(rawYamlTemplate);
        assertThat(yamlLoader.saveString(template))
                .isEqualTo(rawYamlTemplate);
    }

    @Test
    void jsonLoad() throws IOException {
        assertThat(jsonLoader().loadString(rawJsonTemplate))
                .usingRecursiveComparison()
                .isEqualTo(template);
    }

    private GuiLoader yamlLoader() {
        return new ConfigurateGuiLoader(YamlConfigurationLoader::builder);
    }

    private GuiLoader jsonLoader() {
        return new ConfigurateGuiLoader(GsonConfigurationLoader::builder);
    }
}
