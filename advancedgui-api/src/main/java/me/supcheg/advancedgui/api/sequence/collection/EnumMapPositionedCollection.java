package me.supcheg.advancedgui.api.sequence.collection;

import me.supcheg.advancedgui.api.sequence.Positioned;
import me.supcheg.advancedgui.api.sequence.pointcut.PointCut;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collector;
import java.util.stream.Collectors;

class EnumMapPositionedCollection<P extends Positioned<P>> implements PositionedCollection<P> {
    protected static final EnumMapPositionedCollection<?> EMPTY = new EnumMapPositionedCollection<>();

    protected final Map<PointCut, SortedSet<P>> map;

    private EnumMapPositionedCollection() {
        this.map = new HashMap<>();
    }

    EnumMapPositionedCollection(@NotNull Collection<P> collection) {
        this.map = collection
                .stream()
                .collect(Collectors.groupingBy(
                        Positioned::at,
                        HashMap::new,
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
    public SortedSet<P> allElementsWith(@NotNull PointCut at) {
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
