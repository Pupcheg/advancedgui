package me.supcheg.advancedgui.api.loader.interpret;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

@RequiredArgsConstructor
final class DummyActionInterpretContextParser implements ActionInterpretContextParser<DummyActionInterpretContext> {
    private final String name;

    @Override
    public boolean isAcceptable(@NotNull ConfigurationNode node) {
        return name.equals(ActionInterpretContextParser.parseType(node));
    }

    @NotNull
    @Override
    public DummyActionInterpretContext deserialize(@NotNull ConfigurationNode node) {
        return DummyActionInterpretContext.INSTANCE;
    }

    @Override
    public void serialize(@Nullable DummyActionInterpretContext ctx, @NotNull ConfigurationNode node) throws SerializationException {
        node.set(name);
    }
}
