package me.supcheg.advancedgui.platform.paper.interpret.close;

import me.supcheg.advancedgui.api.loader.interpret.SimpleActionInterpretContextParser;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

final class CloseActionInterpretContextParser extends SimpleActionInterpretContextParser<CloseActionInterpretContext> {
    @NotNull
    @Override
    public CloseActionInterpretContext deserialize(@NotNull ConfigurationNode node) throws SerializationException {
        if (node.raw() instanceof String) {
            return CloseActionInterpretContext.DEFAULT;
        }

        return super.deserialize(node);
    }

    @Override
    public void serialize(@NotNull CloseActionInterpretContext ctx, @NotNull ConfigurationNode node) throws SerializationException {
        if (CloseActionInterpretContext.DEFAULT.equals(ctx)) {
            node.set(name);
            return;
        }

        super.serialize(ctx, node);
    }
}
