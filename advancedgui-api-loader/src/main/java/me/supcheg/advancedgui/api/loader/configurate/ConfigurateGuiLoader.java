package me.supcheg.advancedgui.api.loader.configurate;

import me.supcheg.advancedgui.api.Advancedgui;
import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.api.button.attribute.ButtonAttribute;
import me.supcheg.advancedgui.api.gui.template.GuiTemplate;
import me.supcheg.advancedgui.api.lifecycle.LifecycleListenerRegistry;
import me.supcheg.advancedgui.api.lifecycle.Pointcut;
import me.supcheg.advancedgui.api.loader.GuiLoader;
import me.supcheg.advancedgui.api.loader.configurate.interpret.YamlClasspathActionInterpreterSource;
import me.supcheg.advancedgui.api.loader.configurate.serializer.action.ActionTypeSerializer;
import me.supcheg.advancedgui.api.loader.configurate.serializer.adventure.CustomNamespaceKeyTypeSerializer;
import me.supcheg.advancedgui.api.loader.configurate.serializer.adventure.KeyedTypeSerializer;
import me.supcheg.advancedgui.api.loader.configurate.serializer.adventure.StringComponentSerializerWrapperTypeSerializer;
import me.supcheg.advancedgui.api.loader.configurate.serializer.buildable.ClasspathInterfaceImplLookup;
import me.supcheg.advancedgui.api.loader.configurate.serializer.buildable.InterfaceImplTypeSerializer;
import me.supcheg.advancedgui.api.loader.configurate.serializer.layout.LayoutTemplateTypeSerializer;
import me.supcheg.advancedgui.api.loader.configurate.serializer.lifecycle.LifecycleListenerRegistryTypeSerializer;
import me.supcheg.advancedgui.api.loader.configurate.serializer.sequence.PriorityTypeSerializer;
import me.supcheg.advancedgui.api.loader.configurate.serializer.sequence.SequencedSortedSetTypeSerializer;
import me.supcheg.advancedgui.api.loader.interpret.ActionInterpretContext;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.loader.AbstractConfigurationLoader;
import org.spongepowered.configurate.objectmapping.ObjectMapper;
import org.spongepowered.configurate.serialize.TypeSerializerCollection;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Objects;

import static io.leangen.geantyref.GenericTypeReflector.erase;
import static me.supcheg.advancedgui.api.loader.configurate.io.BufferedIO.lazyBufferedOrNull;
import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

public abstract class ConfigurateGuiLoader<L extends AbstractConfigurationLoader<?>, B extends AbstractConfigurationLoader.Builder<B, L>> implements GuiLoader {

    private final TypeSerializerCollection serializers = makeSerializers(TypeSerializerCollection.defaults());

    @NotNull
    protected abstract B configurationLoaderBuilder();

    protected void configureConfigurationLoaderBuilder(@NotNull B builder) {
        builder.defaultOptions(defaultOptions -> defaultOptions.serializers(serializers));
    }

    @NotNull
    protected L buildConfigurationLoader(@NotNull B builder) {
        return builder.build();
    }

    @NotNull
    private L readLoader(@NotNull Reader in) {
        Objects.requireNonNull(in, "in");

        B builder = configurationLoaderBuilder();

        configureConfigurationLoaderBuilder(builder);
        builder.source(lazyBufferedOrNull(in));

        L loader = buildConfigurationLoader(builder);

        if (!loader.canLoad()) {
            throw new IllegalStateException(loader + " doesn't support loading");
        }

        return loader;
    }

    @NotNull
    private L writeLoader(@NotNull Writer out) {
        Objects.requireNonNull(out, "out");

        B builder = configurationLoaderBuilder();

        configureConfigurationLoaderBuilder(builder);
        builder.sink(lazyBufferedOrNull(out));

        L loader = buildConfigurationLoader(builder);

        if (!loader.canSave()) {
            throw new IllegalStateException(loader + " doesn't support saving");
        }

        return loader;
    }

    @NotNull
    private static TypeSerializerCollection makeSerializers(@NotNull TypeSerializerCollection root) {
        ObjectMapper.Factory objectFactory = ObjectMapper.factory();

        return root.childBuilder()
                .register(
                        new StringComponentSerializerWrapperTypeSerializer(miniMessage())
                )
                .register(
                        ActionTypeSerializer::isAction,
                        new ActionTypeSerializer(
                                new YamlClasspathActionInterpreterSource(ConfigurateGuiLoader.class.getClassLoader())
                        )
                )
                .register(
                        SequencedSortedSetTypeSerializer::isSequencedSortedSet,
                        new SequencedSortedSetTypeSerializer()
                )
                .register(
                        LayoutTemplateTypeSerializer::isExactLayoutTemplate,
                        new LayoutTemplateTypeSerializer()
                )
                .register(
                        new PriorityTypeSerializer()
                )
                .register(
                        new CustomNamespaceKeyTypeSerializer(Advancedgui.NAMESPACE)
                )
                .register(
                        ButtonAttribute.class,
                        (KeyedTypeSerializer<ButtonAttribute>) ButtonAttribute::buttonAttribute
                )
                .register(
                        Pointcut.class,
                        (KeyedTypeSerializer<Pointcut>) Pointcut::pointcut
                )
                .register(
                        type -> LifecycleListenerRegistry.class.isAssignableFrom(erase(type)),
                        new LifecycleListenerRegistryTypeSerializer()
                )
                .register(
                        Buildable.class,
                        new InterfaceImplTypeSerializer(
                                objectFactory.asTypeSerializer(),
                                new ClasspathInterfaceImplLookup()
                        )
                )
                .register(
                        ActionInterpretContext.class,
                        objectFactory.asTypeSerializer()
                )
                .registerAnnotatedObjects(objectFactory)
                .build();
    }

    @NotNull
    @Override
    public GuiTemplate loadResource(@NotNull Reader in) throws IOException {
        return readLoader(in)
                .load()
                .require(GuiTemplate.class);
    }

    @Override
    public void saveResource(@NotNull GuiTemplate template, @NotNull Writer writer) throws IOException {
        L loader = writeLoader(writer);
        loader.save(
                loader.createNode()
                        .set(GuiTemplate.class, template)
        );
    }
}
