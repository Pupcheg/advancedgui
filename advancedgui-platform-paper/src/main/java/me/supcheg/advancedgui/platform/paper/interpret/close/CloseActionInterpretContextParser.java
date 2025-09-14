package me.supcheg.advancedgui.platform.paper.interpret.close;

import me.supcheg.advancedgui.api.loader.interpret.SimpleActionInterpretContextParser;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

final class CloseActionInterpretContextParser extends SimpleActionInterpretContextParser<CloseActionInterpretContext> {
    @Override
    public CloseActionInterpretContext deserialize(ConfigurationNode node) throws SerializationException {
        if (node.raw() instanceof String) {
            return CloseActionInterpretContext.DEFAULT;
        }

        return super.deserialize(node);
    }

    @Override
    public void serialize(CloseActionInterpretContext ctx, ConfigurationNode node) throws SerializationException {
        if (CloseActionInterpretContext.DEFAULT.equals(ctx)) {
            node.set(name);
            return;
        }

        super.serialize(ctx, node);
    }
}
