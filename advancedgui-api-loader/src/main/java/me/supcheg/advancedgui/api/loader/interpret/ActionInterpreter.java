package me.supcheg.advancedgui.api.loader.interpret;

import me.supcheg.advancedgui.api.action.ActionContext;

import java.lang.invoke.MethodHandle;

public interface ActionInterpreter<C extends ActionInterpretContext> {
    /**
     * {@code void(}{@link ActionContext}{@code )}
     */
    MethodHandle interpretMethodHandle(C ctx);
}
