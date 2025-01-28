package me.supcheg.advancedgui.platform.paper.interpret.util;

import lombok.RequiredArgsConstructor;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.serialize.TypeSerializerCollection;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.StringReader;

import static me.supcheg.advancedgui.api.loader.configurate.io.BufferedIO.lazyBufferedOrNull;

@RequiredArgsConstructor
public final class SimpleYamlConfigurationLoader {
    private final TypeSerializerCollection serializers;

    @NotNull
    public <T> T require(@NotNull Class<T> type, @Language("yaml") @NotNull String raw) throws ConfigurateException {
        return YamlConfigurationLoader.builder()
                .defaultOptions(defaultOptions -> defaultOptions
                        .serializers(serializers)
                )
                .source(lazyBufferedOrNull(new StringReader(raw)))
                .build()
                .load()
                .require(type);
    }
}
