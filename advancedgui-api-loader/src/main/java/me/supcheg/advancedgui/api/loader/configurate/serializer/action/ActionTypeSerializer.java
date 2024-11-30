package me.supcheg.advancedgui.api.loader.configurate.serializer.action;

import lombok.SneakyThrows;
import me.supcheg.advancedgui.api.action.Action;
import me.supcheg.advancedgui.api.loader.interpret.ActionInterpretContext;
import me.supcheg.advancedgui.api.loader.interpret.ActionInterpreterEntry;
import me.supcheg.advancedgui.api.loader.interpret.ActionInterpreterSource;
import me.supcheg.advancedgui.api.loader.interpret.InterpretedContext;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static io.leangen.geantyref.GenericTypeReflector.erase;

public final class ActionTypeSerializer implements TypeSerializer<Action> {
    private final List<ActionInterpreterEntry<?>> interpreters;

    public static boolean isAction(@NotNull Type type) {
        return Action.class.isAssignableFrom(erase(type));
    }

    public ActionTypeSerializer(ActionInterpreterSource... interpreterSources) {
        this(Arrays.asList(interpreterSources));
    }

    public ActionTypeSerializer(@NotNull Collection<ActionInterpreterSource> interpreterSources) {
        this.interpreters = interpreterSources.stream()
                .flatMap(ActionInterpreterSource::interpreters)
                .toList();
    }

    @SneakyThrows
    @NotNull
    @Override
    public Action deserialize(@NotNull Type type, @NotNull ConfigurationNode node) {
        Class<?> actionType = erase(type);
        String interfaceMethodType = actionType.getMethods()[0].getName();
        InterpretedContext interpretedContext = interpret(node);

        return (Action) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[]{actionType, ContextInterpreted.class},
                new InterpretedContextInvocationHandler(interpretedContext, interfaceMethodType)
        );
    }

    private record InterpretedContextInvocationHandler(
            @NotNull InterpretedContext interpretedContext,
            @NotNull String interfaceMethodName
    ) implements InvocationHandler {
        @Override
        public Object invoke(Object proxy, Method proxiedMethod, Object[] args) throws Throwable {
            String proxiedMethodName = proxiedMethod.getName();

            if (interfaceMethodName.equals(proxiedMethodName)) {
                MethodHandle methodHandle = interpretedContext.methodHandle();
                return methodHandle.invokeWithArguments(args);
            }

            if ("context".equals(proxiedMethodName)) {
                return interpretedContext.context();
            }

            if ("toString".equals(proxiedMethodName)) {
                return ActionTypeSerializer.class + " proxy for " + interpretedContext;
            }

            return proxiedMethod.invoke(interpretedContext, args);
        }
    }

    @NotNull
    private InterpretedContext interpret(@NotNull ConfigurationNode node) throws SerializationException {
        for (ActionInterpreterEntry<?> interpreter : interpreters) {
            if (interpreter.isAcceptable(node)) {
                return interpreter.parseAndInterpret(node);
            }
        }
        throw new UnsupportedOperationException("Not found interpreter for " + node);
    }

    @Override
    public void serialize(@NotNull Type type, @Nullable Action obj, @NotNull ConfigurationNode node) throws SerializationException {
        if (obj instanceof ContextInterpreted interpreted) {
            node.set(interpreted.context());
        }
        throw new SerializationException("Cannot serialize " + obj);
    }

    private interface ContextInterpreted {
        @NotNull
        ActionInterpretContext context();
    }
}
