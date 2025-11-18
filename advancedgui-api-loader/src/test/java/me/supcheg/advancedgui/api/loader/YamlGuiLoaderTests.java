package me.supcheg.advancedgui.api.loader;

import me.supcheg.advancedgui.api.gui.template.GuiTemplate;
import me.supcheg.advancedgui.api.loader.yaml.YamlGuiLoader;
import me.supcheg.advancedgui.api.sequence.NamedPriority;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static me.supcheg.advancedgui.api.button.attribute.ButtonAttribute.glowing;
import static me.supcheg.advancedgui.api.button.attribute.ButtonAttribute.hidden;
import static me.supcheg.advancedgui.api.coordinate.Coordinate.coordinate;
import static me.supcheg.advancedgui.api.gui.template.GuiTemplate.gui;
import static me.supcheg.advancedgui.api.layout.template.AnvilLayoutTemplate.anvilLayout;
import static me.supcheg.advancedgui.api.lifecycle.pointcut.TickPointcut.afterTickPointcut;
import static me.supcheg.advancedgui.api.lifecycle.pointcut.TickPointcut.beforeTickPointcut;
import static me.supcheg.advancedgui.api.loader.interpret.DummyAction.dummyAction;
import static me.supcheg.advancedgui.api.loader.yaml.YamlGuiLoader.yamlGuiLoader;
import static net.kyori.adventure.key.Key.key;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.Style.style;
import static org.junit.jupiter.api.Assertions.assertEquals;

class YamlGuiLoaderTests {
    YamlGuiLoader loader;
    @Language("yaml")
    String yamlTemplate;
    GuiTemplate template;

    @BeforeEach
    void setup() {
        loader = yamlGuiLoader();

        yamlTemplate = """
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

        template = gui(gui -> gui
                .key(key("advancedgui:test/test"))
                .layout(anvilLayout(anvilLayout -> anvilLayout
                        .addInputUpdateListener(inputUpdateListener -> inputUpdateListener
                                .priority(NamedPriority.NORMAL)
                                .action(dummyAction())
                        )
                        .addButton(button -> button
                                .addCoordinate(coordinate(0, 0))
                                .addInteraction(interaction -> interaction
                                        .priority(NamedPriority.NORMAL)
                                        .action(dummyAction())
                                )
                                .texture(key("advancedgui:test/interaction"))
                                .name(text("Hi!", style(TextDecoration.BOLD)))
                                .description(description -> description
                                        .addLine(text("eee", NamedTextColor.RED))
                                        .addLine(text("eEe"))
                                )
                                .attributes(Set.of(glowing(), hidden()))
                                .lifecycleListenerRegistry(lifecycleListenerRegistry -> lifecycleListenerRegistry
                                        .add(lifecycleListener -> lifecycleListener
                                                .pointcut(beforeTickPointcut())
                                                .priority(NamedPriority.NORMAL)
                                                .action(dummyAction())
                                        )
                                )
                        )
                        .lifecycleListenerRegistry(lifecycleListenerRegistry -> lifecycleListenerRegistry
                                .add(lifecycleListener -> lifecycleListener
                                        .pointcut(afterTickPointcut())
                                        .priority(NamedPriority.HIGHEST)
                                        .action(dummyAction())
                                )
                        )
                ))
                .background(background -> background
                        .addLocation(key("advancedgui:test/background"))
                )
                .lifecycleListenerRegistry(lifecycleListenerRegistry -> lifecycleListenerRegistry
                        .add(lifecycleListener -> lifecycleListener
                                .pointcut(beforeTickPointcut())
                                .priority(NamedPriority.NORMAL)
                                .action(dummyAction())
                        )
                        .add(lifecycleListener -> lifecycleListener
                                .pointcut(afterTickPointcut())
                                .priority(NamedPriority.LOW)
                                .action(dummyAction())
                        )
                )
        );
    }

    @Test
    void yamlLoad() {
        assertEquals(
                template,
                loader.readString(yamlTemplate)
        );
    }

    @Test
    void yamlSaveAndLoad() {
        assertEquals(
                template,
                loader.readString(loader.writeString(template))
        );
    }
}
