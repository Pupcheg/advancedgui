package me.supcheg.advancedgui.platform.paper.gui;

import me.supcheg.advancedgui.api.button.Button;
import me.supcheg.advancedgui.api.button.display.ButtonDisplayProvider;
import me.supcheg.advancedgui.api.button.interaction.ButtonInteraction;
import me.supcheg.advancedgui.api.coordinate.Coordinate;
import me.supcheg.advancedgui.api.lifecycle.LifecycleListenerRegistry;
import me.supcheg.advancedgui.api.sequence.collection.SequencedSortedSet;
import me.supcheg.advancedgui.platform.paper.lifecycle.DefaultLifecycled;

import static me.supcheg.advancedgui.api.lifecycle.pointcut.TickPointcut.afterTickPointcut;
import static me.supcheg.advancedgui.api.lifecycle.pointcut.TickPointcut.beforeTickPointcut;

public record ButtonImpl(
        Coordinate coordinate,
        SequencedSortedSet<ButtonInteraction> interactions,
        ButtonDisplayProvider displayProvider,
        LifecycleListenerRegistry<Button> lifecycleListenerRegistry
) implements Button, DefaultLifecycled<Button> {

    public void tick() {
        handleEachLifecycleAction(beforeTickPointcut());


        handleEachLifecycleAction(afterTickPointcut());
    }
}
