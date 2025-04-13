package me.supcheg.advancedgui.platform.paper.interpret.close;

import lombok.RequiredArgsConstructor;
import me.supcheg.advancedgui.api.loader.interpret.ActionInterpretContextParser;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

@RequiredArgsConstructor
final class CloseActionInterpretContextParser implements ActionInterpretContextParser<CloseActionInterpretContext> {
    private final String name;

    @Override
    public boolean isAcceptable(@NotNull ConfigurationNode node) {
        return name.equals(ActionInterpretContextParser.parseType(node));
    }

    @NotNull
    @Override
    public CloseActionInterpretContext deserialize(@NotNull ConfigurationNode node) throws SerializationException {
        if (node.raw() instanceof String) {
            return CloseActionInterpretContext.DEFAULT;
        }

        return node.require(CloseActionInterpretContext.class);
    }

    @Override
    public void serialize(@NotNull CloseActionInterpretContext ctx, @NotNull ConfigurationNode node) throws SerializationException {
        if (CloseActionInterpretContext.DEFAULT.equals(ctx)) {
            node.set(name);
            return;
        }

        ActionInterpretContextParser.super.serialize(ctx, node);
    }
}
