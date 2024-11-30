package me.supcheg.advancedgui.api.loader.interpret;

import me.supcheg.advancedgui.api.action.ActionContext;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;

import static java.lang.invoke.MethodType.methodType;

public interface GenericActionInterpreter<C extends ActionInterpretContext> extends ActionInterpreter<C> {

    @NotNull
    @Override
    default MethodHandle interpret(@NotNull C ctx) {
        return MethodHandleAccessor.genericLambda_handle.bindTo(interpretLambda(ctx));
    }

    @NotNull
    GenericLambda interpretLambda(@NotNull C ctx);

    @FunctionalInterface
    interface GenericLambda {
        void handle(@NotNull ActionContext ctx);
    }

    class MethodHandleAccessor {
        private static final MethodHandle genericLambda_handle;

        int a() {
            return 0;
        }

        static {
            try {
                genericLambda_handle = MethodHandles.publicLookup()
                        .findVirtual(GenericLambda.class, "handle", methodType(void.class, ActionContext.class));
            } catch (NoSuchMethodException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
