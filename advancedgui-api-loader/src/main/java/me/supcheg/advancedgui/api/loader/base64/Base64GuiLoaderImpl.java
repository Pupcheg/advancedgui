package me.supcheg.advancedgui.api.loader.base64;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import me.supcheg.advancedgui.api.gui.template.GuiTemplate;
import me.supcheg.advancedgui.api.loader.GuiLoader;
import me.supcheg.advancedgui.api.loader.json.JsonGuiLoader;
import me.supcheg.advancedgui.api.loader.yaml.YamlGuiLoader;
import net.kyori.adventure.util.Services;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.Objects;
import java.util.Optional;

import static me.supcheg.advancedgui.api.loader.base64.Base64GuiLoader.base64GuiLoader;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
final class Base64GuiLoaderImpl implements Base64GuiLoader {
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType") // paper-like api contract
    private static final Optional<Provider> SERVICE = Services.service(Provider.class);

    private final GuiLoader downstream;
    private final Decoder decoder;
    private final Encoder encoder;

    @Override
    public GuiTemplate read(InputStream in) throws IOException {
        return downstream.read(decoder.wrap(in));
    }

    @Override
    public void write(GuiTemplate template, OutputStream original) throws IOException {
        try (var out = encoder.wrap(new NonClosingOutputStream(original))) { // wrapped OS performs some write operations on close
            downstream.write(template, out);
        }
    }

    private static final class NonClosingOutputStream extends FilterOutputStream {
        private NonClosingOutputStream(OutputStream out) {
            super(out);
        }

        @Override
        public void close() {
            // nothing
        }
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    static final class Instances {
        static final Base64GuiLoader JSON_DOWNSTREAM = SERVICE
                .map(Provider::jsonDownstreamBase64GuiLoader)
                .orElseGet(() ->
                        base64GuiLoader(base64GuiLoader -> base64GuiLoader
                                .downstream(JsonGuiLoader.jsonGuiLoader())
                                .decoder(Base64.getDecoder())
                                .encoder(Base64.getEncoder())
                        )
                );

        static final Base64GuiLoader YAML_DOWNSTREAM = SERVICE
                .map(Provider::yamlDownstreamBase64GuiLoader)
                .orElseGet(() ->
                        base64GuiLoader(base64GuiLoader -> base64GuiLoader
                                .downstream(YamlGuiLoader.yamlGuiLoader())
                                .decoder(Base64.getDecoder())
                                .encoder(Base64.getEncoder())
                        )
                );
    }

    @Override
    public Builder toBuilder() {
        return new BuilderImpl(this);
    }

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    static final class BuilderImpl implements Builder {
        private @Nullable GuiLoader downstream;
        private @Nullable Decoder decoder;
        private @Nullable Encoder encoder;

        BuilderImpl(Base64GuiLoaderImpl impl) {
            this.downstream = impl.downstream;
            this.decoder = impl.decoder;
            this.encoder = impl.encoder;
        }

        @Override
        @Nullable
        public GuiLoader downstream() {
            return downstream;
        }

        @Override
        public Builder downstream(GuiLoader downstream) {
            Objects.requireNonNull(downstream, "downstream");
            this.downstream = downstream;
            return this;
        }

        @Override
        @Nullable
        public Encoder encoder() {
            return encoder;
        }

        @Override
        public Builder encoder(Encoder encoder) {
            Objects.requireNonNull(encoder, "encoder");
            this.encoder = encoder;
            return this;
        }

        @Override
        @Nullable
        public Decoder decoder() {
            return decoder;
        }

        @Override
        public Builder decoder(Decoder decoder) {
            Objects.requireNonNull(decoder, "decoder");
            this.decoder = decoder;
            return this;
        }

        @Override
        public Base64GuiLoader build() {
            return new Base64GuiLoaderImpl(
                    Objects.requireNonNull(downstream, "downstream"),
                    Objects.requireNonNull(decoder, "decoder"),
                    Objects.requireNonNull(encoder, "encoder")
            );
        }
    }
}
