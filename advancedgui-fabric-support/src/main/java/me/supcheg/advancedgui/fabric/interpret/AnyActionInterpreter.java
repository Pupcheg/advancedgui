package me.supcheg.advancedgui.fabric.interpret;

import me.supcheg.advancedgui.api.action.ActionContext;
import me.supcheg.advancedgui.api.loader.interpret.ActionInterpreter;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandle;

import static java.lang.invoke.MethodHandles.empty;
import static java.lang.invoke.MethodType.methodType;

final class AnyActionInterpreter implements ActionInterpreter<AnyActionInterpretContext> {
    private final MethodHandle action = empty(methodType(void.class, ActionContext.class));

    @NotNull
    @Override
    public MethodHandle interpretMethodHandle(@NotNull AnyActionInterpretContext ctx) {
        return action;
    }
}
