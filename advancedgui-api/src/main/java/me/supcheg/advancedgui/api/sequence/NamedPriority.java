package me.supcheg.advancedgui.api.sequence;

import org.jetbrains.annotations.NotNull;

public sealed interface NamedPriority extends Priority permits NamedPriorityImpl {
    @NotNull
    String name();
}
