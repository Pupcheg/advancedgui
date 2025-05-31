package me.supcheg.advancedgui.api.loader.interpret;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

public abstract class SimpleActionInterpretContextParser<C extends ActionInterpretContext> implements ActionInterpretContextParser<C> {
    protected final @UnknownNullability String name;
    protected final Class<C> contextType;

    protected SimpleActionInterpretContextParser(@Nullable String name, Class<C> contextType) {
        this.name = name; // will be inserted via reflection if null
        this.contextType = contextType;
    }

    protected SimpleActionInterpretContextParser(Class<C> contextType) {
        this(null, contextType);
    }

    @SafeVarargs
    protected SimpleActionInterpretContextParser(C ... actionContextTypeArray) {
        this(null, obtainContextType(actionContextTypeArray));
    }

    @SafeVarargs
    protected SimpleActionInterpretContextParser(String name, C ... actionContextTypeArray) {
        this(name, obtainContextType(actionContextTypeArray));
    }

    private static <C> Class<C> obtainContextType(C [] actionContextTypeArray) {
        if (actionContextTypeArray.length != 0) {
            throw new IllegalArgumentException("Action type array must be empty");
        }
        Class<?> arrayType = actionContextTypeArray.getClass().getComponentType();

        if (Object.class.equals(arrayType)) {
            throw new IllegalArgumentException("Couldn't automatically get the action context type. Use another constructor instead");
        }

        //noinspection unchecked
        return (Class<C>) arrayType;
    }

    @Override
    public boolean isAcceptable(ConfigurationNode node) {
        return name.equals(ActionInterpretContextParser.parseType(node));
    }

    @Override
    public C deserialize(ConfigurationNode node) throws SerializationException {
        return node.require(contextType);
    }

    @Override
    public void serialize(C ctx, ConfigurationNode node) throws SerializationException {
        node.node(TYPE_KEY).set(name);
        node.set(ctx.getClass(), ctx);
    }
}
