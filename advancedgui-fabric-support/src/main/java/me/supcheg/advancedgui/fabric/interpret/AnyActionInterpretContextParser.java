package me.supcheg.advancedgui.fabric.interpret;

import me.supcheg.advancedgui.api.loader.interpret.ActionInterpretContextParser;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

final class AnyActionInterpretContextParser implements ActionInterpretContextParser<AnyActionInterpretContext> {
    @Override
    public boolean isAcceptable(@NotNull ConfigurationNode node) {
        return true;
    }

    @NotNull
    @Override
    public AnyActionInterpretContext deserialize(@NotNull ConfigurationNode node) {
        return new AnyActionInterpretContext(node.raw());
    }

    @Override
    public void serialize(@NotNull AnyActionInterpretContext ctx, @NotNull ConfigurationNode node) throws SerializationException {
        node.set(ctx.value());
    }
}
