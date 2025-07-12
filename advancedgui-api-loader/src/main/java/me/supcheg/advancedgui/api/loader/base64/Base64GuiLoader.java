package me.supcheg.advancedgui.api.loader.base64;

import me.supcheg.advancedgui.api.builder.AbstractBuilder;
import me.supcheg.advancedgui.api.builder.Buildable;
import me.supcheg.advancedgui.api.loader.GuiLoader;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.function.Consumer;

public interface Base64GuiLoader extends GuiLoader, Buildable<Base64GuiLoader, Base64GuiLoader.Builder> {
    static Base64GuiLoader jsonDownstreamBase64GuiLoader() {
        return Base64GuiLoaderImpl.Instances.JSON_DOWNSTREAM;
    }

    static Base64GuiLoader yamlDownstreamBase64GuiLoader() {
        return Base64GuiLoaderImpl.Instances.YAML_DOWNSTREAM;
    }

    static Builder base64GuiLoader() {
        return new Base64GuiLoaderImpl.BuilderImpl();
    }

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

        Builder downstream(GuiLoader downstream);

        @Nullable
        Encoder encoder();

        Builder encoder(Encoder encoder);

        @Nullable
        Decoder decoder();

        Builder decoder(Decoder decoder);
    }
}
