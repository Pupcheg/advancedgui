package me.supcheg.advancedgui.api.loader.interpret;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurationNode;

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
}
