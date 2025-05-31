package me.supcheg.advancedgui.api.sequence;

import net.kyori.examination.ExaminableProperty;

import java.util.stream.Stream;

import static me.supcheg.advancedgui.api.sequence.NamedPriorityImpl.namedPriorityImpl;

public sealed interface NamedPriority extends Priority permits NamedPriorityImpl {
    NamedPriority LOWEST = namedPriorityImpl("lowest", 1000);
    NamedPriority LOW = namedPriorityImpl("low", 500);
    NamedPriority NORMAL = namedPriorityImpl("normal", 0);
    NamedPriority HIGH = namedPriorityImpl("high", -500);
    NamedPriority HIGHEST = namedPriorityImpl("highest", -1000);

    static NamedPriority namedPriority(String name) {
        return NamedPriorityImpl.byNameOrThrow(name);
    }

    String name();

    @Override
    default Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(
                ExaminableProperty.of("name", name())
        );
    }
}
