package me.supcheg.advancedgui.api.loader.interpret;

import me.supcheg.advancedgui.api.action.ActionContext;
import org.jetbrains.annotations.NotNull;

import java.lang.invoke.MethodHandle;

public interface ActionInterpreter<C extends ActionInterpretContext> {
    /**
     * {@code void(}{@link ActionContext}{@code )}
     */
    @NotNull
    MethodHandle interpretMethodHandle(@NotNull C ctx);
}
