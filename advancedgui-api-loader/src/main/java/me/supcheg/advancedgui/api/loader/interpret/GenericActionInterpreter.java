package me.supcheg.advancedgui.api.loader.interpret;

import me.supcheg.advancedgui.api.action.ActionContext;

import java.lang.invoke.MethodHandle;

import static java.lang.invoke.MethodHandles.publicLookup;
import static java.lang.invoke.MethodType.methodType;
import static lombok.Lombok.sneakyThrow;

public interface GenericActionInterpreter<C extends ActionInterpretContext> extends ActionInterpreter<C> {

    @Override
    default MethodHandle interpretMethodHandle(C ctx) {
        return MethodHandleAccessor.GenericAction_handle.bindTo(interpretGenericAction(ctx));
    }

    GenericAction interpretGenericAction(C ctx);

    @FunctionalInterface
    interface GenericAction {
        void handle(ActionContext ctx);
    }

    class MethodHandleAccessor {
        private static final MethodHandle GenericAction_handle;

        static {
            try {
                GenericAction_handle = publicLookup()
                        .findVirtual(GenericAction.class, "handle", methodType(void.class, ActionContext.class));
            } catch (NoSuchMethodException | IllegalAccessException e) {
                throw sneakyThrow(e);
            }
        }
    }
}
