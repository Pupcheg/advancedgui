package me.supcheg.advancedgui.api.loader.interpret;

import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

@RequiredArgsConstructor
final class DummyActionInterpretContextParser implements ActionInterpretContextParser<DummyActionInterpretContext> {
    private final String name;

    @Override
    public boolean isAcceptable(ConfigurationNode node) {
        return name.equals(ActionInterpretContextParser.parseType(node));
    }

    @Override
    public DummyActionInterpretContext deserialize(ConfigurationNode node) {
        return DummyActionInterpretContext.INSTANCE;
    }

    @Override
    public void serialize(@Nullable DummyActionInterpretContext ctx, ConfigurationNode node) throws SerializationException {
        node.set(name);
    }
}
