package me.supcheg.advancedgui.api.builder;

import net.kyori.adventure.util.Buildable;

@SuppressWarnings("deprecation") // Buildable.Builder for compatibility
public interface AbstractBuilder<R> extends net.kyori.adventure.builder.AbstractBuilder<R>, Buildable.Builder<R> {
}
