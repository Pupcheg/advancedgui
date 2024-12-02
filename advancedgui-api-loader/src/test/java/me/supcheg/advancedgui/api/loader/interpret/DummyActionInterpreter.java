package me.supcheg.advancedgui.api.loader.interpret;

import org.jetbrains.annotations.NotNull;

final class DummyActionInterpreter implements GenericActionInterpreter<DummyActionInterpretContext> {
    private static final GenericAction ACTION = ctx -> {/* Hi! I'm dummy */};

    @NotNull
    @Override
    public GenericAction interpretGenericAction(@NotNull DummyActionInterpretContext ctx) {
        return ACTION;
    }
}
