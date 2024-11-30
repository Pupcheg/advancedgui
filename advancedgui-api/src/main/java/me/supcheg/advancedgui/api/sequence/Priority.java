package me.supcheg.advancedgui.api.sequence;

import org.jetbrains.annotations.NotNull;

import static me.supcheg.advancedgui.api.sequence.NamedPriorityImpl.namedPriorityImpl;
import static me.supcheg.advancedgui.api.sequence.PriorityImpl.priorityImpl;

public sealed interface Priority extends Comparable<Priority> permits NamedPriority, PriorityImpl {
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
    static Priority priority(int value) {
        Priority named = NamedPriorityImpl.byValue(value);
        if (named != null) {
            return named;
        }
        return priorityImpl(value);
    }

    int value();

    @NotNull
    default Priority earlier(int value) {
        return priority(this.value() - value);
    }

    @NotNull
    default Priority earlier(@NotNull Priority priority) {
        return earlier(priority.value());
    }

    @NotNull
    default Priority later(int value) {
        return priority(this.value() + value);
    }

    @NotNull
    default Priority later(@NotNull Priority priority) {
        return later(priority.value());
    }

    @Override
    default int compareTo(@NotNull Priority o) {
        return Integer.compare(value(), o.value());
    }
}
