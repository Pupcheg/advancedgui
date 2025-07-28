package me.supcheg.advancedgui.api.loader.configurate.serializer.action;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FunctionalInterfaceUtilities {
    private static final Map<Class<?>, Method> FUNCTIONAL_INTERFACE_METHODS = new ConcurrentHashMap<>();

    public static Method getFunctionalInterfaceMethod(Class<?> clazz) {
        if (!clazz.isInterface()) {
            throw new IllegalArgumentException("Interface class " + clazz + " is not an interface");
        }

        return FUNCTIONAL_INTERFACE_METHODS.computeIfAbsent(clazz, FunctionalInterfaceUtilities::findFunctionalInterfaceMethod);
    }

    @SneakyThrows
    private static Method findFunctionalInterfaceMethod(Class<?> interfaceClass) {
        var it = Arrays.stream(interfaceClass.getMethods())
                .filter(method -> Modifier.isAbstract(method.getModifiers()))
                .iterator();

        if (!it.hasNext()) {
            throw new IllegalArgumentException("Interface class " + interfaceClass + " doesn't have an abstract method");
        }

        Method first = it.next();

        if (it.hasNext()) {
            throw new IllegalArgumentException("Interface class " + interfaceClass + " has more than one abstract method");
        }

        return first;
    }
}
