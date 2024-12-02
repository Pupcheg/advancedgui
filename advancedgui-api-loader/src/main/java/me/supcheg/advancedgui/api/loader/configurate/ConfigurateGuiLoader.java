package me.supcheg.advancedgui.api.loader.configurate;

import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.api.gui.template.GuiTemplate;
import me.supcheg.advancedgui.api.loader.GuiLoader;
import me.supcheg.advancedgui.api.loader.configurate.serializer.action.ActionTypeSerializer;
import me.supcheg.advancedgui.api.loader.configurate.serializer.adventure.ComponentTypeSerializer;
import me.supcheg.advancedgui.api.loader.configurate.serializer.adventure.KeyTypeSerializer;
import me.supcheg.advancedgui.api.loader.configurate.serializer.buildable.ClasspathInterfaceImplLookup;
import me.supcheg.advancedgui.api.loader.configurate.serializer.buildable.InterfaceImplTypeSerializer;
import me.supcheg.advancedgui.api.loader.configurate.serializer.layout.LayoutTemplateTypeSerializer;
import me.supcheg.advancedgui.api.loader.configurate.serializer.sequence.PositionedCollectionTypeSerializer;
import me.supcheg.advancedgui.api.loader.configurate.serializer.sequence.PriorityTypeSerializer;
import me.supcheg.advancedgui.api.loader.configurate.serializer.sequence.SequencedSortedSetTypeSerializer;
import me.supcheg.advancedgui.api.loader.interpret.ActionInterpretContext;
import me.supcheg.advancedgui.api.loader.configurate.interpret.YamlClasspathActionInterpreterSource;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurationOptions;
import org.spongepowered.configurate.loader.AbstractConfigurationLoader;
import org.spongepowered.configurate.loader.ConfigurationLoader;
import org.spongepowered.configurate.serialize.TypeSerializerCollection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.concurrent.Callable;

import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;
import static org.spongepowered.configurate.objectmapping.ObjectMapper.factory;

public final class ConfigurateGuiLoader implements GuiLoader {
    private final ConfigurationLoaderOpener loaderBuilder;

    public ConfigurateGuiLoader(
            @NotNull ConfigurationLoaderBuilderFactory loaderFactory
    ) {
        TypeSerializerCollection serializers = TypeSerializerCollection.defaults()
                .childBuilder()
                .register(
                        ActionTypeSerializer::isAction,
                        new ActionTypeSerializer(
                                new YamlClasspathActionInterpreterSource(getClass().getClassLoader())
                        )
                )
                .register(
                        PositionedCollectionTypeSerializer::isPositionedCollection,
                        new PositionedCollectionTypeSerializer()
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
                        new ComponentTypeSerializer(
                                miniMessage()
                        )
                )
                .register(
                        new PriorityTypeSerializer()
                )
                .register(
                        new KeyTypeSerializer()
                )
                .register(
                        Buildable.class,
                        new InterfaceImplTypeSerializer(
                                factory().asTypeSerializer(),
                                new ClasspathInterfaceImplLookup()
                        )
                )
                .register(
                        ActionInterpretContext.class,
                        factory().asTypeSerializer()
                )
                .build();

        ConfigurationOptions configurationOptions = ConfigurationOptions.defaults()
                .serializers(serializers);

        this.loaderBuilder = in ->
                loaderFactory.newConfigurationLoader()
                        .defaultOptions(configurationOptions)
                        .source(asBufferedReaderCallable(in))
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

    @NotNull
    @Override
    public GuiLoader.Builder toBuilder() {
        throw new UnsupportedOperationException();
    }

    public interface ConfigurationLoaderBuilderFactory {
        @NotNull
        @Contract("-> new")
        AbstractConfigurationLoader.Builder<?, ?> newConfigurationLoader();
    }

    public interface ConfigurationLoaderOpener {
        @NotNull
        @Contract("_ -> new")
        ConfigurationLoader<?> openLoader(@NotNull Reader in);
    }
}
