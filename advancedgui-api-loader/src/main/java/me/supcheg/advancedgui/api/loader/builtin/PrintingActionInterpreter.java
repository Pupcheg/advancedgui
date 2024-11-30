package me.supcheg.advancedgui.api.loader.builtin;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.supcheg.advancedgui.api.loader.interpret.ActionInterpreter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;

import static java.lang.invoke.MethodHandles.dropArguments;
import static java.lang.invoke.MethodType.methodType;

@Slf4j
public final class PrintingActionInterpreter implements ActionInterpreter<PrintingActionInterpretContext> {
    @SneakyThrows
    @NotNull
    @Override
    public MethodHandle interpret(@NotNull PrintingActionInterpretContext ctx) {
        return dropArguments(
                MethodHandles.publicLookup().findVirtual(Logger.class, "info", methodType(void.class, String.class))
                        .bindTo(log)
                        .bindTo(ctx.message()),
                0,
                Object.class
        );
    }
}
