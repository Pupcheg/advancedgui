package me.supcheg.advancedgui.api.loader.configurate.serializer.buildable;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import me.supcheg.advancedgui.api.builder.AbstractBuilder;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.util.NamingScheme;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import static java.lang.reflect.Modifier.isStatic;

@RequiredArgsConstructor
public class DefaultBuildableMethodDataLookup implements BuildableMethodDataLookup {
    private static final String BUILDER = "builder";
    private static final List<String> GET_METHOD_BLACKLIST = List.of("build");
    private final MethodHandles.Lookup lookup = MethodHandles.publicLookup();
    private final NamingScheme namingScheme;

    @NotNull
    @Override
    public MethodDataReport buildReport(@NotNull Class<?> buildableClass) {
        Class<?> builderClass = findBuilderClass(buildableClass);

        MethodHandle builderFactory = Arrays.stream(buildableClass.getMethods())
                .filter(method -> isStatic(method.getModifiers()))
                .filter(method -> builderClass.equals(method.getReturnType()))
                .map(this::unreflect)
                .peek(DefaultBuildableMethodDataLookup::validateBuilderFactory)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No builder factory found for " + buildableClass));

        Method[] builderClassMethods = builderClass.getMethods();

        List<ValueMethodData> values = Arrays.stream(builderClassMethods)
                .filter(getMethod -> getMethod.getParameterCount() == 0)
                .filter(getMethod -> !GET_METHOD_BLACKLIST.contains(getMethod.getName()))
                .map(getMethod -> {
                            Type returnType = getMethod.getGenericReturnType();
                            return new ValueMethodData(
                                    namingScheme.coerce(getMethod.getName()),
                                    returnType,
                                    Arrays.stream(builderClassMethods)
                                            .filter(setMethod -> setMethod.getParameterCount() == 1)
                                            .filter(setMethod ->
                                                    returnType.equals(setMethod.getGenericParameterTypes()[0])
                                            )
                                            .map(this::unreflect)
                                            .findFirst()
                                            .orElseThrow(() -> new IllegalStateException("No setMethod found for " + getMethod.getName())),
                                    unreflect(getMethod)
                            );
                        }
                )
                .toList();

        return new MethodDataReport(builderFactory, values);
    }

    private static void validateBuilderFactory(@NotNull MethodHandle methodHandle) {
        MethodType type = methodHandle.type();
        if (type.parameterCount() != 0) {
            throw new IllegalStateException("Builder factory must have no parameters. Actual: " + type);
        }

        if (!AbstractBuilder.class.isAssignableFrom(type.returnType())) {
            throw new IllegalStateException("Builder factory must return AbstractBuilder inheritor. Actual: " + type);
        }
    }

    @SneakyThrows
    private MethodHandle unreflect(Method method) {
        return lookup.unreflect(method);
    }

    @NotNull
    private static Class<?> findBuilderClass(@NotNull Class<?> buildable) {
        return Arrays.stream(buildable.getClasses())
                .filter(clazz -> clazz.getSimpleName().equalsIgnoreCase(BUILDER))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No builder class found for " + buildable));
    }
}
