package me.supcheg.advancedgui.api.sequence;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

record PriorityImpl(
        int value
) implements Priority {
    @NotNull
    @Contract("_ -> new")
    static PriorityImpl priorityImpl(int value) {
        return new PriorityImpl(value);
    }
}
