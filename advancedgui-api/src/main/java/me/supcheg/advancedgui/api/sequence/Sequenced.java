package me.supcheg.advancedgui.api.sequence;

import org.jetbrains.annotations.NotNull;

public interface Sequenced<T extends Sequenced<T>> extends Comparable<T> {

    @NotNull
    Priority priority();

    @Override
    default int compareTo(@NotNull T o) {
        return priority().compareTo(o.priority());
    }
}
