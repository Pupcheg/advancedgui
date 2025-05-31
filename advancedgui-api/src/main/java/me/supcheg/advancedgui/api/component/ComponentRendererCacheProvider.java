package me.supcheg.advancedgui.api.component;

import net.kyori.adventure.text.Component;

import java.util.Map;

public interface ComponentRendererCacheProvider {
    Map<Component, Component> newCacheMap();
}
