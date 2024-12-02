package me.supcheg.advancedgui.api.loader.configurate.interpret;

import io.leangen.geantyref.TypeToken;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import me.supcheg.advancedgui.api.loader.interpret.ActionInterpreterEntry;
import me.supcheg.advancedgui.api.loader.interpret.ActionInterpreterSource;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurationOptions;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.ObjectMapper;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
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
        String name = raw.name();
        return new ActionInterpreterEntry<>(
                name,
                constructInstance(raw.interpreter(), name),
                constructInstance(raw.contextParser(), name)
        );
    }

    @SneakyThrows
    @NotNull
    private <T> T constructInstance(@NotNull String className, @NotNull String name) {
        Constructor<?>[] constructors = Class.forName(className).getDeclaredConstructors();

        if (constructors.length != 1) {
            throw new IllegalArgumentException("Couldn't construct " + className);
        }

        Constructor<?> constructor = constructors[0];
        constructor.setAccessible(true);

        Object object = switch (constructor.getParameterCount()) {
            case 0 -> constructor.newInstance();
            case 1 -> {
                Class<?> parameterType = constructor.getParameterTypes()[0];
                if (!parameterType.isInstance(name)) {
                    throw new IllegalArgumentException("Couldn't construct " + className);
                }
                yield constructor.newInstance(name);
            }
            default -> throw new IllegalArgumentException("Couldn't construct " + className);
        };
        return (T) object;
    }

    @SneakyThrows
    private List<RawActionInterpreterEntry> parseInterpreters(URL url) {
        return YamlConfigurationLoader.builder()
                .defaultOptions(configurationOptions)
                .source(() -> new BufferedReader(new InputStreamReader(url.openStream())))
                .build()
                .load()
                .require(new TypeToken<>() {
                });
    }

    @ConfigSerializable
    private record RawActionInterpreterEntry(
            @NotNull String name,
            @NotNull String interpreter,
            @NotNull String contextParser
    ) {
    }
}
