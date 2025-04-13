package me.supcheg.advancedgui.api.component.guava;

import com.google.common.cache.Cache;
import me.supcheg.advancedgui.api.component.ComponentRendererCacheProvider;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface GuavaComponentRendererCacheProvider extends ComponentRendererCacheProvider {

    @NotNull
    @Contract("-> new")
    Cache<Component, Component> newGuavaCache();

    @Override
    @NotNull
    default Map<Component, Component> newCacheMap() {
        return newGuavaCache().asMap();
    }
}
