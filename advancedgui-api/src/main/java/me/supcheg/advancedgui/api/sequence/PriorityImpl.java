package me.supcheg.advancedgui.api.sequence;

record PriorityImpl(
        int value
) implements Priority {

    static PriorityImpl priorityImpl(int value) {
        return new PriorityImpl(value);
    }

}
