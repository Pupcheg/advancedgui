package me.supcheg.advancedgui.platform.paper.interpret.util;

import lombok.RequiredArgsConstructor;
import org.intellij.lang.annotations.Language;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.serialize.TypeSerializerCollection;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.BufferedReader;
import java.io.StringReader;

@RequiredArgsConstructor
public final class SimpleYamlConfigurationLoader {
    private final TypeSerializerCollection serializers;

    public <T> T require(Class<T> type, @Language("yaml") String raw) throws ConfigurateException {
        return YamlConfigurationLoader.builder()
                .defaultOptions(defaultOptions -> defaultOptions
                        .serializers(serializers)
                )
                .source(() -> new BufferedReader(new StringReader(raw)))
                .build()
                .load()
                .require(type);
    }
}
