package me.supcheg.advancedgui.api.button.display;

import java.util.Iterator;

public sealed interface ButtonDisplayProvider permits SingleButtonDisplayProvider, UpdatableButtonDisplayProvider {
    Iterator<ButtonDisplay> displaysLoop();
}
