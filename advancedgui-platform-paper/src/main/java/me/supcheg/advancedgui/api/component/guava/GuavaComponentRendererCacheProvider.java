package me.supcheg.advancedgui.api.component.guava;

import com.google.common.cache.Cache;
import me.supcheg.advancedgui.api.component.ComponentRendererCacheProvider;
import net.kyori.adventure.text.Component;

import java.util.Map;

public interface GuavaComponentRendererCacheProvider extends ComponentRendererCacheProvider {

    Cache<Component, Component> newGuavaCache();

    @Override
    default Map<Component, Component> newCacheMap() {
        return newGuavaCache().asMap();
    }
}
