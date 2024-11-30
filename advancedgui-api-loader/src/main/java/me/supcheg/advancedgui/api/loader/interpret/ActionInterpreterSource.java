package me.supcheg.advancedgui.api.loader.interpret;

import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public interface ActionInterpreterSource {
    @NotNull
    Stream<ActionInterpreterEntry<?>> interpreters();
}
