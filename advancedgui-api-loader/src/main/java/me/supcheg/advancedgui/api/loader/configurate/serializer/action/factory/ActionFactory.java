package me.supcheg.advancedgui.api.loader.configurate.serializer.action.factory;

import me.supcheg.advancedgui.api.action.Action;
import me.supcheg.advancedgui.api.loader.interpret.InterpretedContext;

public interface ActionFactory {
    <A extends Action & ContextInterpreted> A createAction(Class<A> requiredType, InterpretedContext ctx);
}
