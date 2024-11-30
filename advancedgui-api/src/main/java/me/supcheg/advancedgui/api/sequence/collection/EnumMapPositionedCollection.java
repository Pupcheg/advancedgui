package me.supcheg.advancedgui.api.sequence.collection;

import me.supcheg.advancedgui.api.sequence.At;
import me.supcheg.advancedgui.api.sequence.Positioned;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collector;
import java.util.stream.Collectors;

class EnumMapPositionedCollection<P extends Positioned<P>> implements PositionedCollection<P> {
    protected static final EnumMapPositionedCollection<?> EMPTY = new EnumMapPositionedCollection<>();

    protected final Map<At, SortedSet<P>> map;

    private EnumMapPositionedCollection() {
        this.map = new EnumMap<>(At.class);
    }

    EnumMapPositionedCollection(@NotNull Collection<P> collection) {
        this.map = collection
                .stream()
                .collect(Collectors.groupingBy(
                        Positioned::at,
                        () -> new EnumMap<>(At.class),
                        Collector.<P, SortedSet<P>, SortedSet<P>>of(
                                TreeSet::new,
                                Set::add,
                                (ps, ps2) -> {
                                    ps.addAll(ps2);
                                    return ps;
                                },
                                Collections::unmodifiableSortedSet
                        )
                ));
    }

    @NotNull
    @Unmodifiable
    @Override
    public SortedSet<P> allElementsWith(@NotNull At at) {
        return map.getOrDefault(at, Collections.emptySortedSet());
    }

    @NotNull
    @Unmodifiable
    @Override
    public Collection<P> allElements() {
        return map.values().stream()
                .flatMap(Collection::stream)
                .toList();
    }
}
