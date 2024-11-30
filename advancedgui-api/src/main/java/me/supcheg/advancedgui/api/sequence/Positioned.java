package me.supcheg.advancedgui.api.sequence;

import org.jetbrains.annotations.NotNull;

public interface Positioned<T extends Positioned<T>> extends Sequenced<T> {
    @NotNull
    At at();
}
