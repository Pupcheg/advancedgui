package me.supcheg.advancedgui.api.sequence;

import net.kyori.examination.Examinable;
import net.kyori.examination.ExaminableProperty;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.stream.Stream;

import static me.supcheg.advancedgui.api.sequence.PriorityImpl.priorityImpl;

public sealed interface Priority extends Examinable, Comparable<Priority> permits NamedPriority, PriorityImpl {

    static Priority priority(int value) {
        @Nullable Priority named = NamedPriorityImpl.byValue(value);
        if (named != null) {
            return named;
        }
        return priorityImpl(value);
    }

    int value();

    @Override
    default Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(
                ExaminableProperty.of("value", value())
        );
    }

    default Priority earlier(int value) {
        return priority(this.value() - value);
    }

    default Priority earlier(Priority priority) {
        return earlier(priority.value());
    }

    default Priority later(int value) {
        return priority(this.value() + value);
    }

    default Priority later(Priority priority) {
        return later(priority.value());
    }

    @Override
    default int compareTo(Priority o) {
        return Integer.compare(value(), o.value());
    }
}
