package me.supcheg.advancedgui.api.loader.interpret;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import me.supcheg.advancedgui.api.action.Action;
import me.supcheg.advancedgui.api.loader.configurate.interpret.YamlClasspathActionInterpreterSource;
import me.supcheg.advancedgui.api.loader.configurate.serializer.action.ActionTypeSerializer;
import me.supcheg.advancedgui.api.loader.configurate.serializer.action.factory.EnvironmentActionFactoryProvider;

import static org.spongepowered.configurate.BasicConfigurationNode.root;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DummyAction {
    private static final String NAME = "dummy";

    private static final ActionTypeSerializer ACTION_TYPE_SERIALIZER = new ActionTypeSerializer(
            new EnvironmentActionFactoryProvider().makeActionFactory(),
            new YamlClasspathActionInterpreterSource(DummyAction.class.getClassLoader())
    );

    @SafeVarargs
    public static <A extends Action> A dummyAction(A ... typeResolver) {
        assert typeResolver.length == 0;
        @SuppressWarnings("unchecked")
        Class<A> type = (Class<A>) typeResolver.getClass().getComponentType();
        return dummyAction(type);
    }

    @SneakyThrows
    public static <A extends Action> A dummyAction(Class<A> type) {
        @SuppressWarnings("unchecked")
        A action = (A) ACTION_TYPE_SERIALIZER.deserialize(type, root().set(NAME));
        return action;
    }
}
