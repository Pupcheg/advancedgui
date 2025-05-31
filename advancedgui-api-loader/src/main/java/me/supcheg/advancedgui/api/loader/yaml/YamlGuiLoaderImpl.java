package me.supcheg.advancedgui.api.loader.yaml;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.supcheg.advancedgui.api.loader.configurate.ConfigurateGuiLoader;
import net.kyori.adventure.util.Services;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.util.Optional;

final class YamlGuiLoaderImpl extends ConfigurateGuiLoader<YamlConfigurationLoader, YamlConfigurationLoader.Builder> implements YamlGuiLoader {
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType") // paper-like api contract
    private static final Optional<Provider> SERVICE = Services.service(Provider.class);

    @Override
    protected YamlConfigurationLoader.Builder configurationLoaderBuilder() {
        return YamlConfigurationLoader.builder();
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    static final class Instances {
        static final YamlGuiLoader INSTANCE = SERVICE
                .map(Provider::yamlGuiLoader)
                .orElseGet(YamlGuiLoaderImpl::new);
    }
}
