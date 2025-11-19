package me.supcheg.advancedgui.api.button.display;

import java.time.Duration;

public non-sealed interface UpdatableButtonDisplayProvider extends ButtonDisplayProvider {
    Duration switchDuration();

    boolean updatable();
}
