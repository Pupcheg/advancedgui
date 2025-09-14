package me.supcheg.advancedgui.platform.paper.gui;

import me.supcheg.advancedgui.api.button.Button;
import me.supcheg.advancedgui.api.button.attribute.ButtonAttribute;
import me.supcheg.advancedgui.api.button.description.Description;
import me.supcheg.advancedgui.api.button.interaction.ButtonInteraction;
import me.supcheg.advancedgui.api.coordinate.Coordinate;
import me.supcheg.advancedgui.api.lifecycle.LifecycleListenerRegistry;
import me.supcheg.advancedgui.api.sequence.collection.SequencedSortedSet;
import me.supcheg.advancedgui.platform.paper.lifecycle.DefaultLifecycled;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;

import java.util.Set;

import static me.supcheg.advancedgui.api.lifecycle.pointcut.TickPointcut.afterTickPointcut;
import static me.supcheg.advancedgui.api.lifecycle.pointcut.TickPointcut.beforeTickPointcut;

public record ButtonImpl(
        Coordinate coordinate,
        SequencedSortedSet<ButtonInteraction> interactions,
        Key texture,
        Component name,
        Description description,
        Set<ButtonAttribute> attributes,
        LifecycleListenerRegistry<Button> lifecycleListenerRegistry
) implements Button, DefaultLifecycled<Button> {

    public void tick() {
        handleEachLifecycleAction(beforeTickPointcut());


        handleEachLifecycleAction(afterTickPointcut());
    }
}
