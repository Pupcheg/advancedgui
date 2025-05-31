package me.supcheg.advancedgui.api.loader.configurate.interpret;

import io.leangen.geantyref.TypeToken;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import me.supcheg.advancedgui.api.loader.interpret.ActionInterpreterEntry;
import me.supcheg.advancedgui.api.loader.interpret.ActionInterpreterSource;
import me.supcheg.advancedgui.api.loader.interpret.SimpleActionInterpretContextParser;
import org.spongepowered.configurate.ConfigurationOptions;
import org.spongepowered.configurate.objectmapping.ObjectMapper;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
public final class YamlClasspathActionInterpreterSource implements ActionInterpreterSource {
    private static final String INTERPRETERS_FILE = "action_interpreters.yml";

    private final ClassLoader classLoader;
    private final ConfigurationOptions configurationOptions = ConfigurationOptions.defaults()
            .serializers(serializers -> serializers
                    .register(RawActionInterpreterEntry.class, ObjectMapper.factory().asTypeSerializer())
            );

    @Override
    public Stream<ActionInterpreterEntry<?>> interpreters() {
        return classLoader.resources(INTERPRETERS_FILE)
                .map(this::parseInterpreters)
                .<RawActionInterpreterEntry>mapMulti(Iterable::forEach)
                .map(YamlClasspathActionInterpreterSource::parseRawActionInterpreterEntry);
    }

    private static ActionInterpreterEntry<?> parseRawActionInterpreterEntry(RawActionInterpreterEntry raw) {
        String name = raw.name();
        return new ActionInterpreterEntry<>(
                name,
                constructInstance(raw.interpreter(), name),
                constructInstance(raw.contextParser(), name)
        );
    }

    @SneakyThrows
    private static <T> T constructInstance(String classname, String typename) {
        Object instance = constructInstance(Class.forName(classname), typename);

        if (instance instanceof SimpleActionInterpretContextParser<?> parser) {
            SimpleActionInterpretContextParserReflection.setNameIfNotPresent(parser, typename);
        }

        @SuppressWarnings("unchecked")
        T cast = (T) instance;
        return cast;
    }

    private static Object constructInstance(Class<?> clazz, Object additionalParameter)
            throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();

        if (constructors.length != 1) {
            throw new IllegalArgumentException(
                    "Couldn't construct " + clazz + ". " +
                    "Must have exactly one constructor."
            );
        }

        Constructor<?> constructor = constructors[0];
        constructor.setAccessible(true);

        return switch (constructor.getParameterCount()) {
            case 0 -> constructor.newInstance();
            case 1 -> {
                Class<?> parameterType = constructor.getParameterTypes()[0];
                if (!parameterType.isInstance(additionalParameter)) {
                    throw new IllegalArgumentException(
                            "Couldn't construct " + clazz + ". " +
                            "Must have exactly one parameter of type " + parameterType + "."
                    );
                }
                yield constructor.newInstance(additionalParameter);
            }
            default -> throw new IllegalArgumentException(
                    "Couldn't construct " + clazz + ". " +
                    "Must have exactly one parameter."
            );
        };
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

    private record RawActionInterpreterEntry(
            String name,
            String interpreter,
            String contextParser
    ) {
    }
}
