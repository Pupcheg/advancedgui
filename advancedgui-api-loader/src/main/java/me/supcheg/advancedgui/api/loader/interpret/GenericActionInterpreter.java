package me.supcheg.advancedgui.api.loader.interpret;

import me.supcheg.advancedgui.api.action.ActionContext;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;

import static java.lang.invoke.MethodType.methodType;

public interface GenericActionInterpreter<C extends ActionInterpretContext> extends ActionInterpreter<C> {

    @NotNull
    @Override
    default MethodHandle interpretMethodHandle(@NotNull C ctx) {
        return MethodHandleAccessor.genericLambda_handle.bindTo(interpretGenericAction(ctx));
    }

    @NotNull
    GenericAction interpretGenericAction(@NotNull C ctx);

    @FunctionalInterface
    interface GenericAction {
        void handle(@NotNull ActionContext ctx);
    }

    class MethodHandleAccessor {
        private static final MethodHandle genericLambda_handle;

        static {
            try {
                genericLambda_handle = MethodHandles.publicLookup()
                        .findVirtual(GenericAction.class, "handle", methodType(void.class, ActionContext.class));
            } catch (NoSuchMethodException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
