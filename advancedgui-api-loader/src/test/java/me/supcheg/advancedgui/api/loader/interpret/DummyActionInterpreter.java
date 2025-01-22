package me.supcheg.advancedgui.api.loader.interpret;

import me.supcheg.advancedgui.api.action.ActionContext;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandle;

import static java.lang.invoke.MethodHandles.empty;
import static java.lang.invoke.MethodType.methodType;

final class DummyActionInterpreter implements ActionInterpreter<DummyActionInterpretContext> {
    private static final MethodHandle doNothing = empty(methodType(void.class, ActionContext.class));

    @NotNull
    @Override
    public MethodHandle interpretMethodHandle(@NotNull DummyActionInterpretContext ctx) {
        return doNothing;
    }
}
