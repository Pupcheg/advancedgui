package me.supcheg.advancedgui.api.sequence;

import me.supcheg.advancedgui.api.sequence.pointcut.PointCut;
import org.jetbrains.annotations.NotNull;

public interface Positioned<T extends Positioned<T>> extends Sequenced<T> {
    @NotNull
    PointCut at();
}
