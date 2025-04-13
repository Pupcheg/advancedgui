package me.supcheg.advancedgui.platform.paper.gui;

import me.supcheg.advancedgui.api.button.Button;
import me.supcheg.advancedgui.api.button.attribute.ButtonAttribute;
import me.supcheg.advancedgui.api.button.description.Description;
import me.supcheg.advancedgui.api.button.interaction.ButtonInteraction;
import me.supcheg.advancedgui.api.coordinate.Coordinate;
import me.supcheg.advancedgui.api.lifecycle.LifecycleListenerRegistry;
import me.supcheg.advancedgui.platform.paper.lifecycle.DefaultLifecycled;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.SortedSet;

import static me.supcheg.advancedgui.api.lifecycle.pointcut.TickPointcut.afterTickPointcut;
import static me.supcheg.advancedgui.api.lifecycle.pointcut.TickPointcut.beforeTickPointcut;

public record ButtonImpl(
        @NotNull Coordinate coordinate,
        @NotNull SortedSet<ButtonInteraction> interactions,
        @NotNull Key texture,
        @NotNull Component name,
        @NotNull Description description,
        @NotNull Set<ButtonAttribute> attributes,
        @NotNull LifecycleListenerRegistry<Button> lifecycleListenerRegistry
) implements Button, DefaultLifecycled<Button> {

    public void tick() {
        handleEachLifecycleAction(beforeTickPointcut());


        handleEachLifecycleAction(afterTickPointcut());
    }

    @NotNull
    @Override
    public Button lifecycleSubject() {
        return this;
    }
}
