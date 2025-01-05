package me.supcheg.advancedgui.api.loader.interpret;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import me.supcheg.advancedgui.api.action.Action;
import me.supcheg.advancedgui.api.loader.configurate.serializer.action.ActionTypeSerializer;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.AttributedConfigurationNode;

import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DummyAction {
    private static final String NAME = "dummy";

    private static final ActionTypeSerializer ACTION_TYPE_SERIALIZER = new ActionTypeSerializer(
            () -> Stream.of(
                    new ActionInterpreterEntry<>(
                            NAME,
                            new DummyActionInterpreter(),
                            new DummyActionInterpretContextParser(NAME)
                    )
            )
    );

    @SafeVarargs
    public static <A extends Action> A dummyAction(@NotNull A @NotNull ... typeResolver) {
        assert typeResolver.length == 0;
        @SuppressWarnings("unchecked")
        Class<A> type = (Class<A>) typeResolver.getClass().getComponentType();
        return dummyAction(type);
    }

    @SneakyThrows
    public static <A extends Action> A dummyAction(@NotNull Class<A> type) {
        @SuppressWarnings("unchecked")
        A action = (A) ACTION_TYPE_SERIALIZER.deserialize(type, AttributedConfigurationNode.root().set(NAME));
        return action;
    }
}
