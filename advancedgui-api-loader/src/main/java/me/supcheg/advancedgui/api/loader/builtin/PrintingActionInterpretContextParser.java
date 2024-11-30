package me.supcheg.advancedgui.api.loader.builtin;

import me.supcheg.advancedgui.api.loader.interpret.ActionInterpretContextParser;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

public final class PrintingActionInterpretContextParser implements ActionInterpretContextParser<PrintingActionInterpretContext> {
    private static final String TYPE = "print";

    @Override
    public boolean isAcceptable(@NotNull ConfigurationNode node) {
        return node.isMap() && TYPE.equals(ActionInterpretContextParser.parseType(node));
    }

    @NotNull
    @Override
    public PrintingActionInterpretContext deserialize(ConfigurationNode node) throws SerializationException {
        return node.require(PrintingActionInterpretContext.class);
    }
}
