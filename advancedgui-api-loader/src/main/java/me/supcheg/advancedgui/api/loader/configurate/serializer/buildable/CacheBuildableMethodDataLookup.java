package me.supcheg.advancedgui.api.loader.configurate.serializer.buildable;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
public class CacheBuildableMethodDataLookup implements BuildableMethodDataLookup {
    private final BuildableMethodDataLookup delegate;
    private final Map<Class<?>, MethodDataReport> cache = new ConcurrentHashMap<>();

    @NotNull
    @Override
    public MethodDataReport buildReport(@NotNull Class<?> clazz) {
        return cache.computeIfAbsent(clazz, delegate::buildReport);
    }
}
