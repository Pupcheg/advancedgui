package me.supcheg.advancedgui.api.loader.interpret;

import me.supcheg.advancedgui.api.action.ActionContext;

import java.lang.invoke.MethodHandle;

import static java.lang.invoke.MethodHandles.empty;
import static java.lang.invoke.MethodType.methodType;

final class DummyActionInterpreter implements ActionInterpreter<DummyActionInterpretContext> {
    private static final MethodHandle doNothing = empty(methodType(void.class, ActionContext.class));

    @Override
    public MethodHandle interpretMethodHandle(DummyActionInterpretContext ctx) {
        return doNothing;
    }
}
