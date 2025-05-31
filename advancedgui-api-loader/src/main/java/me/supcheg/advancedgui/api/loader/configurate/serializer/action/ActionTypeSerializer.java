package me.supcheg.advancedgui.api.loader.configurate.serializer.action;

import lombok.extern.slf4j.Slf4j;
import me.supcheg.advancedgui.api.action.Action;
import me.supcheg.advancedgui.api.loader.interpret.ActionInterpretContext;
import me.supcheg.advancedgui.api.loader.interpret.ActionInterpretContextParser;
import me.supcheg.advancedgui.api.loader.interpret.ActionInterpreterEntry;
import me.supcheg.advancedgui.api.loader.interpret.ActionInterpreterSource;
import me.supcheg.advancedgui.api.loader.interpret.InterpretedContext;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static io.leangen.geantyref.GenericTypeReflector.erase;

@Slf4j
public final class ActionTypeSerializer implements TypeSerializer<Action> {
    private final List<ActionInterpreterEntry<?>> interpreters;

    public static boolean isAction(Type type) {
        return Action.class.isAssignableFrom(erase(type));
    }

    public ActionTypeSerializer(ActionInterpreterSource... interpreterSources) {
        this(Arrays.asList(interpreterSources));
    }

    public ActionTypeSerializer(Collection<ActionInterpreterSource> interpreterSources) {
        this.interpreters = interpreterSources.stream()
                .flatMap(ActionInterpreterSource::interpreters)
                .toList();
        if (log.isDebugEnabled()) {
            log.debug("Loaded interpreters: {}", interpreters.stream().map(ActionInterpreterEntry::name).toList());
        }
    }

    @Override
    public Action deserialize(Type type, ConfigurationNode node) throws SerializationException {
        Class<?> actionType = erase(type);
        String interfaceMethodName = findFunctionalInterfaceMethodName(actionType);
        InterpretedContext interpretedContext = interpret(node);

        return (Action) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[]{actionType, ContextInterpreted.class},
                new InterpretedContextInvocationHandler(interpretedContext, interfaceMethodName)
        );
    }

    private static String findFunctionalInterfaceMethodName(Class<?> interfaceClass) throws SerializationException {
        if (!interfaceClass.isInterface()) {
            throw new SerializationException("Interface class " + interfaceClass + " is not an interface");
        }

        return Arrays.stream(interfaceClass.getMethods())
                .filter(method -> Modifier.isAbstract(method.getModifiers()))
                .map(Method::getName)
                .findFirst()
                .orElseThrow(() -> new SerializationException(interfaceClass + " has no any non-default method"));
    }

    private record InterpretedContextInvocationHandler(
            InterpretedContext interpretedContext,
            String interfaceMethodName
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

    private InterpretedContext interpret(ConfigurationNode node) throws SerializationException {
        for (ActionInterpreterEntry<?> interpreter : interpreters) {
            if (interpreter.isAcceptable(node)) {
                InterpretedContext context = interpreter.parseAndInterpret(node);
                assertIsValid(context.methodHandle(), interpreter);
                return context;
            }
        }
        throw new SerializationException("Not found interpreter for " + node);
    }

    private void assertIsValid(MethodHandle handle, ActionInterpreterEntry<?> interpreter) {
        MethodType type = handle.type();
        if (!void.class.equals(type.returnType())) {
            throw new IllegalArgumentException(
                    "Required %s return type, but got %s from %s"
                            .formatted(void.class, type, interpreter.actionInterpreter())
            );
        }
    }

    @Override
    public void serialize(Type type, @Nullable Action obj, ConfigurationNode node) throws SerializationException {
        if (!(obj instanceof ContextInterpreted interpreted)) {
            throw new SerializationException("Cannot serialize " + obj);
        }

        ActionInterpretContext context = interpreted.context();
        @SuppressWarnings("unchecked") // safe cast
        ActionInterpretContextParser<ActionInterpretContext> parser =
                (ActionInterpretContextParser<ActionInterpretContext>) interpreted.parser();

        parser.serialize(context, node);
    }

    private interface ContextInterpreted {
        ActionInterpretContextParser<?> parser();

        ActionInterpretContext context();
    }
}
