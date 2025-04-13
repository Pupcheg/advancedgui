package me.supcheg.advancedgui.api.component;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface ComponentRendererCacheProvider {
    @NotNull
    @Contract("-> new")
    Map<Component, Component> newCacheMap();
}
