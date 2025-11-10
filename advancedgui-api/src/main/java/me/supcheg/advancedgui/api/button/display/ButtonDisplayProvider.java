package me.supcheg.advancedgui.api.button.display;

import java.util.Iterator;

public interface ButtonDisplayProvider {
    Iterator<? extends ButtonDisplay> displaysLoop();
}
