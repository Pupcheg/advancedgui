package me.supcheg.advancedgui.api.sequence.collection;

import me.supcheg.advancedgui.api.sequence.NamedPriority;
import me.supcheg.advancedgui.api.sequence.Priority;
import me.supcheg.advancedgui.api.sequence.Sequenced;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SequencedSortedSetTests {

    record TheSequenced(int index, Priority priority) implements Sequenced<TheSequenced> {
    }

    @Test
    void missionTest() {
        assertEquals(
                List.of(
                        new TheSequenced(0, NamedPriority.HIGHEST),
                        new TheSequenced(1, NamedPriority.HIGH),
                        new TheSequenced(2, NamedPriority.NORMAL),
                        new TheSequenced(3, NamedPriority.LOW),
                        new TheSequenced(4, NamedPriority.LOWEST)
                ),
                List.copyOf(SequencedSortedSets.of(
                        new TheSequenced(1, NamedPriority.HIGH),
                        new TheSequenced(2, NamedPriority.NORMAL),
                        new TheSequenced(4, NamedPriority.LOWEST),
                        new TheSequenced(3, NamedPriority.LOW),
                        new TheSequenced(0, NamedPriority.HIGHEST)
                ))
        );
    }

    @Test
    void createTest() {
        var set = SequencedSortedSets.<TheSequenced>create();

        assertThat(set).isEmpty();

        set.add(new TheSequenced(0, NamedPriority.NORMAL));
        assertThat(set)
                .containsExactlyInAnyOrder(new TheSequenced(0, NamedPriority.NORMAL));
    }

    @Test
    void createVarArgsTest() {
        var set = SequencedSortedSets.create(
                new TheSequenced(0, NamedPriority.NORMAL),
                new TheSequenced(1, NamedPriority.NORMAL)
        );

        assertThat(set)
                .containsExactlyInAnyOrder(
                        new TheSequenced(0, NamedPriority.NORMAL),
                        new TheSequenced(1, NamedPriority.NORMAL)
                );

        set.add(new TheSequenced(2, NamedPriority.NORMAL));
        assertThat(set)
                .containsExactlyInAnyOrder(
                        new TheSequenced(0, NamedPriority.NORMAL),
                        new TheSequenced(1, NamedPriority.NORMAL),
                        new TheSequenced(2, NamedPriority.NORMAL)
                );
    }

    @Test
    void createCopyTest() {
        var set = SequencedSortedSets.createCopy(List.of(
                new TheSequenced(0, NamedPriority.NORMAL),
                new TheSequenced(1, NamedPriority.NORMAL)
        ));

        assertThat(set)
                .containsExactlyInAnyOrder(
                        new TheSequenced(0, NamedPriority.NORMAL),
                        new TheSequenced(1, NamedPriority.NORMAL)
                );

        set.add(new TheSequenced(2, NamedPriority.NORMAL));
        assertThat(set)
                .containsExactlyInAnyOrder(
                        new TheSequenced(0, NamedPriority.NORMAL),
                        new TheSequenced(1, NamedPriority.NORMAL),
                        new TheSequenced(2, NamedPriority.NORMAL)
                );
    }

    @Test
    void ofTest() {
        var set = SequencedSortedSets.<TheSequenced>of();

        assertThat(set).isEmpty();

        assertThat(set).isUnmodifiable();
    }

    @Test
    void ofVarArgsTest() {
        var set = SequencedSortedSets.of(
                new TheSequenced(0, NamedPriority.NORMAL),
                new TheSequenced(1, NamedPriority.NORMAL)
        );

        assertThat(set).containsExactlyInAnyOrder(
                new TheSequenced(0, NamedPriority.NORMAL),
                new TheSequenced(1, NamedPriority.NORMAL)
        );

        assertThat(set).isUnmodifiable();
    }

    @Test
    void copyOfTest() {
        var set = SequencedSortedSets.copyOf(List.of(
                new TheSequenced(0, NamedPriority.NORMAL),
                new TheSequenced(1, NamedPriority.NORMAL)
        ));

        assertThat(set)
                .containsExactlyInAnyOrder(
                        new TheSequenced(0, NamedPriority.NORMAL),
                        new TheSequenced(1, NamedPriority.NORMAL)
                );

        assertThat(set).isUnmodifiable();
    }

}
