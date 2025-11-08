package me.supcheg.advancedgui.platform.paper.gui;

import me.supcheg.advancedgui.api.layout.ChestLayout;
import me.supcheg.advancedgui.api.lifecycle.LifecycleListenerRegistry;
import net.minecraft.world.inventory.MenuType;

import java.util.Set;

import static me.supcheg.advancedgui.api.lifecycle.pointcut.TickPointcut.afterTickPointcut;
import static me.supcheg.advancedgui.api.lifecycle.pointcut.TickPointcut.beforeTickPointcut;

public record ChestLayoutImpl(
        Set<ButtonImpl> buttons,
        int rows,
        LifecycleListenerRegistry<ChestLayout> lifecycleListenerRegistry
) implements ChestLayout, LayoutImpl<ChestLayout> {
    @Override
    public MenuType<?> menuType() {
       return switch (rows) {
           case 1 -> MenuType.GENERIC_9x1;
           case 2 -> MenuType.GENERIC_9x2;
           case 3 -> MenuType.GENERIC_9x3;
           case 4 -> MenuType.GENERIC_9x4;
           case 5 -> MenuType.GENERIC_9x5;
           case 6 -> MenuType.GENERIC_9x6;
           default -> throw new IllegalArgumentException("Illegal row count " + rows);
        };
    }

    @Override
    public void tick() {
        handleEachLifecycleAction(beforeTickPointcut());

        for (ButtonImpl button : buttons) {
            button.tick();
        }

        handleEachLifecycleAction(afterTickPointcut());
    }
}
