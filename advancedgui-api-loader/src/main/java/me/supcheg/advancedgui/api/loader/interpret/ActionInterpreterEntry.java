package me.supcheg.advancedgui.api.loader.interpret;

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

    public InterpretedContext parseAndInterpret(ConfigurationNode node) throws SerializationException {
        C ctx = contextParser.deserialize(node);
        return new InterpretedContext(
                contextParser,
                ctx,
                actionInterpreter.interpretMethodHandle(ctx)
        );
    }
}
