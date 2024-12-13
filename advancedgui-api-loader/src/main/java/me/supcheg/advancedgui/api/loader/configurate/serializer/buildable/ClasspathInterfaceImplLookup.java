package me.supcheg.advancedgui.api.loader.configurate.serializer.buildable;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import static io.leangen.geantyref.GenericTypeReflector.erase;
import static io.leangen.geantyref.TypeFactory.parameterizedClass;

@Slf4j
public final class ClasspathInterfaceImplLookup implements InterfaceImplLookup {
    private static final String IMPL_SUFFIX = "Impl";

    @NotNull
    @Override
    public Type findImpl(@NotNull Type interfaceType) {
        Class<?> interfaceClass = erase(interfaceType);
        Class<?> implClass = findImpl(interfaceClass);

        if (interfaceType instanceof ParameterizedType parameterized) {
            return parameterizedClass(implClass, parameterized.getActualTypeArguments());
        }

        return implClass;
    }

    @NotNull
    public Class<?> findImpl(@NotNull Class<?> interfaceClass) {
        if (!interfaceClass.isInterface()) {
            log.warn("Got not a interface {}", interfaceClass);
            return interfaceClass;
        }

        String implClassName = interfaceClass.getName() + IMPL_SUFFIX;

        Class<?> implClass;
        try {
            implClass = interfaceClass.getClassLoader().loadClass(implClassName);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot find implementation class " + implClassName, e);
        }

        if (!interfaceClass.isAssignableFrom(implClass)) {
            throw new IllegalStateException("Implementation class " + implClassName + " does not implement " + interfaceClass);
        }

        return implClass;
    }
}
