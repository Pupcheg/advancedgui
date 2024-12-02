package me.supcheg.advancedgui.api.sequence;

import org.jetbrains.annotations.NotNull;

import static me.supcheg.advancedgui.api.sequence.NamedPriorityImpl.namedPriorityImpl;

public sealed interface NamedPriority extends Priority permits NamedPriorityImpl {
    NamedPriority LOWEST = namedPriorityImpl("lowest", 1000);
    NamedPriority LOW = namedPriorityImpl("low", 500);
    NamedPriority NORMAL = namedPriorityImpl("normal", 0);
    NamedPriority HIGH = namedPriorityImpl("high", -500);
    NamedPriority HIGHEST = namedPriorityImpl("highest", -1000);

    @NotNull
    static NamedPriority namedPriority(@NotNull String name) {
        return NamedPriorityImpl.byNameOrThrow(name);
    }

    @NotNull
    String name();
}
