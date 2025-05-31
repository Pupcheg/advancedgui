package me.supcheg.advancedgui.api.loader.interpret;

import java.util.stream.Stream;

public interface ActionInterpreterSource {
    Stream<ActionInterpreterEntry<?>> interpreters();
}
