package me.supcheg.advancedgui.api.sequence.collection;

import me.supcheg.advancedgui.api.sequence.At;
import me.supcheg.advancedgui.api.sequence.Positioned;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toCollection;

class MutableEnumMapPositionedCollection<P extends Positioned<P>> implements MutablePositionedCollection<P> {
    protected final Map<At, SortedSet<P>> map;

    MutableEnumMapPositionedCollection(@NotNull Collection<P> collection) {
        this.map = collection
                .stream()
                .collect(Collectors.groupingBy(
                        Positioned::at,
                        () -> new EnumMap<>(At.class),
                        toCollection(TreeSet::new)
                ));
    }

    MutableEnumMapPositionedCollection() {
        this.map = new EnumMap<>(At.class);
    }

    @Override
    public void add(@NotNull P positioned) {
        map.computeIfAbsent(positioned.at(), at -> new TreeSet<>())
                .add(positioned);
    }

    @Override
    public void remove(@NotNull P positioned) {
        SortedSet<P> set = map.get(positioned.at());
        if (set != null) {
            set.remove(positioned);
        }
    }

    @NotNull
    @Unmodifiable
    @Override
    public SortedSet<P> allElementsWith(@NotNull At at) {
        return Collections.unmodifiableSortedSet(map.getOrDefault(at, Collections.emptySortedSet()));
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
