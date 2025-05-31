package me.supcheg.advancedgui.api.loader.json;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.supcheg.advancedgui.api.loader.configurate.ConfigurateGuiLoader;
import net.kyori.adventure.util.Services;
import org.spongepowered.configurate.jackson.JacksonConfigurationLoader;

import java.util.Optional;

final class JsonGuiLoaderImpl extends ConfigurateGuiLoader<JacksonConfigurationLoader, JacksonConfigurationLoader.Builder> implements JsonGuiLoader {
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType") // paper-like api contract
    private static final Optional<Provider> SERVICE = Services.service(Provider.class);

    @Override
    protected JacksonConfigurationLoader.Builder configurationLoaderBuilder() {
        return JacksonConfigurationLoader.builder();
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    static final class Instances {
        static final JsonGuiLoader INSTANCE = SERVICE
                .map(Provider::jsonGuiLoader)
                .orElseGet(JsonGuiLoaderImpl::new);
    }
}
