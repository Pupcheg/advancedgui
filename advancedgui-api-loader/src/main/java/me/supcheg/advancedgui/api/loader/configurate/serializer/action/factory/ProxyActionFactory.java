package me.supcheg.advancedgui.api.loader.configurate.serializer.action.factory;

import me.supcheg.advancedgui.api.action.Action;
import me.supcheg.advancedgui.api.action.ActionContext;
import me.supcheg.advancedgui.api.loader.configurate.serializer.action.ActionTypeSerializer;
import me.supcheg.advancedgui.api.loader.interpret.InterpretedContext;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Objects;

final class ProxyActionFactory implements ActionFactory {
    @Override
    public <A extends Action & ContextInterpreted> A createAction(Class<A> requiredType, InterpretedContext ctx) {
        Object generatedClass = Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[]{requiredType, ContextInterpreted.class},
                new InterpretedContextInvocationHandler(ctx, findFunctionalInterfaceMethodName(requiredType))
        );
        // noinspection unchecked
        return (A) generatedClass;
    }

    private static String findFunctionalInterfaceMethodName(Class<?> interfaceClass) {
        if (!interfaceClass.isInterface()) {
            throw new IllegalArgumentException("Interface class " + interfaceClass + " is not an interface");
        }

        return Arrays.stream(interfaceClass.getMethods())
                .filter(method -> Modifier.isAbstract(method.getModifiers()))
                .map(Method::getName)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(interfaceClass + " has no any non-default method"));
    }

    private record InterpretedContextInvocationHandler(
            InterpretedContext interpretedContext,
            String interfaceMethodName
    ) implements InvocationHandler {
        @Override
        @Nullable
        public Object invoke(Object proxy, Method proxiedMethod, Object[] args) throws Throwable {
            String proxiedMethodName = proxiedMethod.getName();

            if (interfaceMethodName.equals(proxiedMethodName)) {
                interpretedContext.actionHandle().handle(uncheckedCast(args[0]));
                return null;
            }

            return switch (proxiedMethodName) {
                case "context" -> interpretedContext.context();
                case "parser" -> interpretedContext.parser();
                case "equals" -> equalsImpl(args);
                case "toString" -> ActionTypeSerializer.class + " proxy for " + interpretedContext;
                default -> proxiedMethod.invoke(interpretedContext, args);
            };
        }

        private static <C extends ActionContext> C uncheckedCast(Object ctx) {
            return (C) ctx;
        }

        /**
         * {@link #equals(Object)}
         */
        private Object equalsImpl(@Nullable Object[] args) {
            @Nullable Object o = Objects.requireNonNull(args[0]);

            if (o == null) {
                return false;
            }

            if (getClass() != o.getClass()) {
                return false;
            }

            ContextInterpreted other = (ContextInterpreted) o;

            return getClass().equals(other.getClass()) &&
                   interpretedContext.context().equals(other.context());
        }
    }
}
