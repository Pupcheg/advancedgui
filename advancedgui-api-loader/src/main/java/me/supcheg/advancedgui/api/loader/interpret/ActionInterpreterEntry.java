package me.supcheg.advancedgui.api.loader.interpret;

import me.supcheg.advancedgui.api.action.ActionContext;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

public record ActionInterpreterEntry<C extends ActionInterpretContext>(
        String name,
        ActionInterpreter<C> actionInterpreter,
        ActionInterpretContextParser<C> contextParser
) {

    public boolean isAcceptable(ConfigurationNode node) {
        return contextParser.isAcceptable(node);
    }

    public InterpretedContext parseAndInterpret(ConfigurationNode node, Class<? extends ActionContext> targetActionContextType) throws SerializationException {
        C ctx = contextParser.deserialize(node);
        return new InterpretedContext(
                contextParser,
                ctx,
                actionInterpreter.interpretActionHandle(ctx, targetActionContextType)
        );
    }
}
