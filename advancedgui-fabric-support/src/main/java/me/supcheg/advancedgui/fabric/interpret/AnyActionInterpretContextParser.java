package me.supcheg.advancedgui.fabric.interpret;

import me.supcheg.advancedgui.api.loader.interpret.ActionInterpretContextParser;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

final class AnyActionInterpretContextParser implements ActionInterpretContextParser<AnyActionInterpretContext> {
    @Override
    public boolean isAcceptable(ConfigurationNode node) {
        return true;
    }

    @Override
    public AnyActionInterpretContext deserialize(ConfigurationNode node) {
        return new AnyActionInterpretContext(node.raw());
    }

    @Override
    public void serialize(AnyActionInterpretContext ctx, ConfigurationNode node) throws SerializationException {
        node.set(ctx.value());
    }
}
