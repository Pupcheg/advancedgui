package me.supcheg.advancedgui.api.loader.interpret;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

import java.lang.reflect.Type;

@RequiredArgsConstructor
final class DummyActionInterpretContextParser implements ActionInterpretContextParser<DummyActionInterpretContext> {
    private final String name;

    @Override
    public boolean isAcceptable(@NotNull ConfigurationNode node) {
        return name.equals(node.getString());
    }

    @NotNull
    @Override
    public DummyActionInterpretContext deserialize(@NotNull ConfigurationNode node) {
        return DummyActionInterpretContext.INSTANCE;
    }

    @Override
    public void serialize(@NotNull Type type, @Nullable DummyActionInterpretContext ctx, @NotNull ConfigurationNode node) throws SerializationException {
        node.set(name);
    }
}
