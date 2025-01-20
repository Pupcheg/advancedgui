package me.supcheg.advancedgui.api.gui;

import me.supcheg.advancedgui.api.gui.background.Background;
import me.supcheg.advancedgui.api.layout.Layout;
import me.supcheg.advancedgui.api.lifecycle.Lifecycled;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.NotNull;

public interface Gui extends Keyed, Lifecycled<Gui> {

    @NotNull
    @Override
    Key key();

    @NotNull
    Background background();

    @NotNull
    Layout layout();

    void open(@NotNull Audience audience);
}
