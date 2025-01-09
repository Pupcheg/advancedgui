package me.supcheg.advancedgui.api.loader.configurate;

import me.supcheg.advancedgui.api.AdvancedGuiApi;
import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.api.button.attribute.ButtonAttribute;
import me.supcheg.advancedgui.api.gui.template.GuiTemplate;
import me.supcheg.advancedgui.api.lifecycle.LifecycleListenerRegistry;
import me.supcheg.advancedgui.api.lifecycle.Pointcut;
import me.supcheg.advancedgui.api.loader.GuiLoader;
import me.supcheg.advancedgui.api.loader.configurate.button.ButtonAttributeTypeSerializer;
import me.supcheg.advancedgui.api.loader.configurate.interpret.YamlClasspathActionInterpreterSource;
import me.supcheg.advancedgui.api.loader.configurate.serializer.action.ActionTypeSerializer;
import me.supcheg.advancedgui.api.loader.configurate.serializer.adventure.CustomNamespaceKeyTypeSerializer;
import me.supcheg.advancedgui.api.loader.configurate.serializer.adventure.StringComponentSerializerWrapperTypeSerializer;
import me.supcheg.advancedgui.api.loader.configurate.serializer.buildable.ClasspathInterfaceImplLookup;
import me.supcheg.advancedgui.api.loader.configurate.serializer.buildable.InterfaceImplTypeSerializer;
import me.supcheg.advancedgui.api.loader.configurate.serializer.layout.LayoutTemplateTypeSerializer;
import me.supcheg.advancedgui.api.loader.configurate.serializer.lifecycle.LifecycleListenerRegistryTypeSerializer;
import me.supcheg.advancedgui.api.loader.configurate.serializer.lifecycle.PointcutTypeSerializer;
import me.supcheg.advancedgui.api.loader.configurate.serializer.sequence.PriorityTypeSerializer;
import me.supcheg.advancedgui.api.loader.configurate.serializer.sequence.SequencedSortedSetTypeSerializer;
import me.supcheg.advancedgui.api.loader.interpret.ActionInterpretContext;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurationOptions;
import org.spongepowered.configurate.loader.AbstractConfigurationLoader;
import org.spongepowered.configurate.loader.ConfigurationLoader;
import org.spongepowered.configurate.objectmapping.ObjectMapper;
import org.spongepowered.configurate.serialize.TypeSerializerCollection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.concurrent.Callable;

import static io.leangen.geantyref.GenericTypeReflector.erase;
import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

public final class ConfigurateGuiLoader implements GuiLoader {

    private final ConfigurationLoaderOpener loaderBuilder;

    public ConfigurateGuiLoader(
            @NotNull ConfigurationLoaderBuilderFactory loaderFactory
    ) {
        ConfigurationOptions configurationOptions = ConfigurationOptions.defaults()
                .serializers(makeSerializers(TypeSerializerCollection.defaults()));

        this.loaderBuilder = in ->
                loaderFactory.newConfigurationLoader()
                        .defaultOptions(configurationOptions)
                        .source(asBufferedReaderCallable(in))
                        .build();
    }

    private TypeSerializerCollection makeSerializers(TypeSerializerCollection root) {
        ObjectMapper.Factory objectFactory = ObjectMapper.factory();

        return root.childBuilder()
                .register(
                        new StringComponentSerializerWrapperTypeSerializer(miniMessage())
                )
                .register(
                        ActionTypeSerializer::isAction,
                        new ActionTypeSerializer(
                                new YamlClasspathActionInterpreterSource(getClass().getClassLoader())
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
                        new CustomNamespaceKeyTypeSerializer(AdvancedGuiApi.NAMESPACE)
                )
                .register(
                        ButtonAttribute.class,
                        new ButtonAttributeTypeSerializer()
                )
                .register(
                        Pointcut.class,
                        new PointcutTypeSerializer()
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
    public static Callable<BufferedReader> asBufferedReaderCallable(@NotNull Reader reader) {
        BufferedReader bufferedReader = reader instanceof BufferedReader alreadyBuffered ?
                alreadyBuffered :
                new BufferedReader(reader);
        return () -> bufferedReader;
    }

    @NotNull
    @Override
    public GuiTemplate loadResource(@NotNull Reader in) throws IOException {
        return loaderBuilder.openLoader(in)
                .load()
                .require(GuiTemplate.class);
    }

    public interface ConfigurationLoaderBuilderFactory {
        @NotNull
        @Contract("-> new")
        AbstractConfigurationLoader.Builder<?, ?> newConfigurationLoader();
    }

    private interface ConfigurationLoaderOpener {
        @NotNull
        @Contract("_ -> new")
        ConfigurationLoader<?> openLoader(@NotNull Reader in);
    }
}
