package me.supcheg.advancedgui.api.loader.configurate.serializer.action.factory;

import me.supcheg.advancedgui.api.loader.interpret.ActionInterpretContext;
import me.supcheg.advancedgui.api.loader.interpret.ActionInterpretContextParser;

public interface ContextInterpreted {
    ActionInterpretContextParser<?> parser();

    ActionInterpretContext context();
}