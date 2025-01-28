package me.supcheg.advancedgui.api.loader.configurate.serializer.action;

import me.supcheg.advancedgui.api.action.Action;
import me.supcheg.advancedgui.api.loader.interpret.ActionInterpretContext;
import me.supcheg.advancedgui.api.loader.interpret.ActionInterpretContextParser;
import me.supcheg.advancedgui.api.loader.interpret.ActionInterpreterEntry;
import me.supcheg.advancedgui.api.loader.interpret.ActionInterpreterSource;
import me.supcheg.advancedgui.api.loader.interpret.InterpretedContext;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static io.leangen.geantyref.GenericTypeReflector.erase;
import static me.supcheg.advancedgui.api.loader.configurate.serializer.unchecked.Unchecked.uncheckedCast;

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

    @NotNull
    @Override
    public Action deserialize(@NotNull Type type, @NotNull ConfigurationNode node) throws SerializationException {
        Class<?> actionType = erase(type);
        String interfaceMethodName = actionType.getMethods()[0].getName();
        InterpretedContext interpretedContext = interpret(node);

        return (Action) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[]{actionType, ContextInterpreted.class},
                new InterpretedContextInvocationHandler(interpretedContext, interfaceMethodName)
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

            return switch (proxiedMethodName) {
                case "context" -> interpretedContext.context();
                case "parser" -> interpretedContext.parser();
                case "equals" -> equalsImpl(args);
                case "toString" -> ActionTypeSerializer.class + " proxy for " + interpretedContext;
                default -> proxiedMethod.invoke(interpretedContext, args);
            };
        }

        /**
         * {@link #equals(Object)}
         */
        private Object equalsImpl(Object[] args) {
            Object other = args[0];
            return other instanceof ContextInterpreted contextInterpreted &&
                   interpretedContext.context().equals(contextInterpreted.context());
        }
    }

    @NotNull
    private InterpretedContext interpret(@NotNull ConfigurationNode node) throws SerializationException {
        for (ActionInterpreterEntry<?> interpreter : interpreters) {
            if (interpreter.isAcceptable(node)) {
                InterpretedContext context = interpreter.parseAndInterpret(node);
                assertIsValid(context.methodHandle(), interpreter);
                return context;
            }
        }
        throw new SerializationException("Not found interpreter for " + node);
    }

    private void assertIsValid(@NotNull MethodHandle handle, @NotNull ActionInterpreterEntry<?> interpreter) {
        MethodType type = handle.type();
        if (!void.class.equals(type.returnType())) {
            throw new IllegalArgumentException(
                    "Required %s return type, but got %s from %s"
                            .formatted(void.class, type, interpreter.actionInterpreter())
            );
        }
    }

    @Override
    public void serialize(@NotNull Type type, @Nullable Action obj, @NotNull ConfigurationNode node) throws SerializationException {
        if (!(obj instanceof ContextInterpreted interpreted)) {
            throw new SerializationException("Cannot serialize " + obj);
        }

        ActionInterpretContext context = interpreted.context();
        ActionInterpretContextParser<?> parser = interpreted.parser();

        parser.serialize(uncheckedCast(context), node);
    }

    private interface ContextInterpreted {
        @NotNull
        ActionInterpretContextParser<?> parser();

        @NotNull
        ActionInterpretContext context();
    }
}
