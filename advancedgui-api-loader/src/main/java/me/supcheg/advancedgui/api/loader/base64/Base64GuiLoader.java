package me.supcheg.advancedgui.api.loader.base64;

import me.supcheg.advancedgui.api.builder.AbstractBuilder;
import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.api.loader.GuiLoader;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.util.Base64;
import java.util.function.Consumer;

public interface Base64GuiLoader extends GuiLoader, Buildable<Base64GuiLoader, Base64GuiLoader.Builder> {
    static Base64GuiLoader jsonDownstreamBase64GuiLoader() {
        return Base64GuiLoaderImpl.Instances.JSON_DOWNSTREAM;
    }

    static Base64GuiLoader yamlDownstreamBase64GuiLoader() {
        return Base64GuiLoaderImpl.Instances.YAML_DOWNSTREAM;
    }

    @Contract(value = "-> new", pure = true)
    static Builder base64GuiLoader() {
        return new Base64GuiLoaderImpl.BuilderImpl();
    }

    @Contract(value = "_ -> new", pure = true)
    static Base64GuiLoader base64GuiLoader(Consumer<Builder> consumer) {
        return Buildable.configureAndBuild(base64GuiLoader(), consumer);
    }

    interface Provider {
        Base64GuiLoader jsonDownstreamBase64GuiLoader();

        Base64GuiLoader yamlDownstreamBase64GuiLoader();
    }

    interface Builder extends AbstractBuilder<Base64GuiLoader> {
        @Nullable
        GuiLoader downstream();

        @Contract("_ -> this")
        Builder downstream(GuiLoader downstream);

        @Nullable
        Base64.Encoder encoder();

        @Contract("_ -> this")
        Builder encoder(Base64.Encoder encoder);

        @Nullable
        Base64.Decoder decoder();

        @Contract("_ -> this")
        Builder decoder(Base64.Decoder decoder);
    }
}
