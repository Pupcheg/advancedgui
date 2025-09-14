package me.supcheg.advancedgui.platform.paper.gui;

import me.supcheg.advancedgui.api.layout.AnvilLayout;
import me.supcheg.advancedgui.api.layout.template.anvil.InputUpdateListener;
import me.supcheg.advancedgui.api.lifecycle.LifecycleListenerRegistry;
import me.supcheg.advancedgui.api.sequence.collection.SequencedSortedSet;
import net.minecraft.world.inventory.MenuType;

import java.util.Set;

import static me.supcheg.advancedgui.api.lifecycle.pointcut.TickPointcut.afterTickPointcut;
import static me.supcheg.advancedgui.api.lifecycle.pointcut.TickPointcut.beforeTickPointcut;

public record AnvilLayoutImpl(
        Set<ButtonImpl> buttons,
        SequencedSortedSet<InputUpdateListener> inputUpdateListeners,
        LifecycleListenerRegistry<AnvilLayout> lifecycleListenerRegistry
) implements AnvilLayout, LayoutImpl<AnvilLayout> {

    @Override
    public MenuType<?> menuType() {
        return MenuType.ANVIL;
    }

    public void tick() {
        handleEachLifecycleAction(beforeTickPointcut());

        for (ButtonImpl button : buttons) {
            button.tick();
        }

        handleEachLifecycleAction(afterTickPointcut());
    }
}
