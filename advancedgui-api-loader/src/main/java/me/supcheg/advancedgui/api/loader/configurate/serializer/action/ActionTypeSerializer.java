package me.supcheg.advancedgui.api.loader.configurate.serializer.action;

import me.supcheg.advancedgui.api.action.Action;
import me.supcheg.advancedgui.api.action.ActionContext;
import me.supcheg.advancedgui.api.loader.configurate.serializer.action.factory.ActionFactory;
import me.supcheg.advancedgui.api.loader.configurate.serializer.action.factory.ContextInterpreted;
import me.supcheg.advancedgui.api.loader.interpret.ActionInterpretContext;
import me.supcheg.advancedgui.api.loader.interpret.ActionInterpretContextParser;
import me.supcheg.advancedgui.api.loader.interpret.ActionInterpreterEntry;
import me.supcheg.advancedgui.api.loader.interpret.ActionInterpreterSource;
import me.supcheg.advancedgui.api.loader.interpret.InterpretedContext;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static io.leangen.geantyref.GenericTypeReflector.erase;

public final class ActionTypeSerializer implements TypeSerializer<Action> {
    private final ActionFactory actionFactory;
    private final List<ActionInterpreterEntry<?>> interpreters;

    public static boolean isAction(Type type) {
        return Action.class.isAssignableFrom(erase(type));
    }

    public ActionTypeSerializer(ActionFactory factory, ActionInterpreterSource... interpreterSources) {
        this(factory, Arrays.asList(interpreterSources));
    }

    public ActionTypeSerializer(ActionFactory factory, Collection<ActionInterpreterSource> interpreterSources) {
        this.actionFactory = factory;
        this.interpreters = interpreterSources.stream()
                .flatMap(ActionInterpreterSource::interpreters)
                .toList();
    }

    @Override
    public Action deserialize(Type type, ConfigurationNode node) throws SerializationException {
        Class<?> erased = erase(type);
        // noinspection unchecked, rawtypes
        return actionFactory.createAction((Class) erased, interpret(erased, node));
    }

    private InterpretedContext interpret(Class<?> type, ConfigurationNode node) throws SerializationException {
        for (ActionInterpreterEntry<?> interpreter : interpreters) {
            if (interpreter.isAcceptable(node)) {
                return interpreter.parseAndInterpret(node, getContextType(FunctionalInterfaceUtilities.getFunctionalInterfaceMethod(type)));
            }
        }
        throw new SerializationException("Not found interpreter for " + node);
    }

    private static Class<? extends ActionContext> getContextType(Method method) {
        // noinspection unchecked
        return (Class<? extends ActionContext>) method.getParameterTypes()[0];
    }

    @Override
    public void serialize(Type type, @Nullable Action obj, ConfigurationNode node) throws SerializationException {
        if (!(obj instanceof ContextInterpreted interpreted)) {
            throw new SerializationException("Cannot serialize " + obj);
        }

        ActionInterpretContext context = interpreted.context();
        @SuppressWarnings("unchecked") // safe cast
        ActionInterpretContextParser<ActionInterpretContext> parser =
                (ActionInterpretContextParser<ActionInterpretContext>) interpreted.parser();

        parser.serialize(context, node);
    }
}
