package me.supcheg.advancedgui.api.sequence;

public interface Sequenced<T extends Sequenced<T>> extends Comparable<T> {

    Priority priority();

    @Override
    default int compareTo(T o) {
        return priority().compareTo(o.priority());
    }
}
