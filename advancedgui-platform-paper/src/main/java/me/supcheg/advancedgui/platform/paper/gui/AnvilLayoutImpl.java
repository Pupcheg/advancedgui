package me.supcheg.advancedgui.platform.paper.gui;

import me.supcheg.advancedgui.api.layout.AnvilLayout;
import me.supcheg.advancedgui.api.layout.template.anvil.InputUpdateListener;
import me.supcheg.advancedgui.api.lifecycle.LifecycleListenerRegistry;
import me.supcheg.advancedgui.api.sequence.collection.SequencedSortedSet;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

import static me.supcheg.advancedgui.api.lifecycle.pointcut.TickPointcut.afterTickPointcut;
import static me.supcheg.advancedgui.api.lifecycle.pointcut.TickPointcut.beforeTickPointcut;

public record AnvilLayoutImpl(
        @NotNull Set<ButtonImpl> buttons,
        @NotNull SequencedSortedSet<InputUpdateListener> inputUpdateListeners,
        @NotNull LifecycleListenerRegistry<AnvilLayout> lifecycleListenerRegistry
) implements AnvilLayout, LayoutImpl<AnvilLayout> {

    @NotNull
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
