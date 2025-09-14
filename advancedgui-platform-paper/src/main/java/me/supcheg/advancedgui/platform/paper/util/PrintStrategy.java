package me.supcheg.advancedgui.platform.paper.util;

import lombok.SneakyThrows;
import me.supcheg.advancedgui.api.gui.template.GuiTemplate;
import net.kyori.adventure.text.Component;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.joining;
import static me.supcheg.advancedgui.api.loader.base64.Base64GuiLoader.jsonDownstreamBase64GuiLoader;
import static me.supcheg.advancedgui.api.loader.json.JsonGuiLoader.jsonGuiLoader;
import static me.supcheg.advancedgui.api.loader.yaml.YamlGuiLoader.yamlGuiLoader;
import static net.kyori.examination.string.MultiLineStringExaminer.simpleEscaping;

public enum PrintStrategy {
    EXAMINATION {
        @Override
        public Component print(GuiTemplate template) {
            return simpleEscaping()
                    .examine(template)
                    .collect(collectingAndThen(joining("\n"), Component::text));
        }
    },
    YAML {
        @SneakyThrows
        @Override
        public Component print(GuiTemplate template) {
            return Component.text(
                    yamlGuiLoader().writeString(template)
            );
        }
    },
    JSON {
        @SneakyThrows
        @Override
        public Component print(GuiTemplate template) {
            return Component.text(
                    jsonGuiLoader().writeString(template)
            );
        }
    },
    BASE64 {
        @SneakyThrows
        @Override
        public Component print(GuiTemplate template) {
            return Component.text(
                    jsonDownstreamBase64GuiLoader().writeString(template)
            );
        }
    };

    public abstract Component print(GuiTemplate template);
}
