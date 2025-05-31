package me.supcheg.advancedgui.api.loader.configurate;

import me.supcheg.advancedgui.api.Advancedgui;
import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.api.button.attribute.ButtonAttribute;
import me.supcheg.advancedgui.api.coordinate.Coordinate;
import me.supcheg.advancedgui.api.gui.template.GuiTemplate;
import me.supcheg.advancedgui.api.lifecycle.LifecycleListenerRegistry;
import me.supcheg.advancedgui.api.lifecycle.pointcut.Pointcut;
import me.supcheg.advancedgui.api.loader.GuiLoader;
import me.supcheg.advancedgui.api.loader.configurate.interpret.YamlClasspathActionInterpreterSource;
import me.supcheg.advancedgui.api.loader.configurate.serializer.action.ActionTypeSerializer;
import me.supcheg.advancedgui.api.loader.configurate.serializer.adventure.CustomNamespaceKeyTypeSerializer;
import me.supcheg.advancedgui.api.loader.configurate.serializer.adventure.KeyedTypeSerializer;
import me.supcheg.advancedgui.api.loader.configurate.serializer.adventure.StringComponentSerializerWrapperTypeSerializer;
import me.supcheg.advancedgui.api.loader.configurate.serializer.buildable.BuildableInterfaceTypeSerializer;
import me.supcheg.advancedgui.api.loader.configurate.serializer.buildable.CacheBuildableMethodDataLookup;
import me.supcheg.advancedgui.api.loader.configurate.serializer.buildable.DefaultBuildableMethodDataLookup;
import me.supcheg.advancedgui.api.loader.configurate.serializer.coordinate.CoordinateTypeSerializer;
import me.supcheg.advancedgui.api.loader.configurate.serializer.layout.LayoutTemplateTypeSerializer;
import me.supcheg.advancedgui.api.loader.configurate.serializer.lifecycle.LifecycleListenerRegistryTypeSerializer;
import me.supcheg.advancedgui.api.loader.configurate.serializer.sequence.PriorityTypeSerializer;
import me.supcheg.advancedgui.api.loader.configurate.serializer.sequence.QueueTypeSerializer;
import me.supcheg.advancedgui.api.loader.interpret.ActionInterpretContext;
import org.spongepowered.configurate.loader.AbstractConfigurationLoader;
import org.spongepowered.configurate.objectmapping.ObjectMapper;
import org.spongepowered.configurate.serialize.TypeSerializerCollection;
import org.spongepowered.configurate.util.NamingSchemes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Objects;

import static io.leangen.geantyref.GenericTypeReflector.erase;
import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

public abstract class ConfigurateGuiLoader<L extends AbstractConfigurationLoader<?>, B extends AbstractConfigurationLoader.Builder<B, L>> implements GuiLoader {

    private final TypeSerializerCollection serializers = makeSerializers(TypeSerializerCollection.defaults());

    protected abstract B configurationLoaderBuilder();

    protected void configureConfigurationLoaderBuilder(B builder) {
        builder.defaultOptions(defaultOptions -> defaultOptions.serializers(serializers));
    }

    protected L buildConfigurationLoader(B builder) {
        return builder.build();
    }

    private L readLoader(InputStream in) {
        Objects.requireNonNull(in, "in");

        B builder = configurationLoaderBuilder();

        configureConfigurationLoaderBuilder(builder);
        builder.source(() -> new BufferedReader(new InputStreamReader(in)));

        L loader = buildConfigurationLoader(builder);

        if (!loader.canLoad()) {
            throw new IllegalStateException(loader + " doesn't support loading");
        }

        return loader;
    }

    private L writeLoader(OutputStream out) {
        Objects.requireNonNull(out, "out");

        B builder = configurationLoaderBuilder();

        configureConfigurationLoaderBuilder(builder);
        builder.sink(() -> new BufferedWriter(new OutputStreamWriter(out)));

        L loader = buildConfigurationLoader(builder);

        if (!loader.canSave()) {
            throw new IllegalStateException(loader + " doesn't support saving");
        }

        return loader;
    }

    public static TypeSerializerCollection makeSerializers(TypeSerializerCollection root) {
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
                        QueueTypeSerializer::isQueue,
                        new QueueTypeSerializer()
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
                        Coordinate.class,
                        new CoordinateTypeSerializer()
                )
                .register(
                        type -> LifecycleListenerRegistry.class.isAssignableFrom(erase(type)),
                        new LifecycleListenerRegistryTypeSerializer()
                )
                .register(
                        Buildable.class,
                        new BuildableInterfaceTypeSerializer(
                                new CacheBuildableMethodDataLookup(
                                        new DefaultBuildableMethodDataLookup(NamingSchemes.LOWER_CASE_DASHED)
                                )
                        )
                )
                .register(
                        ActionInterpretContext.class,
                        objectFactory.asTypeSerializer()
                )
                .registerAnnotatedObjects(objectFactory)
                .build();
    }

    @Override
    public GuiTemplate read(InputStream in) throws IOException {
        return readLoader(in)
                .load()
                .require(GuiTemplate.class);
    }

    @Override
    public void write(GuiTemplate template, OutputStream out) throws IOException {
        L loader = writeLoader(out);
        loader.save(
                loader.createNode()
                        .set(GuiTemplate.class, template)
        );
    }
}
