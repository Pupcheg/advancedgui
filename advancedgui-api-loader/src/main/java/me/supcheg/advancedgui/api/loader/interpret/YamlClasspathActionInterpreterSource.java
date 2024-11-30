package me.supcheg.advancedgui.api.loader.interpret;

import io.leangen.geantyref.TypeToken;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurationOptions;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.ObjectMapper;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
public final class YamlClasspathActionInterpreterSource implements ActionInterpreterSource {
    private static final String INTERPRETERS_FILE = "action_interpreters.yml";

    private final ClassLoader classLoader;
    private final ConfigurationOptions configurationOptions = ConfigurationOptions.defaults()
            .serializers(serializers -> serializers
                    .registerAnnotatedObjects(ObjectMapper.factory())
            );


    @NotNull
    @Override
    public Stream<ActionInterpreterEntry<?>> interpreters() {
        return classLoader.resources(INTERPRETERS_FILE)
                .map(this::parseInterpreters)
                .<RawActionInterpreterEntry>mapMulti(Iterable::forEach)
                .map(this::parseRawActionInterpreterEntry);
    }

    @NotNull
    @Contract("_ -> new")
    private ActionInterpreterEntry<?> parseRawActionInterpreterEntry(RawActionInterpreterEntry raw) {
        return new ActionInterpreterEntry<>(
                raw.name(),
                constructInstance(raw.interpreter()),
                constructInstance(raw.contextParser())
        );
    }

    @SneakyThrows
    @NotNull
    private <T> T constructInstance(@NotNull String className) {
        return (T) Class.forName(className).getConstructors()[0].newInstance();
    }

    @SneakyThrows
    private List<RawActionInterpreterEntry> parseInterpreters(URL url) {
        return YamlConfigurationLoader.builder()
                .defaultOptions(configurationOptions)
                .source(() -> new BufferedReader(new InputStreamReader(url.openStream())))
                .build()
                .load()
                .require(new TypeToken<>() { });
    }

    @ConfigSerializable
    private record RawActionInterpreterEntry(
            @NotNull String name,
            @NotNull String interpreter,
            @NotNull String contextParser
    ) {
    }
}
