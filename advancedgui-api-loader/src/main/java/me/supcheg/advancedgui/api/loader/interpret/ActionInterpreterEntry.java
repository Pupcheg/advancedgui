package me.supcheg.advancedgui.api.loader.interpret;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

public record ActionInterpreterEntry<C extends ActionInterpretContext>(
        @NotNull String name,
        @NotNull ActionInterpreter<C> actionInterpreter,
        @NotNull ActionInterpretContextParser<C> contextParser
) {

    public boolean isAcceptable(@NotNull ConfigurationNode node) {
        return contextParser.isAcceptable(node);
    }

    @NotNull
    public InterpretedContext parseAndInterpret(@NotNull ConfigurationNode node) throws SerializationException {
        C ctx = contextParser.deserialize(node);
        return new InterpretedContext(
                ctx,
                actionInterpreter.interpret(ctx)
        );
    }
}
