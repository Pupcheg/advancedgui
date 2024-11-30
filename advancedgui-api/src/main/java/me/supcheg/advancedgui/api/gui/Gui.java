package me.supcheg.advancedgui.api.gui;

import me.supcheg.advancedgui.api.gui.background.Background;
import me.supcheg.advancedgui.api.gui.tick.GuiTicker;
import me.supcheg.advancedgui.api.layout.Layout;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.NotNull;

import java.util.SortedSet;

public interface Gui extends Keyed {

    @NotNull
    @Override
    Key key();

    @NotNull
    Background background();

    @NotNull
    SortedSet<GuiTicker> tickers();

    @NotNull
    Layout layout();

    void open(@NotNull Object entity);
}
